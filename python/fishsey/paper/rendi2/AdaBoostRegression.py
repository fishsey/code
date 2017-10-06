# -*- coding: utf-8 -*-
"""
Created on Wed Nov  9 09:36:55 2016

@author: root
"""
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
    
    
def generateClassifyProb(train, test): 
    from sklearn.ensemble import AdaBoostRegressor
    from sklearn.tree import DecisionTreeRegressor
    from sklearn.model_selection import GridSearchCV
    import numpy as np
    
#    gscv = GridSearchCV()

    dmt = DecisionTreeRegressor(criterion='mse', min_samples_split=10, splitter='best')
    adbst = AdaBoostRegressor(base_estimator=dmt, n_estimators=100, learning_rate=1.0, loss="square")
    
    paras = {'loss':['linear', 'square', 'exponential'], 'n_estimators':[20, 50, 100, 200, 500, 1000], 'algorithm':['SAMME', 'SAMME.R'], 'learning_rate':[0.01, 0.05, 0.1, 0.5, 1.0, 2.0, 5.0]}
    clf = GridSearchCV(adbst, param_grid=paras, n_jobs=30)
    
    
    clf.fit(train[:, :-1], train[:, -1])    
    py = clf.predict(test[:, :-1])
    
    y = test[:, -1]
    
    print np.mean(abs(py -y))
        
        
if __name__ == '__main__':  
    import time
    start = time.time()
    for sparess in [5]:
        for fileNum in range(1, 2):    
            trainFile = "dataset/rendi2/train/sparseness%s/training%d.txt" % (sparess, fileNum)
            trainFeature = doMapping(trainFile) 
            
            testFile = "dataset/rendi2/puiAnalyze/puiAnalyze-%d-%d" % (sparess, fileNum) 
            testFeature = doMapping(testFile, modify=False)
            
#            testFile = "dataset/rendi2/test/sparseness%s/test%d.txt" % (sparess, fileNum)    
#            testFeature = doMapping(testFile)
            
            generateClassifyProb(trainFeature, testFeature)
    print "during time ... " , time.time() - start