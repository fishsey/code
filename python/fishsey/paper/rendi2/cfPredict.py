# -*- coding: utf-8 -*-
"""
Created on Sat Nov 12 10:26:40 2016

@author: root
"""
NoneValue = 111111
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
    
def predictByMean(user, ws, label, trainArray=None):
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
    from paper.rendi2 import  cfPredictByDBSCAN
    from paper.rendi2 import  cfPredictByEuclid
    start = time.time()
    for sparess in [5, 10, 15, 20]:
        for fileNum in range(4, 5):  
            #dataset files
            trainFile = "/rootAAA/dataset/rendi2/train/sparseness%s/training%d.txt" % (sparess, fileNum)
            testFile = "dataset/rendi2/test/sparseness%s/test%d.txt" % (sparess, fileNum)         
#            trainFileSampling = "dataset/rendi2/puiAnalyze/PuiTrainSampling%d-%d" % (sparess, fileNum)
            simFile1 = "dataset/rendi2/train/sparseness%s/training%deuSimMatrix" % (sparess, fileNum) 
            simFile2 = "dataset/rendi2/train/sparseness%s/training%dcooSimMatrix" % (sparess, fileNum)
            
            #load dataset
            trainArray = ld.loadArrayObj(trainFile)
            testData = ld.loadTestList(testFile)
            
#            clf = AdaBoostClassify.getClassify(trainFeature)
            
            simArrayEu = np.loadtxt(simFile1)
            simArrayCoo = np.loadtxt(simFile2)
            #load the prob dataset
            kProb = 1
            trainProb =  "dataset/rendi2/test/sparseness%s/test%d-prob" % (sparess, fileNum)         
            trainProbData = np.loadtxt(trainProb)
            labels = np.argsort(trainProbData, axis=1)[:, -kProb:] - 1 #top-k labels
            labelsProb = np.sort(trainProbData, axis=1)[:, -kProb:]#top-k labels's 
#            
            
            #cal eui
            eui = 0.0
            k = 15
            count = 0.0
#            for alpha in [0.0, 0.1, 0.2, 0.3, 0.4, 0.5]
            for index, line in enumerate(testData):
#                print index
                user, ws, tui = line
                user = int(user)
                ws = int(ws)
                label = labels[index]
                labelProb = labelsProb[index]
                             
                puiProbCoo = cfPredictByDBSCAN.predict(user, ws, label, labelProb, k, simArrayCoo, trainArray)
                puiProbEu = cfPredictByEuclid.predict(user, ws, label, labelProb, k, simArrayEu, trainArray)
                if puiProbCoo is None and puiProbEu is None:
                    count += 1.0
#                    tag = clf.predict(testFeature[index][:-1].reshape(1, -1))[0]
#                    tag = np.argsort(clf.predict_proba(testFeature[index][:-1].reshape(1, -1)))[0][-1] - 1
                    tag = label[0]
                    puiByMean = predictByMean(user, ws, tag, trainArray)
                    eui += abs(tui - puiByMean) 
                else:
                    count += 1.0
                    if puiProbCoo is None:
                        eui += abs(tui - puiProbEu)
                    elif puiProbEu is None:
                        eui += abs(tui - puiProbCoo) 
                    else:
                        eui += abs(tui - 0.5 * puiProbCoo - 0.5 * puiProbEu) 
                        
            print sparess, fileNum, count, eui / count
#        print 
#            pui = np.array(pui)
#            np.savetxt("dataset/rendi2/puiAnalyze/PuiTrainSampling%d-%d" % (sparess, fileNum), pui, fmt='%f', delimiter='\t')
#        break
    #end modeling
    print "during time ... " , time.time() - start