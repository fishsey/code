# -*- coding: utf-8 -*-
"""
Created on Wed Jun 22 13:34:52 2016

@author: fishsey
"""
NoneValue = 111111.0
#==============================================================================
# cal the mean and std
#==============================================================================
def createArrayObj(fileName, userNum=339, wsNum=5825):
    import numpy as np
    trainObj = np.loadtxt(fileName)
    userLs, wsLs, rt  = trainObj[:, 0], trainObj[:, 1], trainObj[:, 2]
    userLs = np.array(userLs, dtype=int) - 1
    wsLs = np.array(wsLs, dtype=int) - 1
    rt = np.array(rt, dtype=float)
    arrayObj = np.empty((userNum, wsNum))
    arrayObj.fill(NoneValue)
    arrayObj[userLs, wsLs] = rt 
    return arrayObj 
def calRowMean(arrayObj):
    import numpy as np
    result = np.empty(len(arrayObj))
    for index, line in enumerate(arrayObj):
        result[index] = np.mean(line[line != NoneValue])
    return result 
def calRowStd(arrayObj):
    import numpy as np
    result = np.empty(len(arrayObj))
    for index, line in enumerate(arrayObj):
        result[index] = np.std(line[line != NoneValue])
    return result
def calMeanAndStd(trainFile):
    trainArrayObj = createArrayObj(trainFile)
    userMean = calRowMean(trainArrayObj)
    userStd = calRowStd(trainArrayObj)
    wsMean = calRowMean(trainArrayObj.T)
    wsStd = calRowStd(trainArrayObj.T)
    return userMean, userStd, wsMean, wsStd

#==============================================================================
# cal the feature vector
#==============================================================================
def createFeatureVec(trainFile, testFile):
    import numpy as np
    userMean, userStd, wsMean, wsStd = calMeanAndStd(trainFile)
    #translate training dataset
    trainData = np.loadtxt(trainFile)
    trainData[:, 0] -= 1
    trainData[:, 1] -= 1
    samplesNum = trainData.shape[0]
    featuresNum = 5
    result = np.empty((samplesNum, featuresNum))
    for index, line in enumerate(trainData):
        temp = []
        user, ws, rt = line[0], line[1], line[2]
        temp.append(userMean[user])
        temp.append(userStd[user])
        temp.append(wsMean[ws])
        temp.append(wsStd[ws])
        temp.append(rt)
        result[index] = temp
    np.savetxt('%sinfo.txt' % (trainFile.split('.')[0]), result, fmt='%s', delimiter='\t')
    #translate test dataset
    testData = np.loadtxt(testFile)
    testData[:, 0] -= 1
    testData[:, 1] -= 1
    samplesNum = testData.shape[0]
    featuresNum = 5
    result = np.empty((samplesNum, featuresNum))
    for index, line in enumerate(testData):
        temp = []
        user, ws, rt = line[0], line[1], line[2]
        temp.append(userMean[user])
        temp.append(userStd[user])
        temp.append(wsMean[ws])
        temp.append(wsStd[ws])
        temp.append(rt)
        result[index] = temp
    np.savetxt('%sinfo.txt' % (testFile.split('.')[0]), result, fmt='%s', delimiter='\t')


def batchHander():
    traingFile = 'paper2/training1.txt' 
    testFile = 'paper2/test1.txt' 
    createFeatureVec(traingFile, testFile)
            
import unittest
class testClass(unittest.TestCase):
    def test1(self):
        batchHander()       
        
        
if __name__ == '__main__':
    unittest.main()
    