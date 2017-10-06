# -*- coding: utf-8 -*-
"""
Created on Mon Dec 19 21:00:46 2016

@author: root
"""

import tensorflow as tf
import numpy as np
import collections
from timer import timerFunc
import loadData_mnistSelf as ld

    
def topK(array, k=1):
    maxValue = max(array)
    result = []
    for i in range(k):
        index = np.argmin(array)#the index of minLossValue
        array[index] = maxValue
        result.append(index)
    #neighbors label
    labels = ty[result]
    #major vote with neighbors label
    return collections.Counter(labels).most_common(1)[0][0]
    
    
def initVar(features):
    train = tf.placeholder(tf.float32, [None, features])#totally traing sample
    test = tf.placeholder(tf.float32, [features])#single test sample
    return train, test
    

def knnModel(train, test):
    diffDistance = tf.reduce_sum((train - test) ** 2, axis=1)#L2 distance
    return diffDistance
    
    

@timerFunc      
def calMae():
    init = tf.global_variables_initializer()
    sess = tf.Session()
    sess.run(init)
    #placeholder
    train, test = initVar(x.shape[1])#use features init
    #model
    model = knnModel(train, test)
    #test for every sample
    count = 0
    for i, sample in enumerate(tx):
        diffDistance = sess.run(model, feed_dict={train:tx, test:sample})
        
        labelPred = topK(diffDistance, k=3) #use majority voting
        
        if labelPred == ty[i]:
            count += 1
        print (count, float(count)/(i+1))
    
    

if __name__ == "__main__":
    x, y, tx, ty = ld.loadTrainAndTestDataset()
    calMae()
    
    
    
    
    
        
        
    
    
