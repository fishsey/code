# -*- coding: utf-8 -*-
"""
Created on Wed Nov  9 09:36:55 2016

@author: root
"""

def frequencyOnLabels(trainFile, modify, flag, sparess, userNum=339, wsNum=5825):
    import numpy as np
    from paper.rendi2 import loadDataset as ld
    trainData = ld.loadTestList(trainFile, modify=modify)
    if trainData.shape[1] == 4:
        trainData = trainData[:, 1:]
    #trainFeature
    userFreq = np.zeros((userNum, 21))
    wsFreq = np.zeros((wsNum, 21))
    for index, line in enumerate(trainData):
        if flag == True and index % sparess != 0:
            continue
        user, ws, tui = line
        user = int(user)
        ws = int(ws)
        labelIndex = int(tui) + 1 #labelIndex i means label i-1
        userFreq[user, labelIndex] += 1
        wsFreq[ws, labelIndex] += 1
    
    return userFreq, wsFreq, trainData      

      
def doMapping(trainFile, testFile, modify=True, flag=True, sparess=10):
    global userIps
    
#    import numpy as np
    from paper.rendi2 import loadDataset as ld
    userFreq, wsFreq, trainData  = frequencyOnLabels(trainFile, modify=modify, flag=flag, sparess=sparess)
    #trainFeature
    result = []
    for index, line in enumerate(trainData):
        temp = []
        user, ws, tui = line
        user = int(user)
        ws = int(ws)
        temp.extend(userFreq[user])
        temp.extend(userIps[user])
        temp.extend(wsFreq[ws])
        temp.append(tui)
        result.append(temp)
    trainFeature =  np.array(result, dtype=float)
    #testFeature
    result = []
    testData = ld.loadTestList(testFile, modify=modify)
    for index, line in enumerate(testData):
        temp = []
        user, ws, tui = line
        user = int(user)
        ws = int(ws)
        temp.extend(userFreq[user])
        temp.extend(userIps[user])
        temp.extend(wsFreq[ws])
        temp.append(tui)
        result.append(temp)
    testFeature =  np.array(result, dtype=float)
    return trainFeature, testFeature
    

    
def generateClassifyProb(train, test, min_samples_split=10, n_estimators=50): 
    from sklearn.ensemble import RandomForestClassifier
    import numpy as np
    
    global weights
     
#    clf = RandomForestClassifier(n_estimators=100, n_jobs=10, min_samples_split=10, class_weight=weights)
    clf = RandomForestClassifier(n_estimators=100, n_jobs=10, min_samples_split=10)
    
    
    
    clf.fit(train[:, :-1], train[:, -1].astype(float).astype(int))    

    testProb = clf.predict_proba(test[:, :-1])    
    trainProb = clf.predict_proba(train[:, :-1])    

    #cal the prob distribution
    for k in range(1, 10):
        sort = np.argsort(testProb, axis=1)[:, -k:] - 1 #topK most probability
        count = 0
        for i, label in enumerate(test[:, -1].astype(float).astype(int)):
            if label in sort[i]:
                count += 1
        print  float(count)/len(test[:, -1])
    
    return testProb, trainProb
    
import time
import numpy as np    
ips = "/root/AAA/dataset/rendi2/userlist.txt"
userIps = np.loadtxt(ips, dtype=str, delimiter='\t')[:, 1]
userIps = [np.array(line.split('.')) for line in userIps]
  
if __name__ == '__main__':  
    
    start = time.time()
    for sparess in [5, 10, 15, 20]:
        for fileNum in range(1, 11):    
            print sparess, fileNum, "\t", 
         
            #weights
            trainFile1 = "/root/AAA/dataset/rendi2/temp/tempTrain-%d-%d" % (sparess, fileNum)   
            dta = np.loadtxt(trainFile1)[:, -1].astype(int) + 1
            prob = np.bincount(dta)
            weights = {index:prob[index+1] for index in range(-1, 20)}
            
            #train and test dataset
            trainFile = "/root/AAA/dataset/rendi2/train/sparseness%s/training%d.txt" % (sparess, fileNum)   
            testFile = "/root/AAA/dataset/rendi2/temp/temp-%d-%d" % (sparess, fileNum)   
            trainFeature, testFeature = doMapping(trainFile, testFile, flag=False)
            print trainFeature.shape, testFeature.shape
            
            testProb, trainProb = generateClassifyProb(trainFeature, testFeature)
            
            np.savetxt(testFile.split('.')[0] + "-prob", testProb, delimiter='\t', fmt='%f')
#            np.savetxt(trainFile.split('.')[0] + "-prob", trainProb, delimiter='\t', fmt='%f')
            
    print "during time ... " , time.time() - start