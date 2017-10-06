# -*- coding: utf-8 -*-
"""
Created on Thu Jun 23 16:38:07 2016

@author: fishsey
"""
def loadData(*dataFile):
    import paper
    train = paper.loadTest(dataFile[0])
    test = paper.loadTest(dataFile[1])
    return train, test
    
def loadPQ(*fileName):
    import paper 
    p = paper.load(fileName[0]).A
    q = paper.load(fileName[1]).A
    return p, q



def createInfos(arrayObj, p, q, saveFileName):
    import numpy as np
    result = np.empty((len(arrayObj), p.shape[1] + q.shape[1] + 1))
    for index, line in enumerate(arrayObj):
        print index
        temp = []
        u, i, rt = line[0], line[1], line[2]
        pInfo = p[u]
        qInfo = q[i]
        temp.extend(pInfo)
        temp.extend(qInfo)
        temp.append(rt)
        result[index] = temp
    np.savetxt(saveFileName, result, fmt='%s', delimiter='\t')


import unittest
class testClass(unittest.TestCase):
    def test1(self):
        trainFile = 'rt/sparseness5/training1.txt'
        testFile = 'rt/sparseness5/test1.txt'  
        pFile = 'rt/p-5-1.txt'
        qFile = 'rt/q-5-1.txt'
        train, test = loadData(trainFile, testFile)
        p, q = loadPQ(pFile, qFile)
        createInfos(train, p, q, 'trainingInfo-5-1.txt')
        createInfos(test, p, q, 'testInfo-5-1.txt')
        
        
        
if __name__ == '__main__':
    unittest.main()
