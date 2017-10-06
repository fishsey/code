# -*- coding: utf-8 -*-
"""
Created on Sun Mar 06 10:17:13 2016

@author: fishsey
"""
import unittest
import math
import numpy as np
import paper
#constant variable
NoneValue = 111111.0
userNum = 339
wsNum = 5825  

def calMaeAndRmse(trainArrayObj, testArrayObj, wsSimArrayObj, pfEui):
    MAE = 0.0
    RMSE = 0.0
    number = 0
    lens = len(testArrayObj)
    for index in range(lens):
#        print index
        u = int(testArrayObj[index, 0]) 
        i = int(testArrayObj[index, 1]) 
        tui = testArrayObj[index, 2]
        pui = predict(u, i, trainArrayObj, wsSimArrayObj)
        eui = tui - pui
        pfEui.write(str(eui) + '\n')
        RMSE += pow(eui, 2)
        MAE += abs(eui)
        number += 1  
    RMSE = math.sqrt(RMSE / number)
    MAE /= number
    print number, 
    return MAE, RMSE 
    
    
def predict(u, i, trainArrayObj, wsSimArrayObj):
    sums = 0.0
    simSum = 0.0
    vector = trainArrayObj[:, i]
    wsMean = np.mean(vector[vector!= NoneValue])
    for wsId in range(wsNum):
        if wsId != i and trainArrayObj[u, wsId] != NoneValue:
            vector = trainArrayObj[:, wsId]
            wsIdMean = np.mean(vector[vector!= NoneValue])
            simSum += abs(wsSimArrayObj[i, wsId])
            sums += wsSimArrayObj[i, wsId] * (trainArrayObj[u, wsId] - wsIdMean)
    if simSum == 0:
        return wsMean
    return wsMean + sums/simSum

class testClass(unittest.TestCase):
    def testRt(self):
        simCalMethod = paper.simPCC
        sparseness = 20
        fileNumbers = 10
        for i in range(1, fileNumbers+1):
            #文件对象
            print i, 
            euiFileName = 'rt/ipcc/euislopeone-%d-%d.txt' % (sparseness,i)
            pfEui = open(euiFileName, 'w')
            #load data
            trainFileName = r'rt/sparseness%d/training%d.txt' % (sparseness, i)
            trainArrayObj = paper.createArrayObj(trainFileName)
            testFileName = r'rt/sparseness%d/test%d.txt' % (sparseness, i)
            testArrayObj = paper.loadTest(testFileName)
             #相似度矩阵数据
            wsSimFileName = 'rt/ipcc/simArrayWs-%s-%d.txt' % (sparseness,i)
            wsSimArrayObj = paper.createSimArray(trainArrayObj.T, simCalMethod)
            paper.save(wsSimArrayObj, wsSimFileName)
#            wsSimArrayObj = paper.load(wsSimFileName)
            #计算预测准确
            print calMaeAndRmse(trainArrayObj, testArrayObj, wsSimArrayObj, pfEui) 
            pfEui.close()
        print 'ok'
    def testTp(self):
        simCalMethod = paper.simPCC
        sparseness = 20
        fileNumbers = 10
        for i in range(1, fileNumbers+1):
            #文件对象
            print i, 
            euiFileName = 'throught/ipcc/euislopeone-%d-%d.txt' % (sparseness,i)
            pfEui = open(euiFileName, 'w')
            #load data
            trainFileName = r'throught/training%d-%d.txt' % (sparseness, i)
            trainArrayObj = paper.createArrayObj(trainFileName)
            testFileName = r'throught/test%d-%d.txt' % (sparseness, i)
            testArrayObj = paper.loadTest(testFileName)
             #相似度矩阵数据
            wsSimFileName = 'throught/ipcc/simArrayWs-%d-%d.txt' % (sparseness,i)
            wsSimArrayObj = paper.createSimArray(trainArrayObj.T, simCalMethod)
            paper.save(wsSimArrayObj, wsSimFileName)
#            wsSimArrayObj = paper.load(wsSimFileName)
            #计算预测准确
            print calMaeAndRmse(trainArrayObj, testArrayObj, wsSimArrayObj, pfEui) 
            pfEui.close()
        print 'ok'
          
if __name__ == '__main__':
    unittest.main()
    
    
