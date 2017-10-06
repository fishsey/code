# -*- coding: utf-8 -*-
"""
Created on Fri Apr 22 10:34:38 2016

@author: fishsey
"""
import numpy as np
import unittest

NoneValue = 111111.0

def loadOriginalData(fileName = r'/root/AAA/dataset/rtmatrix.txt'):
    originalData = np.loadtxt(fileName)
    return originalData


       
       
class testClass(unittest.TestCase):
    def testSample(self):
        for ration in np.arange(2, 21, 2):
            for fileNum in range(1, 11):
                print ration, fileNum
                fileName1 = r'/root/AAA/dataset/rendi2/sample/rt-training%d-%d' % (ration,fileNum)
                fileName2 = r'/root/AAA/dataset/rendi2/sample/rt-test%d-%d' % (ration,fileNum)
                sample(ration/100.0, fileName1, fileName2) 
#    def test2(self):
#        import paper
#        data = paper.loadOriginalData()
#        print np.min(data),  np.max(data), np.mean(data), np.median(data), np.std(data)
#        print np.min(data[data!=-1]),  np.max(data[data!=-1]), np.mean(data[data!=-1]), np.median(data[data!=-1]), np.std(data[data!=-1])
               

if __name__ == '__main__':
    unittest.main()