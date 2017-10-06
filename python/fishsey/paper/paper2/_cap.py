# -*- coding: utf-8 -*-
"""
Created on Wed Jul 06 12:33:17 2016

@author: fishsey
"""

def loadDataSet(trainFile, testFile):
    import paper
    trainArray = paper.createArrayObj(trainFile)
    testObj = paper.loadTest(testFile)
    return trainArray, testObj

def predictClusterLabels(obj, k):
    from sklearn.cluster import KMeans
    import numpy as np
    obj = obj[:, np.newaxis]
    km = KMeans(n_clusters=k, init='k-means++')
    labels = km.fit_predict(obj)
    return labels

#==============================================================================
# k_means first
#==============================================================================
def generateFirstClusterInfo(trainArray, k=7):
    import numpy as np
    userNum, wsNum = trainArray.shape
    resultLables = np.ones((wsNum, userNum)) * -1
    resultUntrustUsers = np.zeros((wsNum, userNum))
    resultUserInSameCluster = np.zeros((userNum, userNum))
    for index, wsQos in enumerate(trainArray.T):
        indice = np.argwhere(wsQos!=NoneValue)[:, -1]
        wsQos = wsQos[indice]
        kc = k if len(wsQos) >= k else int(np.ceil(len(wsQos)/3.0))
        labels = predictClusterLabels(wsQos, kc)
        sameClusterIndice = inSameCluster(indice, labels)
        minClusterIndice = minClusterMembers(sameClusterIndice)
        #update indice
        resultLables[index][indice] = labels
        resultUntrustUsers[index][minClusterIndice] = 1
        for index, value in enumerate(indice):
            resultUserInSameCluster[value, sameClusterIndice[index]] += 1
        #modify (i,i)==0
    for index in range(userNum):
        resultUserInSameCluster[index, index] = 0
    return resultLables, resultUntrustUsers, resultUserInSameCluster
    
def inSameCluster(indice, label):
    import numpy as np
    result = []
    for index, value in enumerate(indice):
        result.append(indice[label == label[index]])
    return np.array(result)

def minClusterMembers(sameClusterIndice):
    import numpy as np
    minLens = np.inf
    for value in sameClusterIndice:
        lens = len(value)
        if lens < minLens:
            minLens = lens
    result = []
    for value in sameClusterIndice:
        lens = len(value)
        if lens == minLens:
            result.extend(value.tolist())
    return  list(set(result))
    
#==============================================================================
# k_means second        
#==============================================================================
def generateSecondClusterInfo(resultUntrustUsers):
    import numpy as np
    userUntrustCount = np.sum(resultUntrustUsers, axis=0) 
    labels = predictClusterLabels(userUntrustCount, k=2)
    untrustUserList = generateUntrustUser(labels)
    return untrustUserList
def generateUntrustUser(labels):
    import numpy as np
    len_1 = len(labels[labels == 1])
    len_0 = len(labels[labels == 0])
    if len_1 <= len_0:
        return np.argwhere(labels == 1)[:, -1]
    else:
        return np.argwhere(labels == 0)[:, -1]    
    
def main():
    import paper
    import numpy as np
    trainArray, testObj = loadDataSet('rt/sparseness20/training1.txt','rt/sparseness20/test1.txt')
    resultLables, resultUntrustUsers, resultUserInSameCluster = generateFirstClusterInfo(trainArray, k=7)
    
#    #save or load the temp-result
#    paper.save(resultLables, 'resultLables.txt')
#    paper.save(resultUntrustUsers, 'resultUntrustUser.txt')
#    paper.save(resultUserInSameCluster, 'resultUserInSameCluster.txt')
#    resultUntrustUsers = paper.load('resultUntrustUser.txt')
#    resultUserInSameCluster = paper.load('resultUserInSameCluster.txt')
#    resultLables = paper.load('resultLables.txt')
    
    untrustUserList = generateSecondClusterInfo(resultUntrustUsers)
    mae = 0.0
    for index, line in enumerate(testObj):
        u, i, tui = int(line[0]), int(line[1]), float(line[2])
        pui = predict(u, i, trainArray, untrustUserList, resultUserInSameCluster, resultLables) or np.mean(trainArray[:,i] != NoneValue)
        eui = tui - pui
        mae += abs(eui)
    print mae/len(testObj)
    
    
def predict(u, i, trainArray, untrustUserList, resultUserInSameCluster, resultLables):
    import numpy as np
    userMembers = resultUserInSameCluster[u]
    simUser = generateSimUser(trainArray, untrustUserList, userMembers, u, i)
    if simUser is None:
        #if no sim-user, then return the userMean as pui
        return np.mean(trainArray[:,i] != NoneValue)
    else:
        pui = 0.0
        count = 0.0
        classifResult = resultLables[i]
        for userId in simUser:
            clusterMembers = np.argwhere(classifResult==classifResult[userId])[:, -1]
            tui = preditWithMembers(clusterMembers, i, trainArray)
            if tui is not None:
                pui += preditWithMembers(clusterMembers, i, trainArray)
                count += 1.0
        if count is 0.0:
            return None
        return pui/count
        
        
def generateSimUser(trainArray, untrustUserList, userMembers, u, i, k=2):
    import numpy as np
    import sys
    #if user in userMembers hasn't invoked ws-i, then modify -1
    userMembers[trainArray[:, i] == NoneValue] = -1
    #fliter user in untrustUserList
    userMembers[untrustUserList] = -1
    #filter u
    userMembers[u] = -1
    #top_k user
    sortIndice = np.argsort(userMembers)
    simUser = sortIndice[-k: ]    
    return simUser
    result = []
    for index in simUser:
        if userMembers[index] != -1:
            result.append(index)
    if bool(result) == False:
        return None
    return np.array(result)
            
def preditWithMembers(clusterMembers, i, trainArray):
    import numpy as np
    qos = trainArray[clusterMembers, i]
    qos = qos[qos != NoneValue]
    if len(qos) == 0:
        return None
    return np.mean(qos)    

import unittest
NoneValue = 111111.0
class testClass(unittest.TestCase):
    def test1(self):
        main()
        
            
if __name__ == '__main__':
    unittest.main()