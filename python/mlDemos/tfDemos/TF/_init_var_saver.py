# -*- coding: utf-8 -*-
"""
Created on Mon Dec 12 22:10:48 2016

@author: root
"""
import tensorflow as tf
import numpy as np

#create var without init
var1 = tf.Variable(tf.random_normal([2,2]), name="var1")
var2 = tf.Variable(tf.random_normal([2,2]), name="var2") #can use ndarray




#a var-save op
saveOp = tf.train.Saver()

#saveOp = tf.train.import_meta_graph("tf/myModel.meta")


with tf.Session() as sess:
    saveOp.restore(sess,  "tf/myModel")
    for v in tf.trainable_variables():
        print v.name
        print type(v)
        print v.eval(sess)
    print 
    print var1.eval(sess)
    print var2.eval(sess)
        

