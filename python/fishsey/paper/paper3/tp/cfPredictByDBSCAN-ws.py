# -*- coding: utf-8 -*-
"""
Created on Thu Nov 10 16:22:46 2016

@author: root
"""
NoneValue = 111111
def transToLabel(number):
    return (-1 if number < 0 else int(number ** 0.5)) + 1
    
    
def getNeighbors(user, ws, k, simArray, trainArray):
    import numpy as np
    result = []
    #has qos on ws
    for v in range(trainArray.shape[0]):
        if trainArray[v, ws] != NoneValue:
            result.append(v)
    
    result = np.array(result)
    simResult = simArray[user, result]
    if len(result) >= k:
        return result[np.argsort(simResult)[-k:]]
    else:
        return result

def predict(user, ws, label, labelProb, k, simArray, trainArray):
        userNeighbors = getNeighbors(user, ws, k, simArray, trainArray)
        probSum = 0.0
        probPui = 0.0
        for v in userNeighbors:
            if transToLabel(trainArray[v, ws]) in label:
                prob =  labelProb[label.tolist().index(transToLabel(trainArray[v, ws]))]       
                probSum += prob
                probPui += prob * trainArray[v, ws]
        if probSum != 0:
            return probPui/probSum 
        else:
            return None
            
            
def loadArrayObj(fileName, userNum=339, wsNum=5825):
    import numpy as np
    trainObj = np.loadtxt(fileName)
    userLs, wsLs, rt  = trainObj[:, 0], trainObj[:, 1], trainObj[:, 2]
    userLs = np.array(userLs, dtype=int) 
    wsLs = np.array(wsLs, dtype=int)
    rt = np.array(rt, dtype=float)
    arrayObj = np.empty((userNum, wsNum))
    arrayObj.fill(NoneValue)
    arrayObj[userLs, wsLs] = rt 
    return arrayObj 
    
def loadTestList(fileName):
    import numpy as np
    result = np.loadtxt(fileName)
    return result   

            
            
if __name__ == "__main__":
    import numpy as np
    for sparess in range(5, 21, 5):
        for fileNum in range(1, 2):  
            print sparess, fileNum,
            trainFile = "/root/AAA/dataset/qos/tp/train/sparseness%s/training%d.txt" % (sparess, fileNum)
            testFile = "/root/AAA/dataset/qos/tp/test/sparseness%s/test%d.txt" % (sparess, fileNum)
            side = 'ws'
            for eps in [2]:
                for min_samples in [1]:
                    print eps, min_samples,
                    wsSimFile =  "/root/AAA/dataset/qos/tp/coOccurrenceMatrix-%s-%d-%d-%d-%d" % (side, sparess, fileNum, eps, min_samples)
                    trainArray = loadArrayObj(trainFile).T
                    testData = loadTestList(testFile)
                    wsSimArray = np.loadtxt(wsSimFile)
                    kProb = 4
                    testProb =  "/root/AAA/dataset/qos/tp/test/sparseness%s/test%d-prob" % (sparess, fileNum)         
                    testProbData = np.loadtxt(testProb)
                    labels = np.argsort(testProbData, axis=1)[:, -kProb:] #top-k labels
                    labelsProb = np.sort(testProbData, axis=1)[:, -kProb:]#top-k labels's 
                    #cal eui
                    euiProb = 0.0
                    count = 0.0
                    pui = []
                    k = 10
                    for index, line in enumerate(testData):
                        user, ws, tui = line
                        user, ws = int(ws), int(user)
                        label = labels[index]
                        labelProb = labelsProb[index]
                        #by ws
                        puiProb = predict(user, ws, label, labelProb, k, wsSimArray, trainArray)
                        if puiProb is not None:
                            pui.append(puiProb)
                            count += 1
                            euiProb += abs(tui - puiProb)
                        else:
                            pui.append(NoneValue)
                    print count, len(testData)-count, '\t', euiProb/count
                    np.savetxt("/root/AAA/dataset/qos/tp/pui/dbscanPui-ws-%d-%d" % (sparess, fileNum), pui, fmt='%f', delimiter='\t')






