# -*- coding: utf-8 -*-
"""
Created on Tue Jan 10 18:07:54 2017

@author: root
"""




def feedback(a, weights, bias,keepDrop):
    #a is the input-layer data
    for w, b in zip(weights[:-1], bias[:-1]):
        a = tf.nn.relu(tf.add(tf.matmul(a, w), b))
        a = tf.nn.dropout(a, keepDrop)
    return tf.nn.softmax(tf.add(tf.matmul(a, weights[-1]), bias[-1])) 


def model(batchSamples, trueY, weights, biases, keepDrop, alpha=0.1, lamda=0.01):
    predy = feedback(batchSamples, weights, biases, keepDrop)
    loss = tf.reduce_mean(-tf.reduce_sum(trueY * tf.log(predy), 1)) 
#    + lamda * pow2(weights)
    trainOp = tf.train.AdagradOptimizer(alpha).minimize(loss)
#    trainOp = tf.train.AdagradOptimizer(alpha).minimize(loss)
    return trainOp
    
#参数惩罚：防止过拟合
def pow2(weights):
    punish = tf.zeros(1)
    for weight in weights:
        punish = tf.add(tf.reduce_sum(tf.pow(weight, 2.0)), punish)
    return punish
        
def nextBatch(x, y, minBatch):
    indices = np.random.randint(0, len(x), minBatch)
    return x[indices], y[indices]    

    
def evaluate(weights, bias, testX, testY, keepDrop):
    predy = feedback(testX, weights, bias, keepDrop)
    correctBoolean = tf.equal(tf.argmax(predy, 1), tf.arg_max(testY, 1))
    accurary = tf.reduce_mean(tf.cast(correctBoolean, tf.float32))
    return accurary



@timerFunc
def train(trainX, trainY, weights, bias, testX, testY):
    global x, y, keepDrop
    iterNums = 300
    batchSize = 100
    with tf.Session() as sess:
        with tf.device("/cpu:0"):
            trainOp = model(x, y,  weights, bias, keepDrop)
            evaOp = evaluate(weights, bias, x, y, keepDrop)
            initGlobal = tf.global_variables_initializer()
            sess.run(initGlobal)
            #iteration training
            for i in range(iterNums):
                if i % 10 == 0:
                    print (i, end='\t')
                batch_x, batch_y = nextBatch(trainX, trainY, batchSize)
                sess.run(trainOp, feed_dict={x:batch_x, y:batch_y, keepDrop:0.75})
                if i % 10 == 0:
                    print (sess.run(evaOp, feed_dict={x:batch_x, y:batch_y, keepDrop:1.0}))
            print (sess.run(evaOp, feed_dict={x:testX, y:testY, keepDrop:1.0}))
                

import numpy as np
import tensorflow as tf
import loadData_mnistSelf as ldself
import loadData_mnist as ld
from timer import timerFunc  
if __name__ == "__main__":
    trainX, trainY, testX, testY = ldself.loadTrainAndTestDatasetWithLabelEncode()
#    trainX, trainY, testX, testY = ld.mainWithLabelEnocde()
#    init paras
    inputLayerNum = len(trainX[0]) #1024 or 784
    outputLayerNum = len(trainY[0]) #10
    hideLayerNum = [50, 100, 50]
    fullLayers = hideLayerNum
    fullLayers.insert(0, inputLayerNum)
    fullLayers.append(outputLayerNum)
    #init weights and bias
    ##hidden-layer: use relu() as activate-function
    weights = [tf.Variable(tf.truncated_normal([before, after],stddev=0.1, dtype=tf.float32)) for before, after in zip(fullLayers[:-2], fullLayers[1:-1])]
    ##output-layer: use sigmod() as activate-function
    weights.append(tf.Variable(tf.zeros(fullLayers[-2:])))
    bias = [tf.Variable(tf.zeros(num)) for num in fullLayers[1:]]
    #set placeholder
    x = tf.placeholder(tf.float32, [None, inputLayerNum])
    y = tf.placeholder(tf.float32, [None, outputLayerNum])
    keepDrop = tf.placeholder(tf.float32)
    #cal the mae
    train(trainX, trainY, weights, bias, testX, testY)
