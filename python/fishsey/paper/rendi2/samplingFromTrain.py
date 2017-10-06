# -*- coding: utf-8 -*-
"""
Created on Sat Nov 12 10:26:40 2016

@author: root
"""


if __name__ == "__main__":
    from paper.rendi2 import loadDataset as ld
    import numpy as np
    import time
    from paper.rendi2 import  cfPredictByDBSCAN
    from paper.rendi2 import  cfPredictByEuclid
    start = time.time()
    for sparess in [5, 10, 15, 20]:
        for fileNum in range(1, 11):  
            print sparess, fileNum, '\t',
            #load the qos dataset
            trainFile = "dataset/rendi2/train/sparseness%s/training%d.txt" % (sparess, fileNum)
            simFile1 = "dataset/rendi2/train/sparseness%s/training%deuSimMatrix" % (sparess, fileNum) 
            simFile2 = "dataset/rendi2/train/sparseness%s/training%dcooSimMatrix" % (sparess, fileNum)
            trainArray = ld.loadArrayObj(trainFile)
            trainData = ld.loadTestList(trainFile)
            simArrayEu = np.loadtxt(simFile1)
            simArrayCoo = np.loadtxt(simFile2)
            #load the prob dataset
            kProb = 1
            trainProb =  "dataset/rendi2/train/sparseness%s/training%d-prob" % (sparess, fileNum)         
            trainProbData = np.loadtxt(trainProb)
            labels = np.argsort(trainProbData, axis=1)[:, -kProb:] - 1 #top-k labels
            labelsProb = np.sort(trainProbData, axis=1)[:, -kProb:]#top-k labels's 
            #cal eui
            euiProb = 0.0
            count = 0.0
            pui = []
            k = 2
            for index, line in enumerate(trainData):
#                print index
                user, ws, tui = line
                user = int(user)
                ws = int(ws)
                label = labels[index]
                labelProb = labelsProb[index]
                             
                puiProbCoo = cfPredictByDBSCAN.predict(user, ws, label, labelProb, k, simArrayCoo, trainArray)
                puiProbEu = cfPredictByEuclid.predict(user, ws, label, labelProb, k, simArrayEu, trainArray)
                if puiProbCoo is None and puiProbEu is None:
                    count += 1
                    pui.append([user+1, ws+1, tui])
                else:
                    if puiProbCoo is None:
                        euiProb += abs(tui - puiProbEu)
                    elif puiProbEu is None:
                        euiProb += abs(tui - puiProbCoo)
                    else:
                        euiProb += abs(tui - 0.5 * puiProbCoo - 0.5 * puiProbEu)
                        
            print count, euiProb / (len(trainData) - count), sparess, fileNum
            pui = np.array(pui)
            np.savetxt("dataset/rendi2/puiAnalyze/PuiTrainSampling%d-%d" % (sparess, fileNum), pui, fmt='%f', delimiter='\t')
#            break
#        break      
    #end modeling
    print "during time ... " , time.time() - start