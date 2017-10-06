# -*- coding: utf-8 -*-
"""
Created on Sun Mar 06 10:17:13 2016

@author: fishsey
"""           
def calMaeAndRmse(trainArrayObj, testArrayObj, yClassify):
    MAE = 0.0
    RMSE = 0.0
    number = 0
    for index, line in enumerate(testArrayObj):
        label = yClassify[index]
        u, i, tui = int(line[0]), int(line[1]), line[2]
        if label == -1:
            pui = -1
        else:
            pui = predictSlopeOne(u, i, trainArrayObj, label)
        eui = tui - pui
        # write data to the file
        pfEui.write(str(eui) + str(tui) +str(pui) +str(label) +'\n')
        RMSE += pow(eui, 2)
        MAE += abs(eui)
        number += 1  
    RMSE = math.sqrt(RMSE / number)
    MAE /= number
    return MAE, RMSE    
  
def predictSlopeOne(u, i, trainArrayObj, label):
    #初始化参数
    columnNum = trainArrayObj.shape[1]
    differ = np.zeros(columnNum)
    numbers = np.zeros(columnNum)
    dev = np.zeros(columnNum)
    rating = trainArrayObj[u]
    #依次遍历每列
    for index in range(wsNum):
        for memberId in range(userNum):
            if rating[index] != NoneValue and rating[index] >= 0 and index != i and memberId != u and trainArrayObj[memberId,index] != NoneValue and trainArrayObj[memberId,index] >= 0 and trainArrayObj[memberId,i] != NoneValue and trainArrayObj[memberId,i] >= 0 and abs(np.floor(rating[index]) - label) <= 1 and abs(np.floor(trainArrayObj[memberId,index]) - label) <= 1 and abs(np.floor(trainArrayObj[memberId,i]) - label) <= 1:
                 differ[index] += (trainArrayObj[memberId,i] - trainArrayObj[memberId,index]) 
                 numbers[index] += 1.0
    for index in range(columnNum):
        if numbers[index]:
            dev[index] = differ[index]/ numbers[index]
    # 进行预测
    rating = rating + dev
    sums = sum(numbers)
    if sums == 0:
        return label + 0.5
    return  (np.mat(rating) * np.mat(numbers).T)[0,0] / sums


def classify(train, test):
    from _paper2 import dmt
    import numpy as np
    pui = dmt.classifyEntropy(train, test)
    return pui
    
    
import math
import numpy as np
import paper
if __name__ == '__main__': 
    NoneValue = 111111.0
    userNum = 339
    wsNum = 5825
    for sparseness in [5]:
        for num in range(1, 2):
            #文件对象
            euiFileName = 'eui-%d-%d.txt' % (sparseness, num)
            pfEui = open(euiFileName, 'w')
            #load train and test
            trainFile = r'rt/sparseness%d/training%d.txt' % (sparseness, num)
            trainArrayObj = paper.createArrayObj(trainFile)
            testFile = r'rt/sparseness%d/test%d.txt' % (sparseness, num)
            testArrayObj = paper.loadTest(testFile)
            #load train info and test info
            trainFileInfo = r'data/sparseness%d/training%d.txt' % (sparseness, num)
            trainInfoObj = paper.loadTest(trainFileInfo)
            testFileInfo = r'data/sparseness%d/test%d.txt' % (sparseness, num)
            testInfoObj = paper.loadTest(testFileInfo)
            #classify result
            yClassify = classify(trainInfoObj, testInfoObj)
            mae, rmse = calMaeAndRmse(trainArrayObj, testArrayObj, yClassify) 
            print 
            print mae, rmse
            pfEui.close()
        print 'ok'
    
    
    
    