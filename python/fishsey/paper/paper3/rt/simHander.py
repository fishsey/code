# -*- coding: utf-8 -*-
"""
Created on Tue Nov  1 14:49:25 2016

@author: root
"""    
    
NoneValue = 111111


def simMinkowskiDist(inA, inB, n=2.0):
    '''
    距离:闵可夫斯基  2:欧式距离  1:曼哈顿距离
    '''
    import numpy as np
    inA = np.array(inA)
    inB = np.array(inB)
    indiceA = inA!= NoneValue
    indiceB = inB!= NoneValue
    indice = indiceA & indiceB
    inA = inA[indice]
    inB = inB[indice]
    if len(inA) == 0:
        return 0
    distance = sum((abs(inA - inB)) ** n) ** (1.0/n) / len(inA)
    sim = 1.0/(1.0 + distance)
    return sim 


  
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
    
    
def createSimArray(fileName, simMethod=simMinkowskiDist, n=2.0, ws=False):
    '''返回 trainArray 行向量之间的相似度矩阵
    '''
    import numpy as np
    trainArray = loadArrayObj(fileName)
    if ws is True:
        trainArray = trainArray.T
    rowNum, columnNum = trainArray.shape
    result = np.zeros((rowNum, rowNum))
    #sim(i, i) default as 0
    for i in range(rowNum):
#        print i
        for j in range(i):
            result[i,j] = result[j, i] = simMethod(trainArray[i], trainArray[j], n)
    return result


def createCoOccurrenceMatrixByDBSCAN(fileName, ws, eps, min_samples):
    import numpy as np
    from sklearn.cluster import DBSCAN
    dbscan = DBSCAN(eps=eps, min_samples=min_samples, metric='manhattan')
    trainArray = loadArrayObj(fileName)
    if ws is True:
        trainArray = trainArray.T
    rowNum, columnNum = trainArray.shape
    coOccurrenceMatrix = np.zeros((rowNum, rowNum))
    for columnIndex in range(columnNum):
        data1 = trainArray[:, columnIndex]
        data = data1[data1 != NoneValue]
        index = np.argwhere(data1 != NoneValue)[:, -1]
        labels =  dbscan.fit_predict(data.reshape(-1, 1))
        for label in set(labels):
            if len(labels[labels == label]) > 1:
                for row in index[labels == label]:
                    coOccurrenceMatrix[row, index[labels == label]] += 1                    
                    
    #modify c[i,i]=0
    for rowIndex in range(rowNum):
        coOccurrenceMatrix[rowIndex, rowIndex] = 0 
    return coOccurrenceMatrix
 


       
def save(obj, files):
    import cPickle
    with open(files, 'wb') as f:
        cPickle.dump(obj, f)
        
        
        
if __name__ == '__main__':  
    import numpy as np
    from sklearn.preprocessing import minmax_scale
    for sparess in range(5, 21, 5):
        for fileNum in range(1, 2):
            print sparess, fileNum
            trainFile = "/root/AAA/dataset/qos/tp/train/sparseness%s/training%d.txt" % (sparess, fileNum)
            #cluster
            for eps in [0.04]:
                for min_samples in [6]:
                    saveFile = "/root/AAA/dataset/qos/tp/userSim/coOccurrenceMatrix-user-%d-%d" % (sparess, fileNum)
                    wsCoo = createCoOccurrenceMatrixByDBSCAN(trainFile, False, eps, min_samples)
                    wsCoo = minmax_scale(wsCoo, axis=1)
                    np.savetxt(saveFile, wsCoo, delimiter='\t', fmt='%f')
            

    
    
    
    
    
    
    
