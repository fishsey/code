# -*- coding: utf-8 -*-
"""
Created on Tue Mar  7 15:31:50 2017

@author: root
"""
import numpy as np
import tensorflow as tf
NoneValue = -1
def loadMatrix(fileName, userNum=6040, itemNum=3952):
    #(user, item, rating), discard timestamp information
    rating = np.loadtxt(fileName, dtype=int, delimiter="::", usecols=(0, 1, 2))
    result = np.empty((userNum, itemNum))
    result.fill(NoneValue)
    users = rating[:,0] - 1
    items = rating[:,1] - 1
    result[users, items] = rating[:, 2] 
    return result  

def loadArray(fileName):
    result = np.loadtxt(fileName, dtype=int, delimiter="::", usecols=(0, 1, 2))
    result[:, 0] -=  1 #modify index start with 0
    result[:, 1] -=  1
    return result
    
    
def transToPQFeatures(fileName, userNum=6040, itemNum=3952):
    pFile = "/root/AAA/dataset/ml/chengru/p"
    qFile = "/root/AAA/dataset/ml/chengru/q"
    bu = "/root/AAA/dataset/ml/chengru/bu"
    bi = "/root/AAA/dataset/ml/chengru/bi"
    #data
    p = np.loadtxt(pFile)
    q = np.loadtxt(qFile)
    bu = np.loadtxt(bu)
    bi = np.loadtxt(bi)
    mean = 3.5804304422749378
    array = loadArray(fileName)
    #result 
    result = np.empty((len(array), 2*len(p[0])+1+3))
#    print result.shape
    for index, line in enumerate(array):
#        print index
        temp = []
        user, item, tui = line
        #p, q
        temp.extend(p[user])
        temp.extend(q[item])
        #bu, bi, mean
        temp.append(bu[user])
        temp.append(bi[item])
        temp.append(mean)
        #tui
        temp.append(tui)
        result[index] = np.array(temp)
    return result
    
    

if __name__ == "__main__":
    trainFile = "/root/AAA/dataset/ml/ml-1m/traing"
    testFile = "/root/AAA/dataset/ml/ml-1m/test"
#    res = loadMatrix(testFile)
    result = transToPQFeatures(testFile)
    
