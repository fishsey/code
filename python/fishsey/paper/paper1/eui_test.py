# -*- coding: utf-8 -*-
"""
Created on Thu Mar 10 12:25:34 2016

@author: fishsey
"""
import numpy as np
import sys
import unittest
import paper
        
def getMaeAndRmse(a, b, lamda):
    result = a * lamda + b * (1- lamda)
    mae, rmse = paper.maeAndRmse(result) 
    return mae, rmse
    
def findBestMatch(a, b):
    lamda = 0.0
    minMae = sys.maxint
    minRmse = sys.maxint
    minLamda = -1
    while lamda <= 1.0:
        result = a * lamda + b * (1- lamda)
        print lamda,
        tempMae, tempRmse = paper.maeAndRmse(result) 
        if tempMae < minMae:
            minMae = tempMae
            minRmse = tempRmse
            minLamda = lamda
        lamda += 0.1
    print minLamda, minMae, minRmse


def calAverageMaeAndRmse(start=1, end=10, sparseness=5):
    maeSums = rmseSums = 0.0
    for i in range(start, end+1):
#        userEuiFile = r'rt/eui/slopeone-%d-%d.txt' % (sparseness,i)
        userEuiFile = r'throught/eui/slopeone-%d-%d.txt' % (sparseness,i)
        result = np.loadtxt(userEuiFile)
        mae, rmse = paper.maeAndRmse(result) 
        maeSums += mae
        rmseSums += rmse
    print maeSums/(end - start + 1), rmseSums/(end - start + 1)
  
    
class testClass(unittest.TestCase):
#    def testSlopeone(self):
#        calAverageMaeAndRmse(start=1, end=10, sparseness=5)
#        calAverageMaeAndRmseTopK(start=1, end=100, sparseness=5, k=10)
    def testHybrid(self):
        sparseness = 5
        fileNumbers = 1
        for i in range(1, fileNumbers+1):
            pqEui = r'throught/eui/euipq-%d-%d.txt' % (sparseness,i)
            slopeoneEui = r'throught/eui/slopeone-%d-%d.txt' % (sparseness,i) 
            pqEui = np.loadtxt(pqEui)
            slopeoneEui = np.loadtxt(slopeoneEui)
            findBestMatch(pqEui, slopeoneEui)
#    def testHybridAverage(self):
#        lamda = 0.6
#        sparseness = 5
#        fileNumbers = 20
#        maeSums = 0.0
#        for i in range(1, fileNumbers+1):
#            pqEui = r'result/eui/pq/euipq-%d-%d.txt' % (sparseness,i)
#            slopeoneEui = r'result/eui/slopeone/euiSlopeoneTest-user-%d-%d.txt' % (sparseness,i) 
#            pqEui = np.loadtxt(pqEui)
#            slopeoneEui = np.loadtxt(slopeoneEui)
#            maeSums += getMaeAndRmse(pqEui, slopeoneEui, lamda)[0]
#        print maeSums/fileNumbers
#            
         
if __name__ == '__main__':
    unittest.main()
    
        
       
            