# -*- coding: utf-8 -*-
"""
Created on Tue May 24 18:20:13 2016

@author: fishsey
"""

import unittest
import paper
import math
import numpy as np
#constant variable
NoneValue = 111111.0
userNum = 339
wsNum = 5825

def calMaeAndRmse(trainArrayObj, testArrayObj, pfEui):
    MAE = 0.0
    RMSE = 0.0
    number = 0
    lens = len(testArrayObj)
    for index in range(lens):
        u = int(testArrayObj[index, 0]) 
        tui = testArrayObj[index, 2]
        vector = trainArrayObj[u]
        pui = np.mean(vector[vector!=NoneValue])
        eui = tui - pui
        pfEui.write(str(eui) + '\n')
        RMSE += pow(eui, 2)
        MAE += abs(eui)
        number += 1  
    RMSE = math.sqrt(RMSE / number)
    MAE /= number
    print number,
    return MAE, RMSE   

class testClass(unittest.TestCase):
    def testRt(self):
        sparseness = 5
        fileNumbers = 10
        for i in range(1, fileNumbers+1):
            #文件对象
            print i, 
            euiFileName = 'rt/userMean/euislopeone-%d-%d.txt' % (sparseness,i)
            pfEui = open(euiFileName, 'w')
            #load data
            trainFileName = r'rt/sparseness%d/training%d.txt' % (sparseness, i)
            trainArrayObj = paper.createArrayObj(trainFileName)
            testFileName = r'rt/sparseness%d/test%d.txt' % (sparseness, i)
            testArrayObj = paper.loadTest(testFileName)
            #计算预测准确
            print calMaeAndRmse(trainArrayObj, testArrayObj, pfEui) 
            pfEui.close()
        print 'ok'
    def testTp(self):
        sparseness = 5
        fileNumbers = 10
        for i in range(1, fileNumbers+1):
            #文件对象
            print i, 
            euiFileName = 'throught/userMean/euislopeone-%d-%d.txt' % (sparseness,i)
            pfEui = open(euiFileName, 'w')
            #load data
            trainFileName = r'throught/training%d-%d.txt' % (sparseness, i)
            trainArrayObj = paper.createArrayObj(trainFileName)
            testFileName = r'throught/test%d-%d.txt' % (sparseness, i)
            testArrayObj = paper.loadTest(testFileName)
            #计算预测准确
            print calMaeAndRmse(trainArrayObj, testArrayObj, pfEui) 
            pfEui.close()
        print 'ok'
          
if __name__ == '__main__':
    unittest.main()