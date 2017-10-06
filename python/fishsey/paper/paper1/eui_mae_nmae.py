# -*- coding: utf-8 -*-
"""
Created on Sat May 28 14:25:24 2016

@author: fishsey
"""
import unittest
path = 'F:/baiduYun/Syn/dataset/paper1'

def getMae(a, b, lamda):
    import numpy as np
    eui = a * lamda + b * (1- lamda)
    mae = np.mean(np.abs(eui))
    return mae

def getNmae(mae, rui):
    import numpy as np
    nmae = mae / np.mean(rui)
    return nmae
    
def calmaeAndNmae(euiFile, testFileName):
    import paper
    import numpy as np
    #cal the mae
    eui = np.loadtxt(euiFile)
    mae = np.mean(np.abs(eui))
    #cal the NMAE
    testArrayObj = paper.loadTest(testFileName)
    mean = np.mean(testArrayObj[:, 2])
    nmae = mae/mean
    return mae, nmae

def calAverageMaeAndNmae():
    fileNumbers = 10
    for sparseness in [5, 10, 15, 20]:
        maeSums = nmaeSums = 0.0
        for i in range(1, fileNumbers+1):
            eui = path + '/tp/eui/slopeone-%d-%d.txt' % (sparseness,i)
            test = path + '/tp/sparseness%d/test%d.txt' % (sparseness,i)
            mae, nmae = calmaeAndNmae(eui, test)
            maeSums += mae
            nmaeSums += nmae
        print maeSums/fileNumbers, nmaeSums/fileNumbers


def findBestMatch(a, b):
    import sys
    import numpy as np
    lamda = 0.0
    minMae = sys.maxint
    while lamda <= 1.0:
        result = a * lamda + b * (1- lamda)
        tempMae = np.mean(np.abs(result))
        print lamda, tempMae
        if tempMae < minMae:
            minMae = tempMae
            minLamda = lamda
        lamda += 0.1
    return minLamda, minMae
    
def matches():
    import numpy as np
    sparseness = 20
    fileNumbers = 5
    for i in range(5, fileNumbers+1):
        print i,
        eui1 = path + 'tp/eui/euipq-%d-%d.txt' % (sparseness,i)
        eui2 = path + 'tp/eui/slopeone-%d-%d.txt' % (sparseness,i)
        eui1 = np.loadtxt(eui1)
        eui2 = np.loadtxt(eui2)
        print findBestMatch(eui1, eui2)
                 
class testClass(unittest.TestCase):
    def testSlopeone(self):
        calAverageMaeAndNmae()
#    def testHybrid(self):
#        matches()
#    def testHybridAverage(self):
#        import numpy as np
#        import paper
#        lamda = 0.6
#        fileNumbers = 10
#        for sparseness in [5, 10, 15, 20]:
#            maeSums = nmaeSums = 0.0
#            for i in range(1, fileNumbers+1):
#                pqEui = r'F:/baiduYun/Syn/dataset/tp/eui/euipq-%d-%d.txt' % (sparseness,i)
#                slopeoneEui = r'F:/baiduYun/Syn/dataset/tp/eui/slopeone-%d-%d.txt' % (sparseness,i)
##                testFileName = r'F:/baiduYun/Syn/dataset/tp/sparseness%d/test%d.txt' % (sparseness,i)
#                testFileName = r'F:/baiduYun/Syn/dataset/tp/test%d-%d.txt' % (sparseness,i)
#                pqEui = np.loadtxt(pqEui)
#                slopeoneEui = np.loadtxt(slopeoneEui)
#                mae = getMaeAndRmse(pqEui, slopeoneEui, lamda)[0]
#                maeSums += mae
#                #cal the NMAE
#                testArrayObj = paper.loadTest(testFileName)
#                aveMean = np.mean(testArrayObj[:, 2])
#                nmaeSums += mae/aveMean
#            print maeSums/fileNumbers, nmaeSums/fileNumbers
if __name__ == '__main__':
    unittest.main()