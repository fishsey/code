# -*- coding: utf-8 -*-
"""
Created on Thu Nov 10 16:22:46 2016

@author: root
"""
NoneValue = 111111
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
            if int(trainArray[v, ws]) in label:
                prob =  labelProb[label.tolist().index(int(trainArray[v, ws]))]       
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
            for eps in [0.04]:
                for min_samples in [6]:
                    print eps, min_samples,
                    userSimFile = "/root/AAA/dataset/qos/tp/userSim/coOccurrenceMatrix-user-%d-%d" % (sparess, fileNum)      
                    trainArray = loadArrayObj(trainFile)
                    testData = loadTestList(testFile)
                    userSimArray = np.loadtxt(userSimFile)
                    #load the prob dataset
                    kProb = 2
                    testProb =  "/root/AAA/dataset/qos/testPro/sparseness%s/test%d-prob" % (sparess, fileNum)         
                    testProbData = np.loadtxt(testProb)
                    labels = np.argsort(testProbData, axis=1)[:, -kProb:] - 1 #top-k labels
                    labelsProb = np.sort(testProbData, axis=1)[:, -kProb:]#top-k labels's 
                    #cal eui
                    euiProb = 0.0
                    count = 0.0
                    pui = []
                    k = 4 #topK
                    for index, line in enumerate(testData):
                        user, ws, tui = line
                        user = int(user)
                        ws = int(ws)
                        label = labels[index]
                        labelProb = labelsProb[index]
                        puiProb = predict(user, ws, label, labelProb, k, userSimArray, trainArray)
                        if puiProb is not None:
                            pui.append(puiProb)
                            count += 1
                            euiProb += abs(tui - puiProb)
                        else:
                            pui.append(NoneValue)
                    print count, len(testData)-count, euiProb/count
                    np.savetxt("/root/AAA/dataset/qos/paper3/pui/dbscanPui-user-%d-%d" % (sparess, fileNum), pui, fmt='%f', delimiter='\t')





