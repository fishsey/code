# -*- coding: utf-8 -*-
"""
Created on Sat Apr 16 10:56:52 2016

@author: fishsey
"""
import numpy as np
import cPickle
from math import sqrt
NoneValue = 111111.0

def maeAndRmse(eui):
    sumMae = np.sum(np.abs(eui))
    sumRmse = np.linalg.norm(np.abs(eui))
    return sumMae/len(eui), sumRmse/(len(eui) ** 0.5)
    
    
def loadOriginalData(fileName=r'dataSet/original/rtmatrix.txt'):
    return np.loadtxt(fileName)
    
    
def save(obj, file):
    with open(file, 'wb') as f:
        cPickle.dump(obj, f)
        
def load(file):
    with open(file, 'rb') as f:
        obj = cPickle.load(f)
    return obj 
       
def simMinkowskiDist(inA, inB, n=2.0):
    '''闵可夫斯基距离：欧式距离的扩展
    '''
    inA = np.array(inA)
    inB = np.array(inB)
    indiceA = inA!= NoneValue
    indiceB = inB!= NoneValue
    indice = indiceA & indiceB
    inA = inA[indice]
    inB = inB[indice]
    if len(inA) == 0:
        return 0
    distance = sum((abs(inA - inB)) ** n) ** (1.0/n) / len(inA)
    sim = 1.0/(1.0 + distance)
    return sim 
    
def simPCC(inA, inB, n):
    ''' 
    '''
    inA = np.array(inA, dtype=float)
    inB = np.array(inB, dtype=float)
    indiceA = inA!= NoneValue
    meanA = np.mean(inA[indiceA])
    indiceB = inB!= NoneValue
    meanB = np.mean(inB[indiceB])
    indice = indiceA & indiceB
    inA = inA[indice]
    inB = inB[indice]
    if len(inA) == 0:
        return 0
    diffA = inA - meanA
    diffB = inB - meanB
    if (np.linalg.norm(diffA) * np.linalg.norm(diffB)) == 0:
        return 0
    return np.sum(diffA * diffB) / (np.linalg.norm(diffA) * np.linalg.norm(diffB))
    
def simMae(inA, inB):
    '''基于平均绝对差的相似度，返回 （0， 1] 值越大认为相似度越大
    '''
    inA = np.array(inA, dtype=float)
    inB = np.array(inB, dtype=float)
    indiceA = inA!= NoneValue
    indiceB = inB!= NoneValue
    indice = indiceA & indiceB
    inA = inA[indice]
    inB = inB[indice]
    if len(inA) == 0:
        return 0
    diff = inA - inB
    return 1.0/(1.0 + np.mean(np.abs(diff)))
    
    
def simVar(inA, inB):
    '''基于方差的相似度，返回 （0， 1] 值越大认为相似度越大
    '''
    inA = np.array(inA)
    inB = np.array(inB)
    indiceA = inA!= NoneValue
    indiceB = inB!= NoneValue
    indice = indiceA & indiceB
    inA = inA[indice]
    inB = inB[indice]
    if len(inA) == 0:
        return 0
    diff = inA - inB
    return 1.0/(1.0 + np.var(diff))
    
def createSimArray(trainArray, simMethod, n=2.0):
    '''返回 trainArray 行向量之间的相似度矩阵
    '''
    rowNum, columnNum = trainArray.shape
    result = np.zeros((rowNum, rowNum))
    for i in range(rowNum):
        for j in range(i):
            result[i,j] = result[j, i] = simMethod(trainArray[i], trainArray[j], n)
    return result
    
def createArrayObj(fileName=r'sample/training10-1.txt', userNum=339, wsNum=5825):
    trainObj = np.loadtxt(fileName)
    userLs, wsLs, rt  = trainObj[:, 0], trainObj[:, 1], trainObj[:, 2]
    userLs = np.array(userLs, dtype=int)
    wsLs = np.array(wsLs, dtype=int) 
    rt = np.array(rt, dtype=float)
    arrayObj = np.empty((userNum, wsNum))
    arrayObj.fill(NoneValue)
    arrayObj[userLs, wsLs] = rt 
    return arrayObj    
    
def loadTest(testFileName):
    result = np.loadtxt(testFileName)
#    result[:, 0] -=  1 #modify index start with 0
#    result[:, 1] -=  1
    return result
    
def calMean(arrayObj):
    arrayObj = np.array(arrayObj)
    return np.mean(np.extract(arrayObj!=NoneValue, arrayObj))
    
def calVar(arrayObj):
    arrayObj = np.array(arrayObj)
    return np.var(np.extract(arrayObj!=NoneValue, arrayObj))

def columnAverageArray(arrayObj, mean):
    '''返回数组列向量的平均值数组，如果原始数组为 m * n 返回 n * 1\n
    Args:
        arrayObj: 评分数组\n
        mean： 全局平均值
    '''
    arrayObj = np.array(arrayObj)
    columnNums = arrayObj.shape[1]
    result = np.empty(columnNums) #结果长度为列向量的个数
    for index in xrange(columnNums):
        columnData = np.extract(arrayObj[:, index]!=NoneValue, arrayObj[:, index])
        if len(columnData):
            result[index] = np.mean(columnData)
        else:
            result[index] = mean
    return result
 

if __name__ == '__main__':
    simCalMethod = simMinkowskiDist
    for sparseness in [20]:
        for fileNum in range(1, 11):
            print sparseness, fileNum
            trainFileName = "/root/AAA/dataset/rendi2/train/sparseness%s/training%d.txt" % (sparseness, fileNum)
            trainArrayObj = createArrayObj(trainFileName)
            
            userSimFileName = '/root/AAA/dataset/rendi2/slopeoneSim/rt-simArraySlopeoneUser-%d-%d.txt' % (sparseness, fileNum)
            wsSimFileName = '/root/AAA/dataset/rendi2/slopeoneSim/rt-simArraySlopeoneWs-%d-%d.txt' % (sparseness, fileNum)
            
            userSimArrayObj = createSimArray(trainArrayObj, simCalMethod, 1.0)
            wsSimArrayObj = createSimArray(trainArrayObj.T, simCalMethod, 1.7)
            
            save(userSimArrayObj, userSimFileName)
            save(wsSimArrayObj, wsSimFileName)
    
    
