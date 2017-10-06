# -*- coding: utf-8 -*-
"""
Created on Fri Feb 19 14:07:50 2016

@author: fishsey
"""
from math import sqrt
import numpy as np
import paper
import unittest

#constant variable
NoneValue = 111111.0
userNum = 339
wsNum = 5825
feature = 80 #特征数
n = 20#迭代次数
lamd = 0.02 #正则化参数(惩罚因子)
fileNumbers = 10

def predict(u, i, p, q):
    return  (p[u] * q[i].T)[0,0]

def initTrainMatrix():
    p = np.random.rand(userNum, feature) * (1/sqrt(feature))
    q = np.random.rand(wsNum, feature) * (1/sqrt(feature))
    return np.mat(p), np.mat(q)
    
def learningAddIndicateFunctionlfm(trainArrayObj):
    alpha = 0.01
    p, q = initTrainMatrix()
    for step in range(0, n):
        number = 0
        mae = 0
        for u in range(userNum):
            for i in range(wsNum):
                if trainArrayObj[u, i] != NoneValue:
                    number += 1 #number 记录训练集中的评分总数
                    rui = trainArrayObj[u, i]
                    pui = predict(u, i, p, q)
                    eui = rui - pui
                    mae += abs(eui)
                    #修正 p\q                 
                    temp = p[u,] + alpha * (q[i, ] * eui - lamd * p[u,])
                    q[i,] += alpha * (p[u,] * eui - lamd * q[i,])
                    p[u,] = temp  
        nowMae = mae / number
        print 'step: %d      mae: %f' % ((step + 1), nowMae)
        alpha *= 0.9
    return p, q
    
def maeAndRmseRt(p, q, testArrayObj, pfEui):
    euiList = []
    for index in range(len(testArrayObj)):
        u = int(testArrayObj[index, 0]) 
        i = int(testArrayObj[index, 1])
        tui = testArrayObj[index, 2]
        pui = (p[u] * q[i].T)[0,0]
        euiTemp = tui - pui
        euiList.append(euiTemp)
        pfEui.write(str(euiTemp) + '\n')
    paper.maeAndRmse(euiList)    
    
def maeAndRmseTp(p, q, testArrayObj, pfEui):
    euiList = []
    for index in range(len(testArrayObj)):
        u = int(testArrayObj[index, 0]) 
        i = int(testArrayObj[index, 1])
        tui = testArrayObj[index, 2]
        pui = (p[u] * q[i].T)[0,0] * 107.439 + 44.034
        euiTemp = tui - pui
        euiList.append(euiTemp)
        pfEui.write(str(euiTemp) + '\n')
    paper.maeAndRmse(euiList)     

class testClass(unittest.TestCase):
    def testRt(self):
        for sparseness in [5, 10, 15, 20]:
            for i in range(1, fileNumbers+1):
                #文件对象
                print i, 
                euiFileName = 'rt/mf/euislopeone-%d-%d.txt' % (sparseness,i)
                pfEui = open(euiFileName, 'w')
                #load data
                trainFileName = r'rt/sparseness%d/training%d.txt' % (sparseness, i)
                trainArrayObj = paper.createArrayObj(trainFileName)
                testFileName = r'rt/sparseness%d/test%d.txt' % (sparseness, i)
                testArrayObj = paper.loadTest(testFileName)
                #计算预测准确
                p, q =  learningAddIndicateFunctionlfm(trainArrayObj)
                maeAndRmseRt(p, q, testArrayObj, pfEui)
                pfEui.close()
            print 'ok'
    def testTp(self):
        for sparseness in [5, 10, 15, 20]:
            for i in range(1, fileNumbers+1):
                #文件对象
                print i, 
                euiFileName = 'throught/mf/euislopeone-%d-%d.txt' % (sparseness,i)
                pfEui = open(euiFileName, 'w')
                #load data
                trainFileName = r'throught/training%d-%d.txt' % (sparseness, i)
                trainArrayObj = paper.createArrayObj(trainFileName)
                testFileName = r'throught/test%d-%d.txt' % (sparseness, i)
                testArrayObj = paper.loadTest(testFileName)
                #计算预测准确
                trainArrayObj[trainArrayObj != NoneValue] = (trainArrayObj[trainArrayObj != NoneValue] - 44.034) / 107.439
                p, q =  learningAddIndicateFunctionlfm(trainArrayObj)
                maeAndRmseTp(p, q, testArrayObj, pfEui) 
                pfEui.close()
            print 'ok'
          
if __name__ == '__main__':
    unittest.main()    
 
        
