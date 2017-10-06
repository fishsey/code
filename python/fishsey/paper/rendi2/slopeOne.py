# -*- coding: utf-8 -*-
"""
Created on Sun Mar 06 10:17:13 2016

@author: fishsey
"""         

def getNeighbors(user, ws, k=None, simArray=None, trainArray=None):
    import numpy as np
    result = []
    for v in range(339):
        if trainArray[v, ws] != NoneValue:
            result.append(v)
            
    result = np.array(result)
    simResult = simArray[user, result]
    if len(result) >= k:
        return result[np.argsort(simResult)[-k:]]
    else:
        return result
        
        
def calMaeAndRmse(trainArray, testData, simArray):
    eui = 0.0
    count = 0.0
    for index, line in enumerate(testData):
        print index, 
        u, i, tui = int(line[1]), int(line[2]), line[3]
        pui = predictSlopeOne(u, i, trainArray, simArray)
        if pui is not None:
            eui +=  abs(tui - pui)
            count += 1.0
            print count, '\t', eui/count
  
def predictSlopeOne(u, i, trainArrayObj, simArray):
    #初始化参数
    import numpy as np
    maxDiff = 1.0
    
    #cal 
    columnNum = trainArrayObj.shape[1]
    differ = np.zeros(columnNum)
    numbers = np.zeros(columnNum)
    dev = np.zeros(columnNum)
    rating = trainArrayObj[u]
    
    #sim neighbors
    neighbors = getNeighbors(u, i, k=2, simArray=simArray, trainArray=trainArrayObj)
    
    
    #依次遍历每列
    for index in range(wsNum):
        for memberId in range(userNum):
            if memberId in neighbors and rating[index] != NoneValue and index != i and memberId != u and trainArrayObj[memberId,index] != NoneValue and trainArrayObj[memberId,i] != NoneValue and abs(rating[index] - trainArrayObj[memberId,index]) <= maxDiff:
                 differ[index] += (trainArrayObj[memberId,i] - trainArrayObj[memberId,index]) 
                 numbers[index] += 1.0
                 
                 
    for index in range(columnNum):
        if numbers[index]:
            dev[index] = differ[index]/ numbers[index]
            
    # 进行预测
    rating = rating + dev
    sums = sum(numbers)
    if sums == 0:
        return None
    return  (np.mat(rating) * np.mat(numbers).T)[0,0] / sums
    
    

if __name__ == '__main__': 
    import time
    from paper.rendi2 import loadDataset as ld
    import numpy as np
    start = time.time()
    NoneValue = 111111
    userNum = 339
    wsNum = 5825
    for sparess in [5]:
        for fileNum in range(1, 2):    
#            sampleTrainFile = "dataset/rendi2/sample/training-%d-%d" % (sparess, fileNum)      
            trainFile = "dataset/rendi2/train/sparseness%s/training%d.txt" % (sparess, fileNum)
            testFile = "dataset/rendi2/puiAnalyze/puiAnalyze-%d-%d" % (sparess, fileNum)        
            simFile = "dataset/rendi2/train/sparseness%s/training%deuSimMatrix" % (sparess, fileNum)
            simArray = np.loadtxt(simFile)
            
            trainArray = ld.loadArrayObj(trainFile, modify=True)
            testData = ld.loadTestList(testFile, modify=False)
            
            calMaeAndRmse(trainArray, testData, simArray)
            
    #end modeling
    print "during time ... " , time.time() - start
            
            
            
            
    
    
    
    