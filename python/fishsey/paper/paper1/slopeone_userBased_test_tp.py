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
        if trainArrayObj[otherUser, i] != NoneValue and otherUser != u and userSimArrayObj[u, otherUser] > minSim:
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
        if trainArrayObj[u, otherWs]!=NoneValue and otherWs != i and wsSimArrayObj[i,otherWs] > minSim:
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
            pui = modify(u, i, flag) or pui
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
    columnNum = trainArrayObj.shape[1]
    differ = np.zeros(columnNum)
    numbers = np.zeros(columnNum)
    dev = np.zeros(columnNum)
    rating = trainArrayObj[u]
    #依次遍历每列
    flag1 = 0
    flag2 = 0
    flag = 0
    for index in wsMemberlist:
        for memberId in userMemberlist:
            if rating[index] != NoneValue and index != i and memberId != u and trainArrayObj[memberId,index] != NoneValue and trainArrayObj[memberId,i] != NoneValue and abs(rating[index] - trainArrayObj[memberId,index]) <= maxDiff:
                 if trainArrayObj[memberId,index] == -1 or trainArrayObj[u,index] == -1 :
                     flag1 = 1
                 if trainArrayObj[memberId,i] == -1:
                     flag2 = 1
                 differ[index] += (trainArrayObj[memberId,i] - trainArrayObj[memberId,index]) 
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
    for otherUser in range(userNum):
        if  otherUser != u and trainArrayObj[otherUser, i] != NoneValue :
            ls.append([userSimArrayObj[u, otherUser], otherUser])
    ls.sort(reverse = True)
    return ls[0: min(K, len(ls))]
    
def predictWithUser1(u, i, userMembers):
    global userSimArrayObj, trainArrayObj
    sums = 0.0
    simSum = 0.0
    #相似邻居不为空, 则基于相似用户给予评分估计
    for similarity, simUser in userMembers:
        if simUser != u:
            similarity = pow(similarity, amplifier)
            simSum += similarity
            sums += similarity * trainArrayObj[simUser, i]
    if simSum == 0:
        return 0
    return sums/simSum

def slope_knn(u, i):
    userMembers = topKUser2(u)
    return predictWithUser2(u, i, userMembers)

def topKUser2(u, K=3):
    global userSimArrayObj, trainArrayObj
    ls = []
    #遍历每个用户
    for otherUser in range(userNum):
        if  otherUser != u :
            ls.append([userSimArrayObj[u, otherUser], otherUser])
    ls.sort(reverse = True)
    return ls[0: K]
        
def predictWithUser2(u, i, userMembers):
    global userSimArrayObj, trainArrayObj
    sums = 0.0
    simSum = 0.0
    #相似邻居不为空, 则基于相似用户给予评分估计
    for similarity, simUser in userMembers:
        if simUser != u:
            similarity = pow(similarity, amplifier)
            simSum += similarity
            if trainArrayObj[simUser, i] != NoneValue:
                sums += similarity * trainArrayObj[simUser, i]
            else:
                sums += similarity * getPred(simUser, i)
    if simSum == 0:
        return 0
    return sums/simSum
    
  
    
    
import math
import numpy as np
import paper
if __name__ == '__main__': 
    NoneValue = 111111.0
    userNum = 339
    wsNum = 5825
    simCalMethod = paper.simMinkowskiDist
    #load data
    maxlens = 60
    maxlen = 3
    print maxlen
    #参数调整 0.519026063656 1.64540380436
    amplifier = 3.5
    maxDiff = 0.90
    i = 1
#    minSim = 0.55
    #文件对象
    for sparseness in np.arange(2, 31, 2):
        #文件对象
        euiFileName = 'sample/tp-slopeone-%d.txt' % (sparseness)
        pfEui = open(euiFileName, 'w')
        #load data
        trainFileName = r'sample/tp-training%d.txt' % (sparseness)
        trainArrayObj = paper.createArrayObj(trainFileName)
        print np.mean(trainArrayObj[trainArrayObj != NoneValue])
#        testFileName = r'sample/tp-test%d.txt' % (sparseness)
#        testArrayObj = paper.loadTest(testFileName)
#        mean = paper.calMean(trainArrayObj)
#        wsAverageVector = paper.columnAverageArray(trainArrayObj, mean) 
#         #相似度矩阵数据
#        userSimFileName = 'sample/tp-simArraySlopeoneUser-%s-%d.txt' % (sparseness,i)
#        wsSimFileName = 'sample/tp-simArraySlopeoneWs-%s-%d.txt' % (sparseness,i)
##        userSimArrayObj = paper.createSimArray(trainArrayObj, simCalMethod, 1.0)
##        wsSimArrayObj = paper.createSimArray(trainArrayObj.T, simCalMethod, 1.7)
##        paper.save(userSimArrayObj, userSimFileName)
##        paper.save(wsSimArrayObj, wsSimFileName)
#        userSimArrayObj = paper.load(userSimFileName)
#        wsSimArrayObj = paper.load(wsSimFileName)
#        minSim = (np.mean(userSimArrayObj) + np.mean(wsSimArrayObj))/2.0
##        print minSim, 
#        #计算预测准确
#        mae, rmse = calMaeAndRmse() 
#        print mae, rmse
#        pfEui.close()
    print 'ok'
    
    
    
    