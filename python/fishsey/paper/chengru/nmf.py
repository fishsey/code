# -*- coding: utf-8 -*-
"""
Created on Tue Mar  7 18:10:06 2017

@author: root
"""
import numpy as np
import tensorflow as tf



if __name__ == "__main__":
    trainFile = "/root/AAA/dataset/ml/ml-1m/traing"
    testFile = "/root/AAA/dataset/ml/ml-1m/test"
    #load data
#    testMatrix = dataProcess.loadMatrix(testFile)
#    trainMatrix = dataProcess.loadMatrix(trainFile)
#    lshClf = lshNeighbors.lshEstimate(trainMatrix)
#    testVec = np.loadtxt(testFile, dtype=int, delimiter="::", usecols=(0, 1, 2))
    #cal error
    calErr(trainFile, testFile)
    
