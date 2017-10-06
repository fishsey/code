# -*- coding: utf-8 -*-
"""
Created on Fri Feb 19 14:07:50 2016

@author: fishsey
"""
from math import sqrt
import numpy as np
import paper

def generateAllPuiArray(sparseness = 5, i = 1):
    fileName = r'throught/pui/slopeone-%d-%d.txt' % (sparseness, i)
    result = np.loadtxt(fileName).reshape(339, 5825)
    paper.save(result, 'throught/allpuiArray-%d-%d.txt' % (sparseness, i)) 
        
def predict(u, i, p, q):
    return  (p[u] * q[i].T)[0,0]

def initTrainMatrix():
    p = np.random.rand(userNum, feature) * (1/sqrt(feature))
    q = np.random.rand(itemNum, feature) * (1/sqrt(feature))
    return np.mat(p), np.mat(q)
    
#def learningAddIndicateFunctionlfm(n, alpha, lamd):
#    global fileNameAllPuiArray
#    p, q = initTrainMatrix()
#    puiArray = paper.load(fileNameAllPuiArray)
#    data = np.zeros((n, feature))
#    for step in range(0, n):
#        data[step] = np.array(p)[111]
#        number = 0
#        mae = 0
#        for u in range(userNum):
#            for i in range(itemNum):
#                if puiArray[u, i] != 111:
#                    number += 1 #number 记录训练集中的评分总数
#                    rui = puiArray[u, i]
#                    pui = predict(u, i, p, q)
#                    eui = rui - pui
#                    mae += abs(eui)
#                    #修正 p\q                 
#                    temp = p[u,] + alpha * (q[i, ] * eui - lamd * p[u,])
#                    q[i,] += alpha * (p[u,] * eui - lamd * q[i,])
#                    p[u,] = temp  
#                else:
#                    p[u,] -= alpha * lamd * p[u,]
#                    q[i,] -= alpha * lamd * q[i,]  
#        nowMae = mae / number
#        print 'step: %d      mae: %f' % ((step + 1), nowMae)
#        alpha *= 0.9
#    np.savetxt('data1.txt', data)
#    return p, q

def learningAddIndicateFunctionlfm(n, alpha, lamd):
    global fileNameAllPuiArray
    p, q = initTrainMatrix()
    puiArray = paper.load(fileNameAllPuiArray)
    #0-1
    puiArray = (puiArray - 44.034)/ 107.439
    import random
    for step in range(0, n):
        number = 0
        mae = 0
        userList = range(userNum)
        while len(userList) > 0:
            u = random.choice(userList)
            del userList[userList.index(u)]
            itemList= range(itemNum)
            while len(itemList) > 0:
                i = random.choice(itemList)
                del itemList[itemList.index(i)]
                if puiArray[u, i] != 111:
                    number += 1 #number 记录训练集中的评分总数
                    rui = puiArray[u, i]
                    pui = predict(u, i, p, q)
                    eui = rui - pui
                    mae += abs(eui)
                    #修正 p\q                 
                    temp = p[u,] + alpha * (q[i, ] * eui - lamd * p[u,])
                    q[i,] += alpha * (p[u,] * eui - lamd * q[i,])
                    p[u,] = temp  
                else:
                    p[u,] -= alpha * lamd * p[u,]
                    q[i,] -= alpha * lamd * q[i,]  
        nowMae = mae / number
        print 'step: %d      mae: %f' % ((step + 1), nowMae)
        alpha *= 0.9
    return p, q
    
 
if __name__ == '__main__':
    #设置参数
    sparseness = 20
    fileNums = 10
    userNum = 339
    itemNum = 5825
    feature = 80 #特征数
    steps = 20#迭代次数
    alpha = 0.01 #step-length
    lamd = 0.02 #正则化参数(惩罚因子)
    #测试多个训练集，取平均误差
    for number in range(1, fileNums + 1):
        print number
        generateAllPuiArray(sparseness, number)
        fileNameAllPuiArray = 'throught/allpuiArray-%d-%d.txt' % (sparseness, number)
        p, q = learningAddIndicateFunctionlfm(steps, alpha, lamd)
        pFile = r'throught/p-%d-%d.txt' % (sparseness, number)
        qFile = r'throught/q-%d-%d.txt' % (sparseness, number)
        paper.save(p, pFile)
        paper.save(q, qFile)
        
