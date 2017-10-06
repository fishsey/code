# -*- coding: utf-8 -*-
"""
Created on Tue Mar  7 15:28:06 2017

@author: root
"""
import numpy as np
import tensorflow as tf
from sklearn.neighbors import LSHForest
def lshEstimate(train):
    clf = LSHForest(random_state=11)
    clf.fit(train)
    return clf

from paper.chengru import dataProcess 
if __name__ == "__main__":
    trainFile = "/root/AAA/dataset/ml/ml-1m/traing"
    testFile = "/root/AAA/dataset/ml/ml-1m/test"
    train = dataProcess.loadMatrix(trainFile)
    clf = lshEstimate(train)
    
