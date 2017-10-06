# -*- coding: utf-8 -*-
"""
Created on Fri Apr 15 14:49:37 2016

@author: fishsey
"""
import numpy as np
import paper
import unittest

def loadPQRt(sparseness = 5, number = 1):
    pFile = r'rt/p-%d-%d.txt' % (sparseness, number)
    qFile = r'rt/q-%d-%d.txt' % (sparseness, number)
    p = paper.load(pFile)
    q = paper.load(qFile)
    return p, q
    
def predWithPQRt(testDataArray, pf, p, q):
    euiList = []
    for index in range(len(testDataArray)):
        u = int(testDataArray[index, 0]) 
        i = int(testDataArray[index, 1])
        tui = testDataArray[index, 2]
        pui = (p[u] * q[i].T)[0, 0]
        euiTemp = tui - pui
        pf.write(str(euiTemp) + '\n')
        euiList.append(euiTemp)
    paper.maeAndRmse(euiList) 
    
def loadPQTp(sparseness = 5, number = 1):
    pFile = r'throught/p-%d-%d.txt' % (sparseness, number)
    qFile = r'throught/q-%d-%d.txt' % (sparseness, number)
    p = paper.load(pFile)
    q = paper.load(qFile)
    return p, q
    
    
def predWithPQTp(testDataArray, pf, p, q):
    euiList = []
    for index in range(len(testDataArray)):
        u = int(testDataArray[index, 0]) 
        i = int(testDataArray[index, 1])
        tui = testDataArray[index, 2]
        pui = (p[u] * q[i].T)[0, 0] * 107.439 + 44.034
        euiTemp = tui - pui
        pf.write(str(euiTemp) + '\n')
        euiList.append(euiTemp)
    paper.maeAndRmse(euiList) 


class testClass(unittest.TestCase):
    def testRt(self):
        sparseness = 5
        fileNumbers = 2
        for i in range(1, fileNumbers+1):
            p, q = loadPQRt(sparseness, i)
            pfEui = 'rt/eui/euipq-%d-%d.txt' % (sparseness, i)
            pf = open(pfEui,'w')
            #测试测试数据
            testFileName = r'rt/sparseness%d/test%d.txt' % (sparseness, i)
            testArrayObj = paper.loadTest(testFileName)
            predWithPQRt(testArrayObj, pf, p, q)   
            pf.close()  
    def testTp(self):
        sparseness = 5
        fileNumbers = 2
        for i in range(1, fileNumbers+1):
            p, q = loadPQTp(sparseness, i)
            pfEui = 'throught/eui/euipq-%d-%d.txt' % (sparseness, i)
            pf = open(pfEui,'w')
            #测试测试数据
            testFileName = r'throught/test%d-%d.txt' % (sparseness, i)
            testArrayObj = paper.loadTest(testFileName)
            predWithPQTp(testArrayObj, pf, p, q)   
            pf.close()
  
if __name__ == '__main__':
    unittest.main()
        
