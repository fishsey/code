# -*- coding: utf-8 -*-
"""
Created on Fri Dec  9 18:05:15 2016

@author: root
"""

import tensorflow as tf
import numpy as np

np.random.seed(10)
x = np.float32(np.random.rand(100, 2))#shape:(100, 2)
y = np.dot(x, [0.1, 0.2]) + 0.3 #shape:(100,)
alpha = 0.5 #learning rate
iterNums = 1


#launch graph
sess = tf.Session(config=tf.ConfigProto(log_device_placement=False))#output device infos: must execute in python-console(not ipython)


#model: linear regression(y = x*w + b)
b = tf.Variable(tf.zeros([1]))
w = tf.Variable(tf.random_uniform([2, 1], -1.0, 1.0))#uniform distribution:[-1, 1]
predY = tf.matmul(x, w) + b

#loss function: square-loss
loss = tf.reduce_mean(tf.square(y - predY))
optimizer = tf.train.GradientDescentOptimizer(alpha)
train = optimizer.minimize(loss)

#init tf-var
init = tf.global_variables_initializer()
sess.run(init) #execute op(init tf-var)
#train process
for epoch in xrange(0, iterNums):
    sess.run(train)
#output the paras
print sess.run(b)
    