# -*- coding: utf-8 -*-
"""
Created on Sun Mar 06 10:17:13 2016

@author: fishsey
""" 

def simEuclideDistance(inA, inB):
    '''
    计算欧式距离：即多维空间2点之间的距离\n
    Args:
        inA, inB: 一维数组 array([ , , ,]), 行向量\n
        wsWeight: 服务的权重，值越大相似度越大
    Ruturn:
        1.0/(1.0 + 距离): [0,1), 值越大相似性越强
    '''      
    tempA = []
    tempB = []
    for i in xrange(len(inA)):
        if inA[i] and inB[i] :
            tempA.append(inA[i])
            tempB.append(inB[i])
    if len(tempA) == 0:#if no common rating item, then return 0
        return 0.0
    inA = np.array(tempA)
    inB = np.array(tempB)
    return 1.0/(1.0+la.norm(inA-inB)) 
    
def simCorrectionCosine(inA, inB):
    global userAverageVector
    tempA = []
    tempB = []
    for i in xrange(len(inA)):
        if inA[i] and inB[i] :
            tempA.append(inA[i] - userAverageVector[i])
            tempB.append(inB[i] - userAverageVector[i])
    if len(tempA) == 0:#if no common rating item, then return 0
        return 0
    inA = np.mat(tempA)
    inB = np.mat(tempB)
    num = float(inA*inB.T)  
    denom = la.norm(inA) * la.norm(inB)  
    return num/denom       
        
    
def createArrayObj(fileName, userNum, wsNum):
    '''从测试集、训练集文件读取数据，建立相应的 ndarray 对象: user * ws
    '''
    arrayObj = np.array([0.0] * userNum * wsNum).reshape(userNum, wsNum)
    with open(fileName) as pf:
        for line in pf:
            userId, wsId, rt = line.split()
            userId = int(userId) - 1 #修改 id 从 0 开始
            wsId = int(wsId) - 1
            rt = float(rt)
            arrayObj[userId, wsId] = rt    
    return arrayObj
    
def loadDataForRatingArray(fileName):
    arrayObj = createArrayObj(fileName, 339, 5825)
    return arrayObj    
    
def createClusters(fileName):
    '''进行 as\city\country 聚类, 如果该id的as\city\country = "not found" 则放入'not found'聚类中\n
    {as: [id, id, ...], ...}\n
    {city: [id, id, ...], ...}\n
    {country: [id, id, ...], ...}\n
    id: int
    '''
    asClusters = {}
    cityCluseters = {}
    countryCluseters = {}
    with open(fileName, 'r') as pf:
        for line in pf:
            IdNum, Ip, asNum, city, country = line.strip().split('\t')
            IdNum = int(IdNum)
            asClusters.setdefault(asNum,[])
            cityCluseters.setdefault(city,[])
            countryCluseters.setdefault(country,[])
            asClusters[asNum].append(IdNum)
            cityCluseters[city].append(IdNum)
            countryCluseters[country].append(IdNum) 
    return asClusters, cityCluseters, countryCluseters

 
def createMapping(fileName, numbers):
    '''创建 id 与 AS\city\country 的映射：[[as, city, country], ...]
    返回嵌套的列表\n
        Args:
            numbers: int, 用户/服务的数目
    '''
    listObj = [[]] * numbers
    with open(fileName, 'r') as pf:
        for line in pf:
            IdNum, Ip, asNum, city, country = line.strip().split('\t')
            IdNum = int(IdNum)
            temp = [asNum, city, country]
            listObj[IdNum] = temp
    return listObj
    
def loadUserData():
    userMappingFile = r'dataSet/userMapping.txt'
    userMapping = createMapping(userMappingFile, 339)
    userAsClusters, userCityCluster, userCountryCluster = createClusters(userMappingFile)
    return userMapping, userAsClusters, userCityCluster, userCountryCluster
    
def loadWsData():
    wsMappingFile = r'dataSet/webServerMapping.txt'
    wsMapping = createMapping(wsMappingFile, 5825)
    wsAsClusters, wsCityCluster, wsCountryCluster = createClusters(wsMappingFile) 
    return wsMapping, wsAsClusters, wsCityCluster, wsCountryCluster
    

def loadDataForDict(filenameTrain, filenameTest):
    '''读取文件中数据，建立并返回字典对象\n
        {userId(int): {wsId(int): rating(float), ...}, ...}\n
        id 编号从 0 开始, 且为 int
    '''
    train = {}
    test = {}
    for line in open(filenameTrain):
        (userId, itemId, rating) = line.strip().split('\t')
        userId = int(userId)-1 #id均转化为整数,且编号从 0 开始
        itemId = int(itemId)-1
        train.setdefault(userId, {})
        train[userId][itemId] = float(rating)
    for line in open(filenameTest):
        (userId, itemId, rating) = line.strip().split('\t')
        userId = int(userId)-1
        itemId = int(itemId)-1
        test.setdefault(userId, {})
        test[userId][itemId] = float(rating)
    return train, test

def calMean(train):
    '''训练集中所有评分的平均值
    '''
    sumValue = 0.0
    count = 0
    for user in train:
        for item in train[user]:
            sumValue += train[user][item]
            count += 1
    if count == 0:
        return None
    return sumValue / count
    
def columnAverage(trainArrayObj, i, mean):
    '''返回 i 列排除 None 之后的平均值, 如果该列全部是 None 则返回全局平均值
    '''
    number = 0
    sums = 0.0
    for elem in trainArrayObj[:, i]:
            if elem:
                number += 1
                sums += elem
    if number == 0: 
        return mean
    else:
        return sums/number

def columnAverageArray(arrayObj, mean):
    result = np.empty(arrayObj.shape[1]) #结果长度为列向量的个数
    for index in xrange(len(result)):
        result[index] =  columnAverage(arrayObj, index, mean)
    return result   


def topKNeighbors(IdNum, wsMapping, wsAsClusters, wsCountryCluster, simCalMethod):
    '''为 IdNum 指向的用户/服务在国家聚类集合中挑选 k 个最相似邻居集\n
    返回：[(相似度, 用户ID), ...], k 取决于聚类的大小
    '''
    asNum, city, country = wsMapping[IdNum]
    simDict = {}
    length = len(wsCountryCluster[country])
    if length <= 1:
        return []
    if length == 2:
        k = 1
    if length == 3:
        k = 2
    else:
        k = int(math.sqrt(length)) + int(7.0 * length / 24) 
    k = min(k, 30)
    #与 IdNum 在同一个 country 内的用户/服务
    for otherId in wsCountryCluster[country]:
        if otherId == IdNum:
            continue
        sim = simCalMethod(trainArrayObj[:, IdNum], trainArrayObj[:, otherId])
        if sim >= 0.6:
            sim = sim * pow(abs(sim), 10.0)
            simDict[sim] = otherId      
    #相似邻居集的选择增加一个全局均值评分向量，以修正聚类集合太小的聚类
    sim = simCalMethod(trainArrayObj[:, IdNum], wsAverageVector)
    if sim >= 0.6:
        sim = sim * pow(abs(sim), 10.0)
        simDict[sim] = 'global'  
    simList = sorted(simDict.items(), key = lambda x: x[0], reverse = True)
    if len(simList) >= k:
        return simList[0: k]
    return simList
    
    
def predict(u, i, wsTopKNeighbors, trainArrayObj, wsAverageVector):
    '''预测 u 对 i 的评分
    '''
    topK = wsTopKNeighbors[i]
    sums = 0.0
    simSum = 0.0
    #如果相似邻居集为空,则依次使用用户u所在的AS/city/country对 i 的平均评分，
    #用户 u 对服务 i 所在的AS/city/country 的评价评分
    if not topK: 
        return getUserClusterOnWsAverageRate(u, i) or wsAverageVector[i]
    #相似邻居不为空, 则基于相似用户给予评分估计
    for similarity, otherWsId in wsTopKNeighbors[i]:
        if otherWsId == 'global':
            simSum += similarity
            sums += similarity * wsAverageVector[i]
        else:
            if trainArrayObj[u, otherWsId]: #相似用户对 i有评分
                simSum += similarity
                sums += similarity * trainArrayObj[u, otherWsId]
    if sums == 0.0:#如果相似用户对 i 都未评分
        return getUserClusterOnWsAverageRate(u, i) or wsAverageVector[i]
    else:
        return sums/simSum
        
    
def calMaeAndRmse(testDict, wsTopKNeighbors, trainArrayObj, wsAverageVector, pf):
    ''''针对测试集计算预测精确度，返回MAE、RMSE
    '''
    MAE = 0.0
    RMSE = 0.0
    number = 0
    for u in testDict:
        for i in testDict[u]:
            pui = predict(u, i, wsTopKNeighbors, trainArrayObj, wsAverageVector)
            eui = testDict[u][i] - pui
#            pf.write(str(eui) + '\n')
            RMSE += pow(eui, 2)
            MAE += abs(eui)
            number += 1
    RMSE = math.sqrt(RMSE / number)
    MAE /= number
    return MAE, RMSE     
    
def getUserClusterOnWsAverageRate(u, i):
    '''u 对应评分矩阵 trainArrayObj 的行， i 对应评分矩阵 trainArrayObj 的列
    '''
    global userMapping, userAsClusters, trainArrayObj
    sums = 0.0
    numbers = 0.0
    asNum, city, country = userMapping[u]
    if asNum == 'not found':
        return getUserCountryClusterOnWsAverageRate(u, i)
    for idNum in userAsClusters[asNum]:
        if trainArrayObj[idNum][i]:
            sums += trainArrayObj[idNum][i]
            numbers += 1 
    if numbers == 0.0:
        return getUserCountryClusterOnWsAverageRate(u, i)
    return sums/numbers
    
    
def getUserCountryClusterOnWsAverageRate(u, i):
    global userMapping, userCountryClusters, trainArrayObj
    sums = 0.0
    numbers = 0.0
    asNum, city, country = userMapping[u]
    if country == 'not found':
        return None
    for idNum in userCountryClusters[country]:
        if trainArrayObj[idNum][i]:
            sums += trainArrayObj[idNum][i]
            numbers += 1 
    if numbers == 0.0:
        return None
    return sums/numbers          
       
import math
import numpy as np
from numpy import linalg as la
import copy
import sys
if __name__ == '__main__': 
    userMapping, userAsClusters, userCityClusters, userCountryClusters = loadUserData()
    wsMapping, wsAsClusters, wsCityClusters, wsCountryClusters = loadWsData()
    simCalMethod = simCorrectionCosine
    #设定参数
    sparseness = 'sparseness20'
    fileNumbers = 1
    userNum = 339
    wsNum =  5825
    MAE = 0.0
    RMSE = 0.0
    for i in range(1, fileNumbers+1):
        wsTopKNeighbors = []
        trainFileName = 'dataSet/%s/training%d.txt' % (sparseness,i)
        testFileName = 'dataSet/%s/test%d.txt' % (sparseness,i)
        euiFileName = 'result/wsBased20Final-test%d.txt' % (i)
        pf = open(euiFileName, 'w')
        #字典数据装置        
        trainDict, testDict = loadDataForDict(trainFileName, testFileName)
        #评分矩阵装置
        trainArrayObj = loadDataForRatingArray(trainFileName)
        mean = calMean(trainDict)
        #trainArrayObj 列向量均值组成的一维数组
        wsAverageVector = columnAverageArray(trainArrayObj, mean) 
        userAverageVector = columnAverageArray(trainArrayObj.T, mean) 
        #获取相近邻居集
        for IdNum in range(wsNum):
            print IdNum
            result = topKNeighbors(IdNum, wsMapping, wsAsClusters, wsCountryClusters, simCalMethod)
            wsTopKNeighbors.append(result)
        mae, rmse = calMaeAndRmse(testDict, wsTopKNeighbors, trainArrayObj, wsAverageVector, pf) 
        print mae, rmse
        MAE += mae
        RMSE += rmse
    print MAE/fileNumbers, RMSE/fileNumbers
    pf.close()
    print 'ok'