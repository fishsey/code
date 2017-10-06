# -*- coding: utf-8 -*-
"""
Created on Wed Jun 22 13:34:52 2016

@author: fishsey
"""
NoneValue = 111111.0#as the loss QoS
# ROOT_DIRECTORY = "/Users/rd/Documents/EclipseWorkspace/Prediction/data/20%/"
from rendi.prediction import ROOT_DIRECTORY

def transToNumber(data, *lables):
    import numpy as np
    lens = data.shape[1]
    result = np.empty_like(data)
    for index, value in enumerate(data):
        for i in range(lens):
            result[index, i] = lables[i].index(value[i])
    return result  
def createWsInfo(fileName=ROOT_DIRECTORY+"wslist.txt"):
    import numpy as np
    info = np.loadtxt(fileName, dtype=str, delimiter='\t')[:, [-2, -1]]
    provideName = info[:, 0]
    country = info[:, 1]
    listProvide = list(set(provideName))
    listCountry = list(set(country))
    info = transToNumber(info, listProvide, listCountry)
    np.savetxt(ROOT_DIRECTORY+'/wsInfo.txt', info, fmt='%s', delimiter='\t')
      
#==============================================================================
# cal the feature vector
#==============================================================================
def calFeature(data, *labels):
    import numpy as np
    featureNum = sum(labels[i].shape[1] for i in xrange(len(labels)))
    result = np.empty((data.shape[0], featureNum + 1))
    for index, value in enumerate(data):
        temp = []
        u, i, qos = value.astype(int)
        uInfos = labels[0][u]
        wsInfos = labels[1][i]
        temp.extend(uInfos)
        temp.extend(wsInfos)
        temp.append(qos)
        result[index] = temp
    return result
def createFeatureVec(trainFile, testFile):
    import numpy as np
    from rendi import paper
    train = paper.loadTest(trainFile)
    test = paper.loadTest(testFile)
    userInfo = np.loadtxt(ROOT_DIRECTORY+'userInfo.txt')
    wsInfo = np.loadtxt(ROOT_DIRECTORY+'wsInfo.txt')
    trainFeature = calFeature(train, userInfo, wsInfo)
    testFeature = calFeature(test, userInfo, wsInfo)
    np.savetxt(ROOT_DIRECTORY+'trainInfo1-after.txt', trainFeature, fmt='%s', delimiter='\t')
    np.savetxt(ROOT_DIRECTORY+'testInfo1-after.txt', testFeature, fmt='%s', delimiter='\t')
    
import unittest
class testClass(unittest.TestCase):
    def test1(self):
#        transUserInfo()
        createFeatureVec(ROOT_DIRECTORY+'/training1.txt', ROOT_DIRECTORY+'/test1.txt')       
#         createWsInfo()
        
if __name__ == '__main__':
    unittest.main()
    