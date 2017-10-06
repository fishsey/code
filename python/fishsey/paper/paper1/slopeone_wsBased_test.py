# -*- coding: utf-8 -*-
"""
Created on Sun Mar 06 10:17:13 2016

@author: fishsey
"""        
def topKUser(u, i):
    global maxlen, minSim, userSimArrayObj, trainArrayObj
    rowNum = trainArrayObj.shape[0]
    ls = []
    temp = []
    #遍历每个用户
    for otherUser in range(rowNum):
        if trainArrayObj[otherUser, i] != 111 and otherUser != u and userSimArrayObj[u, otherUser] > minSim:
            ls.append([userSimArrayObj[u,otherUser], otherUser])
    ls.sort(reverse = True)
    for index in range(min(maxlen, len(ls))):
        temp.append(ls[index][1])
    return temp
       
def topKWs(u, i):
    global maxlens, minSim, wsSimArrayObj, trainArrayObj
    colNum = trainArrayObj.shape[1]
    ls = []
    temp = []
    #遍历每个用户
    for otherWs in range(colNum):
        if trainArrayObj[u, otherWs]!=111 and otherWs != i and wsSimArrayObj[i,otherWs] > minSim:
            ls.append([wsSimArrayObj[i,otherWs], otherWs])
    ls.sort(reverse = True)
    for index in range(min(maxlens, len(ls))):
        temp.append(ls[index][1])
    return temp
    
def calMaeAndRmse():
    global trainArrayObj, pfEui, testArrayObj
    #cal the mae and rmse
    MAE = 0.0
    RMSE = 0.0
    number = 0
    lens = len(testArrayObj)
    for index in range(lens):
#        print index
        u = int(testArrayObj[index, 0]) 
        i = int(testArrayObj[index, 1]) 
        tui = testArrayObj[index, 2]
        userMemberlist = topKUser(u, i)
        wsMemberlist = topKWs(u, i) 
        flag, pui = predictSlope(u, i, userMemberlist, wsMemberlist)
        if flag:
            pui = modify(u, i, flag)
        eui = tui - pui
        # write data to the file
        pfEui.write(str(eui) + '\n')
        RMSE += pow(eui, 2)
        MAE += abs(eui)
        number += 1  
    RMSE = math.sqrt(RMSE / number)
    MAE /= number
    return MAE, RMSE    
  
def predictSlope(u, i, userMemberlist, wsMemberlist):
    global maxDiff, trainArrayObj, wsAverageVector, mean, var
    #初始化参数
    wsTrainArrayObj = trainArrayObj.T
    columnNum = wsTrainArrayObj.shape[1]
    differ = np.zeros(columnNum)
    numbers = np.zeros(columnNum)
    dev = np.zeros(columnNum)
    rating = wsTrainArrayObj[i]
    #依次遍历每列
    flag1 = 0
    flag2 = 0
    flag = 0
    for index in userMemberlist:
        for memberId in wsMemberlist:
            if rating[index] != 111 and index != u and memberId != i and wsTrainArrayObj[memberId,index] != 111 and wsTrainArrayObj[memberId,u] != 111 and abs(wsTrainArrayObj[memberId,u] - wsTrainArrayObj[memberId,index]) <= maxDiff:
#                abs(rating[index] - wsTrainArrayObj[memberId,index])
                 if wsTrainArrayObj[memberId,index] == -1 or wsTrainArrayObj[i,index] == -1 :
                     flag1 = 1
                 if wsTrainArrayObj[memberId,u] == -1:
                     flag2 = 1
                 differ[index] += (wsTrainArrayObj[memberId,u] - wsTrainArrayObj[memberId,index]) 
                 numbers[index] += 1.0
    for index in range(columnNum):
        if numbers[index]:
            dev[index] = differ[index]/ numbers[index]
    # 进行预测
    rating = rating + dev
    sums = sum(numbers)
    if flag2 == 1 and flag1 != 1:
        flag = 1
    if sums:
        return flag, (np.mat(rating) * np.mat(numbers).T)[0,0] / sums
    else:
        flag = 2
        return flag, wsAverageVector[i] 


def getPred(u, i):
    userMemberlist = topKUser(u, i)
    wsMemberlist = topKWs(u, i) 
    flag, pui = predictSlope(u, i, userMemberlist, wsMemberlist)
    return pui
    
def modify(u, i, flag):
    if flag == 2:
        return knn(u, i)
    elif flag == 1:
        return slope_knn(u, i)

def knn(u, i):
    userMembers = topKUser1(u, i)
    return predictWithUser1(u, i, userMembers)

def topKUser1(u, i, K=3):
    global userSimArrayObj, trainArrayObj 
    ls = []
    #遍历每个用户
    for otherUser in range(339):
        if  otherUser != u and trainArrayObj[otherUser, i] != 111 :
            ls.append([userSimArrayObj[u, otherUser], otherUser])
    ls.sort(reverse = True)
    return ls[0: min(K, len(ls))]
    
def predictWithUser1(u, i, userMembers):
    global userSimArrayObj, trainArrayObj
    sums = 0.0
    simSum = 0.0
    amplifier = 3.5
    #相似邻居不为空, 则基于相似用户给予评分估计
    for similarity, simUser in userMembers:
        if simUser != u:
            similarity = pow(similarity, amplifier)
            simSum += similarity
            sums += similarity * trainArrayObj[simUser, i]
    return sums/simSum

def slope_knn(u, i):
    userMembers = topKUser2(u)
    return predictWithUser2(u, i, userMembers)

def topKUser2(u, K=3):
    global userSimArrayObj, trainArrayObj
    ls = []
    #遍历每个用户
    for otherUser in range(339):
        if  otherUser != u :
            ls.append([userSimArrayObj[u, otherUser], otherUser])
    ls.sort(reverse = True)
    return ls[0: K]
        
def predictWithUser2(u, i, userMembers):
    global userSimArrayObj, trainArrayObj
    sums = 0.0
    simSum = 0.0
    amplifier = 3.5
    #相似邻居不为空, 则基于相似用户给予评分估计
    for similarity, simUser in userMembers:
        if simUser != u:
            similarity = pow(similarity, amplifier)
            simSum += similarity
            if trainArrayObj[simUser, i] != 111:
                sums += similarity * trainArrayObj[simUser, i]
            else:
                sums += similarity * getPred(simUser, i)
    return sums/simSum
    
    
import math
import numpy as np
import paper
if __name__ == '__main__': 
    simCalMethod = paper.simMinkowskiDist
    sparseness = 5
    fileNumbers = 1
    #参数调整 0.501880807328 1.59444081424
#    maxlens = 50
#    maxlen = 4
#    minSim = 0.55
#    maxDiff = 0.4
    maxlens = 70
    maxlen = 3
    minSim = 0.55
    maxDiff = 0.90
    #文件对象
    for i in range(1, fileNumbers+1):
        #文件对象
        euiFileName = 'result/eui/euiSlopeoneTest-ws2-%d-%d.txt' % (sparseness,i)
        pfEui = open(euiFileName, 'w')
        #load data
        trainFileName = r'dataSet/sparseness%d/training%d.txt' % (sparseness, i)
        trainArrayObj = paper.createArrayObj(trainFileName)
        testFileName = r'dataSet/sparseness%d/test%d.txt' % (sparseness, i)
        testArrayObj = paper.loadTest(testFileName)
        mean = paper.calMean(trainArrayObj)
        var = paper.calVar(trainArrayObj)
        wsAverageVector = paper.columnAverageArray(trainArrayObj, mean) 
         #相似度矩阵数据
        userSimFileName = 'result/simArraySlopeoneUser-%s-%d.txt' % (sparseness,i)
        wsSimFileName = 'result/simArraySlopeoneWs-%s-%d.txt' % (sparseness,i)
#        userSimArrayObj = paper.createSimArray(trainArrayObj, simCalMethod, 1.0)
#        wsSimArrayObj = paper.createSimArray(trainArrayObj.T, simCalMethod, 1.7)
#        paper.save(userSimArrayObj, userSimFileName)
#        paper.save(wsSimArrayObj, wsSimFileName)
        userSimArrayObj = paper.load(userSimFileName)
        wsSimArrayObj = paper.load(wsSimFileName)
#        #计算预测准确
#        for maxlen in [3, 4, 5, 6, 7,8, 9, 10]:
        mae, rmse = calMaeAndRmse() 
        print mae, rmse
#        print mae, rmse
        pfEui.close()
    print 'ok'
    
    
    