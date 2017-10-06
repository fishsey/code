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
    
    userQos = []
    userVector = trainArray[user]
    for qos in userVector:
        if int(qos) in label:
            userQos.append(qos)
    userQos = np.array(userQos)        
            
            
            
    wsQos = []       
    wsVector = trainArray[:, ws]
    for qos in wsVector:
        if int(qos) in label:
            wsQos.append(qos)
    wsQos = np.array(wsQos)
    
    
    if len(userQos) > 0 and len(wsQos) > 0:
        return np.mean(userQos)/2.0 + np.mean(wsQos)/ 2.0
    elif len(userQos) > 0:
        return np.mean(userQos)
    elif len(wsQos) > 0:
        return np.mean(wsQos)
    else:
        return label[0] + 0.5
        
        
        
if __name__ == "__main__":
    import paperTools as pp
    import numpy as np
    import time
    from paper.rendi2 import  cfPredictByDBSCAN
    from paper.rendi2 import  cfPredictByEuclid
    start = time.time()
    for sparess in [5, 10, 15, 20]:
        for fileNum in range(1, 11):  
            euiResult = []
            #dataset files
            trainFile = "/root/AAA/dataset/rendi2/train/sparseness%s/training%d.txt" % (sparess, fileNum)
            testFile = "/root/AAA/dataset/rendi2/temp/temp-%d-%d" % (sparess, fileNum)            
            trainArray = pp.createArrayObj(trainFile)
            testData = pp.loadTest(testFile)
            
            #load the prob dataset
            trainProb = "/root/AAA/dataset/rendi2/temp/temp-%d-%d-prob" % (sparess, fileNum)     
            kProb = 1
            trainProbData = np.loadtxt(trainProb)
            labels = np.argsort(trainProbData, axis=1)[:, -kProb:] - 1 #top-k labels
            labelsProb = np.sort(trainProbData, axis=1)[:, -kProb:]#top-k labels's 
            
            #load sim matrix
            simFile1 = "/root/AAA/dataset/rendi2/train/sparseness%s/training%deuSimMatrix" % (sparess, fileNum) 
            simFile2 = "/root/AAA/dataset/rendi2/train/sparseness%s/training%dcooSimMatrix" % (sparess, fileNum)
            simArrayEu = np.loadtxt(simFile1)
            simArrayCoo = np.loadtxt(simFile2)
            
#                 
            #cal eui
            eui = 0.0
            k1 = 3
            k2 = 15
            count = 0.0
            alpha = 0.4
            temp = []
            pows = 1.0
            for index, line in enumerate(testData):
#                print index
                user, ws, tui = line
                user = int(user)
                ws = int(ws)
                label = labels[index]
                labelProb = labelsProb[index]
                puiProbCoo = cfPredictByDBSCAN.predict(user, ws, label, labelProb, k2, simArrayCoo, trainArray, flag=0)
                puiProbEu = cfPredictByEuclid.predict(user, ws, label, labelProb, k2, simArrayEu, trainArray, flag=0)
                if puiProbCoo is None and puiProbEu is None:
#                    pui = predictByMean(user, ws, label, trainArray)
#                    eui += abs(tui - pui) ** pows
#                    count += 1.0
#                    continue
                    puiProbCoo = cfPredictByDBSCAN.predict(user, ws, label, labelProb, k1, simArrayCoo, trainArray, flag=1)
                    puiProbEu = cfPredictByEuclid.predict(user, ws, label, labelProb, k1, simArrayEu, trainArray, flag=1)
                    if puiProbCoo is None and puiProbEu is None:
                        pui = predictByMean(user, ws, label, trainArray)
                        eui += abs(tui - pui) ** pows
                        count += 1.0
                        euiResult.append(tui - pui)
                    else:
                        count += 1.0
                        if puiProbCoo is None:
                            eui += abs(tui - puiProbEu) ** pows
                            euiResult.append(tui - puiProbEu)
                        elif puiProbEu is None:
                            eui += abs(tui - puiProbCoo) ** pows
                            euiResult.append(tui - puiProbCoo)
                        else:
                            eui += abs(tui - alpha * puiProbCoo - (1 - alpha) * puiProbEu) ** pows
                            euiResult.append(tui - alpha * puiProbCoo - (1 - alpha) * puiProbEu)
                else:
                    count += 1.0
                    if puiProbCoo is None:
                        eui += abs(tui - puiProbEu)** pows
                        euiResult.append(tui - puiProbEu)
                    elif puiProbEu is None:
                        eui += abs(tui - puiProbCoo) ** pows
                        euiResult.append(tui - puiProbCoo)
                    else:
                        eui += abs(tui - alpha * puiProbCoo - (1 - alpha) * puiProbEu) ** pows
                        euiResult.append(tui - alpha * puiProbCoo - (1 - alpha) * puiProbEu)
                        
            print sparess, fileNum, count, pow(eui / count, 1.0/pows), len(testData)-count
#            print len(euiResult)
            np.savetxt("/root/AAA/dataset/rendi2/eui/euiAdaboost-%d-%d" % (sparess, fileNum), np.array(euiResult, dtype=str), fmt='%s', delimiter='\t')
#            break
#        break
        
            
            
    print "during time ... " , time.time() - start