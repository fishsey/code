# -*- coding: utf-8 -*-
"""
Created on Sun Mar 06 10:17:13 2016

@author: fishsey
"""
#constant variable
NoneValue = 111111.0
userNum = 339
wsNum = 5825  

def calMaeAndRmse():
    MAE = 0.0
    RMSE = 0.0
    number = 0
    lens = len(testArrayObj)
    for index in range(lens):
#        print index
        u = int(testArrayObj[index, 0]) 
        i = int(testArrayObj[index, 1]) 
        tui = testArrayObj[index, 2]
        pui = predict(u, i)
        eui = tui - pui
        pfEui.write(str(eui) + '\n')
        RMSE += pow(eui, 2)
        MAE += abs(eui)
        number += 1  
    RMSE = math.sqrt(RMSE / number)
    MAE /= number
    print number, 
    return MAE, RMSE 
    
    
def predict(u, i):
    sums = 0.0
    simSum = 0.0
    vector = trainArrayObj[u]
    userMean = np.mean(vector[vector!= NoneValue])
    for userId in range(userNum):
        if userId != u and trainArrayObj[userId, i] != NoneValue:
            vector = trainArrayObj[userId]
            userIdMean = np.mean(vector[vector!= NoneValue])
            simSum += abs(userSimArrayObj[u, userId])
            sums += userSimArrayObj[u, userId] * (trainArrayObj[userId, i] - userIdMean)
    return userMean + sums/simSum

import math
import numpy as np
import paper
if __name__ == '__main__': 
    simCalMethod = paper.simPCC
    fileNumbers = 10
    for sparseness in [5, 10, 15, 20]:
        #文件对象
        for i in range(1, fileNumbers+1):
            print i,
#            euiFileName = 'rt/upcc/euislopeone-%d-%d.txt' % (sparseness,i)
            euiFileName = 'throught/upcc/euislopeone-%d-%d.txt' % (sparseness,i)
            pfEui = open(euiFileName, 'w')
            #load data
#            trainFileName = r'rt/sparseness%d/training%d.txt' % (sparseness, i)
#            testFileName = r'rt/sparseness%d/test%d.txt' % (sparseness, i)
            #throught
            trainFileName = r'throught/training%d-%d.txt' % (sparseness, i)
            testFileName = r'throught/test%d-%d.txt' % (sparseness, i)
            trainArrayObj = paper.createArrayObj(trainFileName)
            testArrayObj = paper.loadTest(testFileName)
             #相似度矩阵数据
#            userSimFileName = 'rt/upcc/simArrayUser-%s-%d.txt' % (sparseness,i)
            userSimFileName = 'throught/upcc/simArrayUser-%s-%d.txt' % (sparseness,i)
            userSimArrayObj = paper.createSimArray(trainArrayObj, simCalMethod)
            paper.save(userSimArrayObj, userSimFileName)
    #        userSimArrayObj = paper.load(userSimFileName)
            #计算预测准确
            mae, rmse = calMaeAndRmse() 
            print mae, rmse
            pfEui.close()
    print 'ok'