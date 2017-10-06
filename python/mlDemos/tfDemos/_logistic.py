# -*- coding: utf-8 -*-
"""
Created on Fri Oct 28 20:52:55 2016

@author: root
"""
import loadMnistDataset
import mnistDataUtil  
import tensorflow as tf
import numpy as np     
from timer import timerFunc


def logisticRegressModel(train, trueY, weights, b):
    predY = tf.nn.softmax(tf.matmul(train, weights) + b)
    
    loss = -tf.reduce_sum(predY * trueY)
#    loss = tf.reduce_sum(- predY * trueY) + lamda * tf.matmul(tf.transpose(weights), weights)
    
    trainOp = tf.train.GradientDescentOptimizer(alpha).minimize(loss)
    return predY, trainOp

def initVar(features):
    train = tf.placeholder(tf.float32, [None, features])#traing sample
    trueY = tf.placeholder(tf.float32, [None, 10])#single test sample
    return train, trueY

@timerFunc
def calMae(trainSamples, featureLens):
    #the optimizer paras
    weights = tf.Variable(tf.zeros([featureLens, labelLens]))
    b = tf.Variable(tf.zeros([labelLens]))
    initGlobal = tf.global_variables_initializer()
    initLocal = tf.local_variables_initializer()
    sess = tf.Session()
    sess.run(initGlobal)
    sess.run(initLocal)
    #data:placeholder
    train, trueY = initVar(featureLens)#use features init
    #model
    predY, trainOp = logisticRegressModel(train, trueY, weights, b)
    #iteration training
    for i in range(iterNums):
#        print i
        sampleX, sampleY = sampleBatch(high=trainSamples)
        sess.run(trainOp, feed_dict={train:sampleX, trueY:sampleY})
        
    predY = tf.argmax(predY, axis=1)
    correctRate = tf.reduce_mean(tf.cast(tf.equal(predY, testY), tf.float32))
    print sess.run(correctRate, feed_dict={train:testX})
    
    
#    indice = np.argmax(result, axis=1)
#    print len(indice[indice==testY])/ float(len(indice))
    
    
       
def sampleBatch(batchSize=1000, low=0, high=1933):
    indices = np.random.randint(low, high, batchSize)
    return trainX[indices], trainY[indices]
        
      

if __name__ == "__main__":
    alpha = 0.2 #the rate of learning
    lamda = 0.1
    iterNums = 100
    labelLens = 10
    #(1934, 1024), (1934, 10), (946, 1024), (946, )
#    trainX, trainY, testX, testY = loadMnistDataset.loadTrainAndTestDatasetWithLabelEncode()
#    trainX, trainY, testX, testY = mnistDataUtil.mainWithLabelEnocde()

#    trainSamples, featureLens = trainX.shape

    calMae(trainSamples, featureLens)


