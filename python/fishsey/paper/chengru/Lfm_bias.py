# -*- coding: utf-8 -*-
"""
Created on Sun Feb 28 10:49:58 2016

@author: fishsey
"""
import numpy as np
from math import sqrt
from paper.chengru import dataProcess

def initBaiasLfm():
    p = np.random.rand(userNum, feature) * (1/sqrt(feature))
    q = np.random.rand(itemNum, feature) * (1/sqrt(feature))
    bu = np.zeros(userNum)
    bi = np.zeros(itemNum)
    return np.mat(p), np.mat(q), bu, bi

def predict(u, i, p, q, bu, bi, mean):
    ret = mean + bu[u] + bi[i]
#    ret = 0.0
    ret += (p[u] * q[i].T)[0, 0]
    return ret
     
def learningBiasLfm(trainFile, n, alpha, lamd):
    p, q, bu, bi = initBaiasLfm()
    trainMatrix = dataProcess.loadMatrix(trainFile)
    means = np.mean(trainMatrix[trainMatrix!=-1])
    for step in range(0, n):
        print step,
        number = 0
        mae = 0.0
        rmse = 0.0
        for u in range(userNum):
            for i in range(itemNum):
                if np.random.randint(0, 1000) <= 900:
                        continue
                if trainMatrix[u, i] != -1:  #如果u-i 在训练集中
                    number += 1 #number 记录训练集中的评分总数
                    rui = trainMatrix[u, i]
                    pui = predict(u, i, p, q, bu, bi, means)
                    eui = rui - pui
                    mae += abs(eui)
                    rmse += eui ** 2
                    #修正偏置项
                    bu[u] += alpha * (eui - lamd * bu[u])                
                    bi[i] += alpha * (eui - lamd * bi[i]) 
                    #修正 p\q                 
                    temp = p[u,] + alpha * (q[i, ] * eui - lamd * p[u,])
                    q[i,] += alpha * (p[u,] * eui - lamd * q[i,])
                    p[u,] = temp  
#                else:
#                    bu[u] -= alpha * lamd * bu[u]               
#                    bi[i] -= alpha * lamd * bi[i]
#                    p[u,] -= alpha * lamd * p[u,]
#                    q[i,] -= alpha * lamd * q[i,]
        nowMae = mae / number
        nowRmse = (rmse / number) * 0.5
        print nowMae, nowRmse
        alpha *= 0.95
    return p, q, bu, bi, means
    
def calErr(testFile, p, q, bu, bi, mean):
    #cal mae and Rmse
    mae = 0.0
    rmse = 0.0
    count = 0.0
    print 'ready for test'
    testVec = np.loadtxt(testFile, dtype=int, delimiter="::", usecols=(0, 1, 2))
    for line in testVec:
        user, item, tui = line
        user -= 1
        item -= 1
        pui = predict(user, item, p, q, bu, bi, mean)
        err = tui - pui
        mae += abs(err)
        rmse += err ** 2
        count += 1
    print mae/count, (rmse/count) ** 0.5
    
    
if __name__ == '__main__':
    #设置参数
    userNum = 6040
    itemNum = 3952
    feature = 66 #特征数
    steps = 10#迭代次数
    alpha = 0.1#learing rate
    lamd = 0.05 #正则化参数(惩罚因子)
    #cal error
    trainFile = "/root/AAA/dataset/ml/ml-1m/traing"
    testFile = "/root/AAA/dataset/ml/ml-1m/test"
#    p, q, bu, bi, mean = learningBiasLfm(trainFile, steps, alpha, lamd)
#    calErr(testFile, p, q, bu, bi, mean)
    np.savetxt("/root/AAA/dataset/ml/chengru/p", p, delimiter="\t")
    np.savetxt("/root/AAA/dataset/ml/chengru/q", q, delimiter="\t")
    np.savetxt("/root/AAA/dataset/ml/chengru/bu", bu, delimiter="\t")
    np.savetxt("/root/AAA/dataset/ml/chengru/bi", bi, delimiter="\t")
#    np.savetxt("/root/AAA/dataset/ml/chengru/mean", mean, delimiter="\t")
    
   
    
    