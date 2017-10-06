# -*- coding: utf-8 -*-
"""
Created on Thu Nov 10 16:22:46 2016

@author: root
"""
#==============================================================================
# consatant in all modules
#==============================================================================
NoneValue = 111111
def frequencyOnLabels(trainFile, modify=True, userNum=339, wsNum=5825):
    import numpy as np
    from paper.rendi2 import loadDataset as ld
    trainData = ld.loadTestList(trainFile, modify=modify)
    if trainData.shape[1] == 4:
        trainData = trainData[:, 1:]
    userFreq = np.zeros((userNum, 21))
    wsFreq = np.zeros((wsNum, 21))
    for line in trainData:
        user, ws, tui = line
        user = int(user)
        ws = int(ws)
        label = int(tui)
        userFreq[user, label] += 1
        wsFreq[ws, label] += 1
    return userFreq, wsFreq, trainData
            
def doMapping(trainFile, modify=True):
    import numpy as np
    userFreq, wsFreq, trainData = frequencyOnLabels(trainFile, modify=modify)
    #do mapping
    result = []
    for line in trainData:
        temp = []
        user, ws, tui = line
        user = int(user)
        ws = int(ws)
        temp.extend(userFreq[user])
        temp.extend(wsFreq[ws])
        temp.append(tui)
        result.append(temp)
    return np.array(result, dtype=float)
    
def predict(user, ws, label, trainArray=None):
    import numpy as np
    userVector = trainArray[user][trainArray[user].astype(int)==label]
    wsVector = trainArray[:, ws][trainArray[:, ws].astype(int)==label]
    if len(userVector) > 0 and len(wsVector) > 0:
        return np.mean(userVector)/2.0 + np.mean(wsVector)/ 2.0
    elif len(userVector) > 0:
        return np.mean(userVector)
    elif len(wsVector) > 0:
        return np.mean(wsVector)
    else:
        return label + 0.5
        
        
        
        
     
            
         
if __name__ == "__main__":
    from paper.rendi2 import loadDataset as ld
    from paper.rendi2 import AdaBoostClassify
    import numpy as np
    import time
    start = time.time()
    start = time.time()
    for sparess in [5, 10, 15, 20]:
        for fileNum in range(1, 11):  
            #data file
            trainFile = "dataset/rendi2/puiAnalyze/PuiTrainSampling%d-%d" % (sparess, fileNum)
            testFile = "dataset/rendi2/puiAnalyze/puiAnalyze-%d-%d" % (sparess, fileNum)         
            
            trainFile1 = "dataset/rendi2/train/sparseness%s/training%d.txt" % (sparess, fileNum)
            
            #load data
            trainArray = ld.loadArrayObj(trainFile1)
            
            testData = ld.loadTestList(testFile)
            
            trainFeature = doMapping(trainFile) 
            testFeature = doMapping(testFile)
            
            #prob dataset
            kProb = 1
            testProb = AdaBoostClassify.generateClassifyProb(trainFeature, testFeature)
            labels = np.argsort(testProb, axis=1)[:, -kProb:] - 1 #top-k label
            print labels
            
          
            #cal eui
            eui = 0.0
            for index, line in enumerate(testData):
                user, ws, tui = line
                user = int(user)
                ws = int(ws)
                label = labels[index][0]
                pui = predict(user, ws, label, trainArray=trainArray)
                eui += abs(tui - pui)
                
            print len(testData), eui/float(len(testData))
            
            
#            np.savetxt("dataset/rendi2/pui/dbscanPui%d-%d" % (sparess, fileNum), pui, fmt='%f', delimiter='\t')
            break
        break
    #end modeling
    print "during time ... " , time.time() - start






