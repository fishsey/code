# -*- coding: utf-8 -*-
"""
Created on Tue Mar  7 16:03:20 2017

@author: root
"""
import numpy as np
import tensorflow as tf
from paper.chengru import lshNeighbors
from paper.chengru import dataProcess

def calErr(trainFile, testFile):
    #cal mae and Rmse
    mae = 0.0
    rmse = 0.0
    count = 0.0
    topk = 500
    print 'ready for test'
    for line in testVec:
        user, item, tui = line
        user -= 1
        item -= 1
        distance, neighbors = lshClf.kneighbors(testMatrix[user].reshape(1, -1), topk)
        pui = predict(user, item, distance[0], neighbors[0], trainMatrix)
        err = tui - pui
        mae += abs(err)
        rmse += err ** 2
        count += 1
        if count % 100 == 0:
            print count
    print mae/count, (rmse/count) ** 0.5

def predict(user, item, distance, neighbors, trainMatrix):
    sim = 0.0
    rating = 0.0
    for index, neighbor in enumerate(neighbors):
        if trainMatrix[neighbor, item] != -1:
            rating += trainMatrix[neighbor, item] * 1.0/distance[index]
            sim += 1.0/distance[index]
    if sim == 0.0:
        return np.mean(trainMatrix[user][trainMatrix[user]!=-1])
    return rating/sim
            
    

if __name__ == "__main__":
    trainFile = "/root/AAA/dataset/ml/ml-1m/traing"
    testFile = "/root/AAA/dataset/ml/ml-1m/test"
    #load data
    testMatrix = dataProcess.loadMatrix(testFile)
    trainMatrix = dataProcess.loadMatrix(trainFile)
    lshClf = lshNeighbors.lshEstimate(trainMatrix)
    testVec = np.loadtxt(testFile, dtype=int, delimiter="::", usecols=(0, 1, 2))
    #cal error
    calErr(trainFile, testFile)
    
