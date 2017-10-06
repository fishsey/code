# -*- coding: utf-8 -*-
"""
Created on Sat Mar 11 10:58:54 2017

@author: root
"""
import numpy as np
import tensorflow as tf
from paper.chengru import dataProcess
from sklearn.neural_network import MLPRegressor
from sklearn.metrics import mean_absolute_error, mean_squared_error



def predict(trainFile, testFile):
    print "ready to test"
    #mlp regress estimater
    clf = MLPRegressor(hidden_layer_sizes=100)
    clf.fit(train[:, :-1], train[:,-1])
    pred = clf.predict(test[:, :-1])
    ture = test[:, -1]
    print mean_absolute_error(ture, pred)
    print mean_squared_error(ture, pred) ** 0.5
    

if __name__ == "__main__":
    trainFile = "/root/AAA/dataset/ml/ml-1m/traing"
    testFile = "/root/AAA/dataset/ml/ml-1m/test"
#    train = dataProcess.transToPQFeatures(trainFile)
#    test = dataProcess.transToPQFeatures(testFile)
    predict(trainFile, testFile)
