# -*- coding: utf-8 -*-
"""
Created on Thu Jun 29 23:31:30 2017

@author: root
"""
import numpy as np
NoneValue = 111111

def sample(sparess, fileName = r'/root/AAA/dataset/qos/tp/tpmatrix.txt', userNum=339, wsNum=5825):
    originalData = np.loadtxt(fileName)
    trainResult = []
    samleNum = userNum * wsNum * sparess / 100.0
    while samleNum > 0:
        u = np.random.randint(0, userNum)
        i = np.random.randint(0, wsNum)
        if originalData[u, i] != NoneValue:
            trainResult.append([u, i, originalData[u, i]])
            originalData[u, i] = NoneValue
            samleNum = samleNum - 1
#            print samleNum,
    
    testResult = []
    samleNum = userNum * wsNum * sparess / 100.0 / 10
    while samleNum > 0:
        u = np.random.randint(0, userNum)
        i = np.random.randint(0, wsNum)
        if originalData[u, i] != NoneValue:
            testResult.append([u, i, originalData[u, i]])
            originalData[u, i] = NoneValue
            samleNum = samleNum - 1
#            print samleNum,
    return np.array(trainResult), np.array(testResult)

   
if __name__ == "__main__":
    for sparess in [5, 10, 15, 20]:
        for fileNum in range(1, 11):    
            print sparess, fileNum
            trainFile = "/root/AAA/dataset/qos/tp/train/sparseness%s/training%d.txt" % (sparess, fileNum)   
            testFile = "/root/AAA/dataset/qos/tp/test/sparseness%s/test%d.txt" % (sparess, fileNum)    
            trainArray, testArray = sample(sparess)
            np.savetxt(trainFile, trainArray, fmt='%s')
            np.savetxt(testFile, testArray, fmt='%s')
            