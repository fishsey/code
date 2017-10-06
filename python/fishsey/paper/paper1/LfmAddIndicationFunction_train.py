# -*- coding: utf-8 -*-
"""
Created on Fri Feb 19 14:07:50 2016

@author: fishsey
"""
from math import sqrt
import numpy as np
import paper
        
def predict(u, i, p, q):
    return  (p[u] * q[i].T)[0,0]

def initTrainMatrix():
    p = np.random.rand(userNum, feature) * (1/sqrt(feature))
    q = np.random.rand(itemNum, feature) * (1/sqrt(feature))
    return np.mat(p), np.mat(q)
    
def learningAddIndicateFunctionlfm(n, alpha, lamd):
    p, q = initTrainMatrix()
    for step in range(0, n):
        number = 0
        mae = 0
        for u in range(userNum):
            for i in range(itemNum):
                if trainArrayObj[u, i] != 111:  #如果u-i 在训练集中
                    number += 1 #number 记录训练集中的评分总数
                    rui = trainArrayObj[u, i]
                    pui = predict(u, i, p, q)
                    eui = rui - pui
                    mae += abs(eui)
                    #修正 p\q                 
                    temp = p[u,] + alpha * (q[i, ] * eui - lamd * p[u,])
                    q[i,] += alpha * (p[u,] * eui - lamd * q[i,])
                    p[u,] = temp
                else:
                    #修正 p\q                 
                    p[u,] -= alpha * lamd * p[u,] 
                    q[i,] -= alpha * lamd * q[i,] 
        nowMae = mae / number
        print 'step: %d      mae: %f' % ((step + 1), nowMae)
        alpha *= 0.9
    return p, q
 
if __name__ == '__main__':
    #设置参数
    sparseness = 5
    number = 1
    userNum = 339
    itemNum = 5825
    feature = 100 #特征数
    steps = 20#迭代次数
    alpha = 0.02 #step-length
    lamd = 0.01 #正则化参数(惩罚因子)
    #测试多个训练集，取平均误差
    trainFileName = r'dataSet/sparseness%d/training%d.txt' % (sparseness, number)
    trainArrayObj = paper.createArrayObj(trainFileName)
    p, q = learningAddIndicateFunctionlfm(steps, alpha, lamd)
    pFile = r'p-%d-%d.txt' %  (sparseness, number)
    qFile = r'q-%d-%d.txt' %  (sparseness, number)
    paper.save(p, pFile)
    paper.save(q, qFile)
