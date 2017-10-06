# -*- coding: utf-8 -*-
"""
Created on Tue Nov  1 14:49:25 2016

@author: root
"""
#==============================================================================
# generate all the mapping infos 
#    user: IP, country, longitude, latitude, AS
#    ws: company, country, AS
#==============================================================================
def GenerateMappingInfos():
    import numpy as np
    userList = "dataset/rendi2/userlist.txt"
    wsList = "dataset/rendi2/wslist.txt"
    userMappingInfo = np.loadtxt(userList, dtype=str, delimiter='\t', usecols = (1, 2, 3, 4), converters = {1: lambda s: ''.join(s.split('.')[:3]), 3: lambda s: s.split('.')[0], 4: lambda s: s.split('.')[0]})
    wsMappingInfo = np.loadtxt(wsList, usecols = (2, 3), dtype=str, delimiter='\t')
    userAs = np.loadtxt("dataset/rendi2/userMapping.txt", dtype=str, usecols=(2, ))[:, np.newaxis]
    wsAs = np.loadtxt("dataset/rendi2/webServerMapping.txt", dtype=str, usecols=(2, ))[:, np.newaxis]
    userMappingInfo = np.hstack((userMappingInfo, userAs))
    wsMappingInfo = np.hstack((wsMappingInfo, wsAs))
    np.savetxt("dataset/rendi2/userInfo", userMappingInfo, fmt='%s', delimiter='\t')
    np.savetxt("dataset/rendi2/wsInfo", wsMappingInfo, fmt='%s', delimiter='\t')
    return userMappingInfo, wsMappingInfo
   

#==============================================================================
# execute the mapping  and save 
#==============================================================================
def loadRating(fileName, modify):
    import numpy as np
    result = np.loadtxt(fileName)
    if modify:
        result[:, 0] -=  1 #modify index start with 0
        result[:, 1] -=  1
    if result.shape[1] == 4:
        result = result[:, 1:]
    return result
def doMapping(srcFile, dirFile, modify=True):
    import numpy as np
    userMappingInfo = np.loadtxt("dataset/rendi2/userInfo", dtype=str, delimiter='\t')
    wsMappingInfo = np.loadtxt("dataset/rendi2/wsInfo", dtype=str, delimiter='\t')
    userWsRating = loadRating(srcFile, modify=modify)
    result = []
    for user, ws, rating in userWsRating:
        temp = []
        temp.extend(userMappingInfo[int(user)].tolist())
        temp.extend(wsMappingInfo[int(ws)])
        temp.append(str(rating))
        result.append(temp)
    result = np.array(result)
    np.savetxt(dirFile, result, fmt='%s', delimiter='\t')
    
    
NoneValue = 111111
#==============================================================================
# generate the similarity matrix
#==============================================================================
#距离:闵可夫斯基  2:欧式距离  1:曼哈顿距离
def simMinkowskiDist(inA, inB, n=2.0):
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
#创建矩阵   
def loadArrayObj(fileName, userNum=339, wsNum=5825):
    import numpy as np
    trainObj = np.loadtxt(fileName)
    userLs, wsLs, rt  = trainObj[:, 0], trainObj[:, 1], trainObj[:, 2]
    userLs = np.array(userLs, dtype=int) - 1
    wsLs = np.array(wsLs, dtype=int) - 1
    rt = np.array(rt, dtype=float)
    arrayObj = np.empty((userNum, wsNum))
    arrayObj.fill(NoneValue)
    arrayObj[userLs, wsLs] = rt 
    return arrayObj 
#相似度矩阵    
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


#==============================================================================
#cal the co-occurrence matrix by DBSCAN
#==============================================================================
def createCoOccurrenceMatrixByDBSCAN(fileName):
    import numpy as np
    from sklearn.cluster import DBSCAN
    dbscan = DBSCAN(eps=0.10, min_samples=2, metric='manhattan')
    trainArray = loadArrayObj(fileName)
    rowNum, columnNum = trainArray.shape
    #cal the CoOccurrenceMatrix by DBSCAN
    coOccurrenceMatrix = np.zeros((rowNum, rowNum))
    for columnIndex in range(columnNum):
#        print columnIndex
        data1 = trainArray[:, columnIndex]
        data = data1[data1 != NoneValue]
        index = np.argwhere(data1 != NoneValue)[:, -1]
        labels =  dbscan.fit_predict(data.reshape(-1, 1))
        for label in set(labels):
            if len(labels[labels == label]) > 1:
                for row in index[labels == label]:
                    coOccurrenceMatrix[row, index[labels == label]] += 1                    
    for rowIndex in range(rowNum):
        coOccurrenceMatrix[rowIndex, rowIndex] = 0 
    return coOccurrenceMatrix
        

if __name__ == '__main__':  
    import time
    start = time.time()
    import numpy as np
    from sklearn.preprocessing import minmax_scale
    for sparess in [5, 10, 15, 20]:
        for fileNum in range(1, 11):
#            trainFile = "dataset/rendi2/train/sparseness%s/training%d.txt" % (sparess, fileNum)
#            testFile = "dataset/rendi2/test/sparseness%s/test%d.txt" % (sparess, fileNum)
#            
#==============================================================================
# #            #do mapping
#==============================================================================
            trainFile = "dataset/rendi2/sample/training-%d-%d" % (sparess, fileNum)        
            testFile = "dataset/rendi2/puiAnalyze/puiAnalyze-%d-%d" % (sparess, fileNum)        
            doMapping(trainFile, trainFile.split('.')[0] + 'feature', modify=False)
            doMapping(testFile, testFile.split('.')[0] + 'feature', modify=False) 
            break
        break
            
            
#==============================================================================
# #            # create co-occurrence matrix by DBSCAN
#==============================================================================
#            coOccurrenceMatrix = createCoOccurrenceMatrixByDBSCAN(trainFile)
#            coOccurrenceMatrix = minmax_scale(coOccurrenceMatrix, axis=1)
#            np.savetxt(trainFile.split('.')[0] + 'cooSimMatrix', coOccurrenceMatrix, delimiter='\t', fmt='%f')
            
            
#==============================================================================
#             # create sim  matrix by Euclidean
#==============================================================================
#            simArray = createSimArray(trainFile)
#            np.savetxt(trainFile.split('.')[0] + 'euSimMatrix', simArray, delimiter='\t', fmt='%f')
#        
#            print sparess, fileNum
    
    

##    
    print 'during time ...' , (time.time() - start)



    
    
    
    
    
    
    
