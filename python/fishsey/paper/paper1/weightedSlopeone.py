# -*- coding: utf-8 -*-
"""
Created on Wed May 25 16:18:25 2016

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
fileNumbers = 3


def calMaeAndRmse(trainArrayObj, testArrayObj, pfEui):
    MAE = 0.0
    RMSE = 0.0
    number = 0
    lens = len(testArrayObj)
    for index in range(lens):
        u = int(testArrayObj[index, 0]) 
        i = int(testArrayObj[index, 1]) 
        tui = testArrayObj[index, 2]
        pui = predictSlope(u, i, trainArrayObj)
        eui = tui - pui
        pfEui.write(str(eui) + '\n')
        RMSE += pow(eui, 2)
        MAE += abs(eui)
        number += 1  
    RMSE = math.sqrt(RMSE / number)
    MAE /= number
    print number, 
    return MAE, RMSE 
    
    
def predictSlope(u, i, trainArrayObj):
    #初始化参数
    columnNum = trainArrayObj.shape[1]
    differ = np.zeros(columnNum)
    numbers = np.zeros(columnNum)
    dev = np.zeros(columnNum)
    rating = trainArrayObj[u]
    #依次遍历每列
    for index in range(wsNum):
        for memberId in range(userNum):
            if rating[index] != NoneValue and index != i and memberId != u and trainArrayObj[memberId,index] != NoneValue and trainArrayObj[memberId,i] != NoneValue :
                 differ[index] += (trainArrayObj[memberId,i] - trainArrayObj[memberId,index]) 
                 numbers[index] += 1.0
    for index in range(columnNum):
        if numbers[index]:
            dev[index] = differ[index]/ numbers[index]
    # 进行预测
    rating = rating + dev
    sums = sum(numbers)
    if sums:
        return (np.mat(rating) * np.mat(numbers).T)[0,0] / sums
    

class testClass(unittest.TestCase):
#    def testRt(self):
#        for sparseness in  [5, 10, 15, 20]:
#            for i in range(1, fileNumbers+1):
#                #文件对象
#                print i, 
#                euiFileName = 'rt/weightedslopeone/euislopeone-%d-%d.txt' % (sparseness,i)
#                pfEui = open(euiFileName, 'w')
#                #load data
#                trainFileName = r'rt/sparseness%d/training%d.txt' % (sparseness, i)
#                trainArrayObj = paper.createArrayObj(trainFileName)
#                testFileName = r'rt/sparseness%d/test%d.txt' % (sparseness, i)
#                testArrayObj = paper.loadTest(testFileName)
#                #计算预测准确
#                print calMaeAndRmse(trainArrayObj, testArrayObj, pfEui) 
#                pfEui.close()
#            print 'ok'
    def testTp(self):
        for sparseness in  [5, 10, 15, 20]:
            for i in range(1, fileNumbers+1):
                #文件对象
                print i, 
                euiFileName = 'throught/weightedslopeone/euislopeone-%d-%d.txt' % (sparseness,i)
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