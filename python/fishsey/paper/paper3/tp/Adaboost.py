# -*- coding: utf-8 -*-
"""
Created on Wed Nov  9 09:36:55 2016

@author: root
"""
import numpy as np

def transToLabel(number):
    return (-1 if number < 0 else int(number ** 0.5)) + 1

def frequencyOnLabels(trainFile, sparess=1, userNum=339, wsNum=5825):
    trainData = np.loadtxt(trainFile)
    maxValue = np.max(trainData[:,-1]) 
    labels = transToLabel(maxValue) #[0, 32]
    print labels
    
    #fraq vector
    userFreq = np.zeros((userNum, labels+1))
    wsFreq = np.zeros((wsNum, labels+1))
    for index, line in enumerate(trainData):
        if index % sparess != 0:
            continue
        user, ws, tui = line
        user = int(user)
        ws = int(ws)
        labelIndex = transToLabel(tui) 
        userFreq[user, labelIndex] += 1 
        wsFreq[ws, labelIndex] += 1
    return userFreq, wsFreq, trainData      

      
def doMapping(trainFile, testFile):
    userFreq, wsFreq, trainData  = frequencyOnLabels(trainFile)
    print userFreq.shape, wsFreq.shape
    #trainFeature
    result = []
    for index, line in enumerate(trainData):
        temp = []
        user, ws, tui = line
        user = int(user)
        ws = int(ws)
        labelIndex = transToLabel(tui)
        temp.extend(userFreq[user])
        temp.extend(wsFreq[ws])
        temp.append(labelIndex)
        result.append(temp)
    trainFeature =  np.array(result, dtype=int)
    
    #testFeature
    result = []
    testData = np.loadtxt(testFile)
    for index, line in enumerate(testData):
        temp = []
        user, ws, tui = line
        user = int(user)
        ws = int(ws)
        labelIndex = transToLabel(tui)
        temp.extend(userFreq[user])
        temp.extend(wsFreq[ws])
        temp.append(labelIndex)
        result.append(temp)
    testFeature =  np.array(result, dtype=int)
    
    return trainFeature, testFeature
    
    
def generateClassifyProb(train, test, min_samples_split=10, n_estimators=50): 
    from sklearn.ensemble import AdaBoostClassifier, RandomForestClassifier
    from sklearn.tree import DecisionTreeClassifier
    import numpy as np
    #adaboost
#    dmt = DecisionTreeClassifier(criterion='entropy', min_samples_split=min_samples_split)
#    clf = AdaBoostClassifier(base_estimator=dmt, n_estimators=n_estimators)
    
    #random forest
    clf = RandomForestClassifier(oob_score=False, criterion='entropy', n_jobs=-1, n_estimators=500, min_samples_split=30, class_weight=None)
    
    clf.fit(train[:, :-1], train[:, -1].astype(float).astype(int))    
    testProb = clf.predict_proba(test[:, :-1])    
    trainProb = clf.predict_proba(train[:, :-1])    
    
    #cal the prob distribution
    for k in range(1, 6):
        sort = np.argsort(testProb, axis=1)[:, -k:]
        count = 0
        for i, label in enumerate(test[:, -1].astype(float).astype(int)):
            if label in sort[i]:
                count += 1
        print  float(count)/len(test[:, -1])
    
    return testProb, trainProb
   
     
if __name__ == '__main__':  
    for sparess in range(5, 21, 5):
        for fileNum in range(1, 4):    
            print sparess, fileNum
            trainFile = "/root/AAA/dataset/qos/tp/train/sparseness%s/training%d.txt" % (sparess, fileNum)   
            testFile = "/root/AAA/dataset/qos/tp/test/sparseness%s/test%d.txt" % (sparess, fileNum)    
            
            trainFeature, testFeature = doMapping(trainFile, testFile)
            print trainFeature.shape, testFeature.shape
            
            testProb, trainProb = generateClassifyProb(trainFeature, testFeature)
            
            np.savetxt(testFile.split('.')[0] + "-prob", testProb, delimiter='\t', fmt='%f')
            np.savetxt(trainFile.split('.')[0] + "-prob", trainProb, delimiter='\t', fmt='%f')
            
