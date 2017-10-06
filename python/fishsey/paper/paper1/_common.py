# -*- coding: utf-8 -*-
"""
Created on Wed Jun 22 13:34:52 2016

@author: fishsey
"""
NoneValue = 111111.0
def transToNumber(a):
    import numpy as np
    for indexs, ip in enumerate(a):
        elems = ip.split('.')
        for index, elem in enumerate(elems):
            if len(elem) == 1:
                elems[index] = '00' + elem
            if len(elem) == 2:
                elems[index] = '0' + elem
        ip = ''.join(elems)
        a[indexs] = ip
    a = np.array(a, dtype=float)
    a = a/1e12
    return a
def createUserInfo(fileName1="paper2/userlist.txt"):
    import numpy as np
    info = np.loadtxt(fileName1, dtype=str, delimiter='\t')[:, [1, -2, -1]]
    info[:, 0] = transToNumber(info[:, 0])
    np.savetxt('paper2/userInfo.txt', info, fmt='%s', delimiter='\t')
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
def calMeanAndStd(trainFile='paper2/training1.txt'):
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
    np.savetxt('data3/%s' % (trainFile.split('/')[1]), result, fmt='%s', delimiter='\t')
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
    np.savetxt('data3/%s' % (testFile.split('/')[1]), result, fmt='%s', delimiter='\t')
#    np.savetxt('data3/%s/%s' % (testFile.split('/')[1], testFile.split('/')[2]), result, fmt='%s', delimiter='\t')

def batchHander():
    for sparess in range(2, 31, 2):
#        for num in range(1, 11):
        print sparess
        traingFile = 'spareness_sample/rt-training%d.txt' % (sparess)
        testFile = 'spareness_sample/rt-test%d.txt' % (sparess)
        createFeatureVec(traingFile, testFile)
            
    
import unittest
class testClass(unittest.TestCase):
    def test1(self):
        batchHander()       
        
        
if __name__ == '__main__':
    unittest.main()
    