# -*- coding: utf-8 -*-
"""
Created on Mon Mar 13 14:19:59 2017

@author: root
"""
import numpy as np
import tensorflow as tf
import loadData_mnistSelf as ldself
import loadData_mnist as ld
from timer import timerFunc

def nextBatch(x, y, minBatch=50):
    indices = np.random.randint(0, len(x), minBatch)
    return x[indices], y[indices]
    
@timerFunc
def model():
    global true_x, true_y, tx, ty
    featureNums = len(true_x[0]) #the number of features
    labelNums = len(true_y[0]) #the number of labels
    
#    mnist = input_data.read_data_sets("/home/fishsey/AAA/dataset/mnist", one_hot=True)
    
    #model paras
    w = tf.Variable(tf.zeros([featureNums, labelNums]))
    b = tf.Variable(tf.zeros([labelNums]))
    #input and output data
    x = tf.placeholder(tf.float32, [None, featureNums])
    y = tf.placeholder(tf.float32, [None, labelNums])
    #construct model
    predy = tf.nn.softmax(tf.matmul(x, w) + b)
    loss = tf.reduce_mean(-tf.reduce_sum(y * tf.log(predy), 1)) + lamda * tf.reduce_sum(w * w)
    train = tf.train.GradientDescentOptimizer(alpha).minimize(loss)
    ##run model
    init = tf.global_variables_initializer()
    sess = tf.Session()
    sess.run(init)
    with tf.device("/cpu:0"):
        for i in range(100):
            if i % 10 == 0:
                print (i)
            batch_x, batch_y = nextBatch(true_x, true_y, 1000)
            sess.run(train, feed_dict={x:batch_x, y:batch_y})
#            print sess.run(loss, feed_dict={x:batch_x, y:batch_y})
#        py = sess.run(predy, feed_dict={x:tx})
        correctBoolean = tf.equal(tf.argmax(predy, 1), tf.arg_max(y, 1))
        accurary = tf.reduce_mean(tf.cast(correctBoolean, tf.float32))
        print (sess.run(accurary, feed_dict={x:tx, y:ty}))
        
    
if __name__ == "__main__":
    true_x, true_y, tx, ty = ldself.loadTrainAndTestDatasetWithLabelEncode()
#    true_x, true_y, tx, ty = ld.mainWithLabelEnocde()
    alpha = 0.02
    lamda = 0.1
    model()
    
    
    
    
