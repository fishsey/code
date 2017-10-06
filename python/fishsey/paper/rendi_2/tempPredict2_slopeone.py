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
    
def predictByMean(user, ws, label, trainArray=None):
    import numpy as np
    
    userQos = []
    userVector = trainArray[user]
    for qos in userVector:
        if int(qos) in label:
            userQos.append(qos)
    userQos = np.array(userQos)        
            
    wsQos = []       
    wsVector = trainArray[:, ws]
    for qos in wsVector:
        if int(qos) in label:
            wsQos.append(qos)
    wsQos = np.array(wsQos)
    
    if len(userQos) > 0 and len(wsQos) > 0:
        return np.mean(userQos)/2.0 + np.mean(wsQos)/ 2.0
    elif len(userQos) > 0:
        return np.mean(userQos)
    elif len(wsQos) > 0:
        return np.mean(wsQos)
    else:
        return label[0] + 0.5
        
def calMaeAndRmse():
    global trainArrayObj, testArrayObj, labels
    #cal the mae and rmse
    MAE = 0.0
    RMSE = 0.0
    number = 0
    lens = len(testArrayObj)
    euiResult = []
    global indexRow
    for indexRow in range(lens):
#        print index,
        u = int(testArrayObj[indexRow, 0]) 
        i = int(testArrayObj[indexRow, 1]) 
        tui = testArrayObj[indexRow, 2]
        userMemberlist = topKUser(u, i)
        wsMemberlist = topKWs(u, i) 
        flag, pui = predictSlope(u, i, userMemberlist, wsMemberlist)
        if flag:
            pui = modify(u, i, flag) or pui
#            
#        if int(pui) not in labels[index]:
#            pui = predictByMean(u, i, labels, trainArrayObj)
            
        eui = tui - pui
        euiResult.append(eui)
        # write data to the file
        RMSE += pow(eui, 2)
        MAE += abs(eui)
        number += 1.0  
        
#        print MAE/number, math.sqrt(RMSE / number)
        
    RMSE = math.sqrt(RMSE / number)
    MAE /= number
    return MAE, RMSE, euiResult   
  
def predictSlope(u, i, userMemberlist, wsMemberlist):
    global maxDiff, trainArrayObj, wsAverageVector, mean, var
    global labels
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
                
                 if int(rating[index]) not in labels[indexRow] :
                     continue
        
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
import paperTools as pp
if __name__ == '__main__': 
    NoneValue = 111111.0
    userNum = 339
    wsNum = 5825
    simCalMethod = pp.simMinkowskiDist
    #load data
    maxlens = 100
    maxlen = 3
    #参数调整 0.519026063656 1.64540380436
    amplifier = 3.5
    maxDiff = 1.5
    minSim = 0.55
    #文件对象
    for sparseness in [5, 10, 15, 20]:
        for fileNum in range(1, 11):
            print sparseness, fileNum, 
            #load data
            trainFileName = "/root/AAA/dataset/rendi2/train/sparseness%s/training%d.txt" % (sparseness, fileNum)
            trainArrayObj = pp.createArrayObj(trainFileName)
            mean = pp.calMean(trainArrayObj)
            wsAverageVector = pp.columnAverageArray(trainArrayObj, mean) 
            
            
            testFileName = "/root/AAA/dataset/rendi2/temp/temp-%d-%d"    % (sparseness, fileNum)   
            testArrayObj = pp.loadTest(testFileName)
            
            #load the prob dataset
            trainProb = "/root/AAA/dataset/rendi2/temp/temp-%d-%d-prob" % (sparseness, fileNum)     
            kProb = 1
            trainProbData = np.loadtxt(trainProb)
            labels = np.argsort(trainProbData, axis=1)[:, -kProb:] - 1 #top-k labels
            labelsProb = np.sort(trainProbData, axis=1)[:, -kProb:]#top-k labels's 
            
            #相似度矩阵数据
            userSimFileName = '/root/AAA/dataset/rendi2/slopeoneSim/rt-simArraySlopeoneUser-%d-%d.txt' % (sparseness, fileNum)
            wsSimFileName = '/root/AAA/dataset/rendi2/slopeoneSim/rt-simArraySlopeoneWs-%d-%d.txt' % (sparseness, fileNum)
            userSimArrayObj = pp.load(userSimFileName)
            wsSimArrayObj = pp.load(wsSimFileName)
            
    
           #计算预测准确
#            for maxDiff in [0.01, 0.05, 0.1, 0.3, 0.7, 1.0, 2.0, 4.0]:
#                for minSim in [0.05, 0.1, 0.2, 0.4, 0.6, 0.8]:
            mae, rmse, euiResult = calMaeAndRmse() 
            print mae, rmse
            np.savetxt("/root/AAA/dataset/rendi2/eui/euiSlopeone-%d-%d" % (sparseness, fileNum), np.array(euiResult, dtype=str), fmt='%s', delimiter='\t')
    
    
    
    