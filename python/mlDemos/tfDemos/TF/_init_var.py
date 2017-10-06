# -*- coding: utf-8 -*-
"""
Created on Mon Dec 12 21:24:56 2016

@author: root
"""

import tensorflow as tf
import numpy as np

#create and init var
var1 = tf.Variable(tf.random_normal([10]), name="var1")
var2 = tf.Variable(tf.random_normal([3,3]), name="var2") #can use ndarray

up1 = tf.scatter_update(var1, [4, 3, 1, 7], [9, 10, 11, 12])
up2 = tf.scatter_update(var2, [0, 2], [[1, 11, 111], [2, 22, 222]])

indice1 = tf.IndexedSlices(var1, range(0, 10, 2))
indice2 = tf.IndexedSlices(var2, [0, 2])

#a var-init op
#initOp = tf.global_variables_initializer()
initOp = tf.initialize_all_variables()

#a var-save op
#saveOp = tf.train.Saver()

ss = var1[2:4]

sess = tf.Session()
sess.run(initOp)
print sess.run(up1)
print 
print sess.run(up2)
print 
print sess.run(indice1.values)
print 
print sess.run(indice2.values)
print sess.run(ss)
#    saveFile = saveOp.save(sess, "tf/myModel")
