# -*- coding: utf-8 -*-
"""
Created on Fri Mar 17 19:01:23 2017

@author: fishsey
"""
import numpy as np
import tensorflow as tf
import loadData_mnistSelf as ldself
import loadData_mnist as ld
from tensorflow.examples.tutorials.mnist import input_data


def weightVariable(shape,stddev=0.1):
    inital = tf.truncated_normal(shape, stddev=stddev)
    return tf.Variable(inital)

def biasVariable(shape, defaultValue=0.1):
    shape = [shape]
    inital = tf.constant(defaultValue, shape=shape)
    return tf.Variable(inital)

def conv2d(x, w,step=1,padding='SAME'):
    return tf.nn.conv2d(x, w, strides=[1,step,step,1], padding=padding)

def maxPool2x2(x,step=2,padding='SAME'):
    return tf.nn.max_pool(x, ksize=[1,step,step,1], strides=[1,step,step,1], padding=padding)
    
def ConvAndPool(xImage, wConv, bConv):
    hConv = tf.nn.relu(conv2d(xImage, wConv) + bConv) #卷积核输出
    hPool = maxPool2x2(hConv) #最大池化层输出最大特征值
    return hPool

def fullConnLayer(flatImage, keepDrop, weights, bias):
    fullConnLayerOutput = tf.nn.relu(tf.matmul(flatImage, weights) + bias)
    return tf.nn.dropout(fullConnLayerOutput, keepDrop)


def outputlayer(fullConnLayerOutput, weights, bias):
    predy = tf.nn.softmax(tf.matmul(fullConnLayerOutput, weights) + bias)
    return predy
    
    
def model(predy, y, alpha=1e-4, lamda=0.01):
    loss = tf.reduce_mean(-tf.reduce_sum(y * tf.log(predy), 1)) 
#    + lamda * pow2(weights)
    trainOp = tf.train.AdamOptimizer(alpha).minimize(loss)
#    trainOp = tf.train.AdagradOptimizer(alpha).minimize(loss)
    return trainOp

def evaluate(predy, testY):
    correctBoolean = tf.equal(tf.argmax(predy, 1), tf.argmax(testY, 1))
    accurary = tf.reduce_mean(tf.cast(correctBoolean, tf.float32))
    return accurary

if __name__ == "__main__":
    mnistData = input_data.read_data_sets("mnistData/", one_hot=True)
    featureNums = 784 
    featuresSqrt = int(featureNums**0.5)
    labelNums = 10
    convSize = 5
    pollSize = 2
    convKeral_1 = 32
    convKeral_2 = 64
    fullConnLayerNums = 1024
    iterNums = 10000
    batchSize = 50
    remaidFeatures = int(featuresSqrt/pollSize/pollSize)
    #定义 placeholder
    x = tf.placeholder(tf.float32, [None, featureNums])
    y = tf.placeholder(tf.float32, [None, labelNums])
    keepDrop = tf.placeholder(tf.float32)
    #将输入转化为空间结构
    xImage = tf.reshape(x, [-1, featuresSqrt, featuresSqrt, 1])
#==============================================================================
#     #1个输入层
#     #2个卷积层
#     #1个全连接层
#     #1个输出层
#==============================================================================
    with tf.Session() as sess:
        #define all the paras
        wConv1 = weightVariable([convSize,convSize,1,convKeral_1])
        bConv1 = biasVariable(convKeral_1)
        wConv2 = weightVariable([convSize,convSize,convKeral_1,convKeral_2])
        bConv2 = biasVariable(convKeral_2)
        wFullLayer = weightVariable([remaidFeatures*remaidFeatures*convKeral_2, fullConnLayerNums])
        bFullLayer = biasVariable(fullConnLayerNums) #隐藏神经元个数
        wOutput = weightVariable([fullConnLayerNums,labelNums])
        bOutput = biasVariable(labelNums) #输出层神经元个数
        
        #define the graph, as featureNums = 784(28*28)
        #output: [28,28] -> 32@[14,14]
        convAndPoll_1st = ConvAndPool(xImage, wConv1, bConv1)
        #output: 32@[14,14] -> 64@[7,7]
        convAndPoll_2st = ConvAndPool(convAndPoll_1st, wConv2, bConv2)
        #output:[1,1024], use dropOut 
        flatImage = tf.reshape(convAndPoll_2st, [-1, remaidFeatures*remaidFeatures*convKeral_2])
        fullConnLayerOutput = fullConnLayer(flatImage, keepDrop, wFullLayer, bFullLayer)
        #[1,10]
        predy = outputlayer(fullConnLayerOutput, wOutput, bOutput)
        #train or eva
        trainOp =  model(predy, y)
        evaOp = evaluate(predy, y)
        #init paras
        initGlobal = tf.global_variables_initializer()
        sess.run(initGlobal)
        with tf.device("/cpu:0"):
            for i in range(iterNums):
                batch_x, batch_y = mnistData.train.next_batch(batchSize)
                if i % 50 == 0:
                    print (i, end='\t')
                    print (sess.run(evaOp, feed_dict={x:batch_x, y:batch_y, keepDrop:1.0}))
                else:
                    sess.run(trainOp, feed_dict={x:batch_x, y:batch_y, keepDrop:0.75})
                
        print (sess.run(evaOp, feed_dict={x:mnistData.test.images, y:mnistData.test.labels, keepDrop:1.0}))
                
                
                
    
    
    
    
    
    
    
    
    
    
    
    
