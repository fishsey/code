# -*- coding: utf-8 -*-
"""
Created on Thu Nov 10 14:24:50 2016

@author: root
"""
#==============================================================================
# consatant in all modules
#==============================================================================
NoneValue = 111111

def getNeighbors(user, ws, k, simArray, trainArray, flag):
    '''neighbors must have historical qos on ws
    '''
    import numpy as np
    result = []
    
    #has qos on ws
    if flag == 1:
        for v in range(339):
            if trainArray[v, ws] != NoneValue:
                result.append(v)
    elif flag == 0:#not require has qos on ws
        result = range(339)
            
    result = np.array(result)
    simResult = simArray[user, result]
    if len(result) >= k:
        return result[np.argsort(simResult)[-k:]]
    else:
        return result

def predict(user, ws, label, labelProb, k=2, simArray=None, trainArray=None, flag=0):
        userNeighbors = getNeighbors(user, ws, k=k, simArray=simArray, trainArray=trainArray, flag=flag)
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
            
            

if __name__ == "__main__":
    from paper.rendi2 import loadDataset as ld
    import numpy as np
    import time
    start = time.time()
    for sparess in [5, 10, 15, 20]:
        for fileNum in range(1, 11):  
            #load the qos dataset
            trainFile = "dataset/rendi2/train/sparseness%s/training%d.txt" % (sparess, fileNum)
            testFile = "dataset/rendi2/test/sparseness%s/test%d.txt" % (sparess, fileNum)         
            simFile = "dataset/rendi2/train/sparseness%s/training%deuSimMatrix" % (sparess, fileNum)         
            trainArray = ld.loadArrayObj(trainFile)
            testData = ld.loadTestList(testFile)
            simArray = np.loadtxt(simFile)
            #load the prob dataset
            kProb = 1
            testProb =  "dataset/rendi2/test/sparseness%s/test%d-prob" % (sparess, fileNum)         
            testProbData = np.loadtxt(testProb)
            labels = np.argsort(testProbData, axis=1)[:, -kProb:] - 1 #top-k labels
            labelsProb = np.sort(testProbData, axis=1)[:, -kProb:]#top-k labels's 
            #cal eui
            euiProb = 0.0
            count = 0.0
            pui = []
            k = 2
            for index, line in enumerate(testData):
                user, ws, tui = line
                user = int(user)
                ws = int(ws)
                label = labels[index]
                labelProb = labelsProb[index]
                             
                puiProb = predict(user, ws, label, labelProb, k, simArray=simArray, trainArray=trainArray)
                    
                if puiProb is not None:
                    pui.append(puiProb)
                    count += 1
                    euiProb += abs(tui - puiProb)
                else:
                    pui.append(NoneValue)
            print count, len(testData)-count, euiProb/count
            np.savetxt("dataset/rendi2/pui/euPui%d-%d" % (sparess, fileNum), pui, fmt='%f', delimiter='\t')
            break
        break
    #end modeling
    print "during time ... " , time.time() - start






