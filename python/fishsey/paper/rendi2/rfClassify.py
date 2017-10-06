# -*- coding: utf-8 -*-
"""
Created on Wed Nov  9 16:36:08 2016

@author: root
"""

# -*- coding: utf-8 -*-
"""
Created on Wed Nov  9 09:36:55 2016

@author: root
"""

lineNum = 0

def frequencyOnLabels(trainFile, modify, flag, userNum=339, wsNum=5825,  sparess=2):
    import numpy as np
    from paper.rendi2 import loadDataset as ld
    trainData = ld.loadTestList(trainFile, modify=modify)
    if trainData.shape[1] == 4:
        trainData = trainData[:, 1:]
    userFreq = np.zeros((userNum, 21))
    wsFreq = np.zeros((wsNum, 21))
    for index, line in enumerate(trainData):
        if flag == True and index % sparess != lineNum:
            continue
        user, ws, tui = line
        user = int(user)
        ws = int(ws)
        labelIndex = int(tui) + 1 #labelIndex i means label i-1
        userFreq[user, labelIndex] += 1
        wsFreq[ws, labelIndex] += 1
    return userFreq, wsFreq, trainData            
def doMapping(trainFile, testFile, modify=True, flag=True, sparess=2):
#    import numpy as np
    from paper.rendi2 import loadDataset as ld
    userFreq, wsFreq, trainData = frequencyOnLabels(trainFile, modify=modify, flag=flag, sparess=sparess)
    #trainFeature
    result = []
    for index, line in enumerate(trainData):
#        if flag == True and index % sparess !=  lineNum:
#            continue
        temp = []
        user, ws, tui = line
        user = int(user)
        ws = int(ws)
        temp.extend(userFreq[user])
        temp.extend(wsFreq[ws])
        temp.append(tui)
        result.append(temp)
    trainFeature =  np.array(result, dtype=float)
    #testFeature
    result = []
    testData = ld.loadTestList(testFile, modify=modify)
    for index, line in enumerate(testData):
#        if flag == True and index % sparess !=  lineNum:
#            continue
        temp = []
        user, ws, tui = line
        user = int(user)
        ws = int(ws)
        temp.extend(userFreq[user])
        temp.extend(wsFreq[ws])
        temp.append(tui)
        result.append(temp)
    testFeature =  np.array(result, dtype=float)
    return trainFeature, testFeature
    


def getClassify(train, min_samples_split=10, n_estimators=20): 
    from sklearn.ensemble import AdaBoostClassifier
    from sklearn.tree import DecisionTreeClassifier
    
#    print 'classify start ....'
    dmt = DecisionTreeClassifier(criterion='entropy', min_samples_split=min_samples_split)
    clf = AdaBoostClassifier(base_estimator=dmt, n_estimators=n_estimators)
    clf.fit(train[:, :-1], train[:, -1].astype(float).astype(int))    
    
    
#    print 'classify end ....'
    return clf


    
def generateClassifyProb(train, test, min_samples_split=10, n_estimators=50): 
    from sklearn.ensemble import RandomForestClassifier
#    from sklearn.tree import DecisionTreeClassifier
#    from sklearn.model_selection import GridSearchCV
    import numpy as np
     
    clf = RandomForestClassifier(oob_score=True, n_jobs=30, n_estimators=50, max_features=0.5, min_samples_split=10)
    clf.fit(train[:, :-1], train[:, -1].astype(float).astype(int))    

    testProb = clf.predict_proba(test[:, :-1])    
    trainProb = clf.predict_proba(train[:, :-1])    

    #cal the prob distribution
    for k in range(1, 2):
        sort = np.argsort(testProb, axis=1)[:, -k:] - 1 #topK most probability
        count = 0
        for i, label in enumerate(test[:, -1].astype(float).astype(int)):
            if label in sort[i]:
                count += 1
    print  float(count)/len(test[:, -1])
    
#    return testProb
    return testProb, trainProb
#    
      
#    paras = {'n_estimators':[5, 10, 20, 50, 100, 200, 500, 1000], 'algorithm':['SAMME', 'SAMME.R'], 'learning_rate':[0.01, 0.02, 0.05, 0.1, 0.2, 0.3, 0.5, 0.7, 1.0, 1.5, 2.0,2.5, 3.0, 3.5, 4.0]}
#    clf = GridSearchCV(adbst, param_grid=paras, n_jobs=5)
    
    
        

      
    
if __name__ == '__main__':  
    import time
    import numpy as np
    start = time.time()
    for sparess in range(5, 21, 5):
        for fileNum in range(1, 2):    
            print sparess, fileNum, "\t", 
#==============================================================================
#             train data
#==============================================================================
            trainFile = "dataset/rendi2/train/sparseness%s/training%d.txt" % (sparess, fileNum)
#            trainFile = "dataset/rendi2/puiAnalyze/PuiTrainSampling%d-%d" % (sparess, fileNum)
#            trainFile = "dataset/rendi2/samples/rt-training%d.txt" % (sparess)
            
#==============================================================================
#             test data
#==============================================================================
#            testFile = "dataset/rendi2/puiAnalyze/puiAnalyze-%d-%d" % (sparess, fileNum)             
            testFile = "dataset/rendi2/test/sparseness%s/test%d.txt" % (sparess, fileNum)    
#            testFile = "dataset/rendi2/samples/rt-test%d.txt" % (sparess)
            trainFeature, testFeature = doMapping(trainFile, testFile, flag=False)
            print trainFeature.shape, testFeature.shape,
            
#==============================================================================
#             cal the accurate
#==============================================================================
#            generateClassifyProb(trainFeature, testFeature)
            
#            testProb = generateClassifyProb(trainFeature, testFeature)
            testProb, trainProb = generateClassifyProb(trainFeature, testFeature)
#        break
            
#            np.savetxt(testFile.split('.')[0] + "-prob", testProb, delimiter='\t', fmt='%f')
#            np.savetxt(trainFile.split('.')[0] + "-prob", trainProb, delimiter='\t', fmt='%f')
            
    print "during time ... " , time.time() - start
