# -*- coding: utf-8 -*-
"""
Created on Thu Dec 15 20:35:07 2016

@author: root
"""

import tensorflow as tf
import numpy as np

features = 20
np.random.seed(10)
x = np.float32(np.random.rand(100, features))#shape:(100, features)
y = np.float32(np.random.rand(100)) #shape:(100,)
alpha = 0.1 #learning rate
iterNums = 30


#launch graph
#output device infos: must execute in python-console(not ipython)
sess = tf.Session(config=tf.ConfigProto(log_device_placement=False))

with tf.name_scope('hidden1'):
    #model: linear regression(y = x*w + b)
    b = tf.Variable(tf.zeros([1]))
    w = tf.Variable(tf.random_uniform([features, 1], 0, 1.0))#uniform distribution:[-1, 1]
    predY = tf.matmul(x, w) + b


#loss function: square-loss
loss = tf.reduce_mean(tf.square(y - predY))
optimizer = tf.train.GradientDescentOptimizer(alpha)
train = optimizer.minimize(loss)


#summary
wHist = tf.summary.histogram("weights", w)
bScalar = tf.summary.histogram("biases", b)
lossHist = tf.summary.histogram("loss", loss)
merged = tf.summary.merge_all()
writer = tf.summary.FileWriter("dataset/tf/mnistLogs", sess.graph)



#init tf-var
init = tf.global_variables_initializer()
sess.run(init) #execute op(init tf-var)
#train process
for epoch in xrange(0, iterNums):
    sess.run(train)
    summaryStr = sess.run(merged)
    writer.add_summary(summaryStr, epoch)

writer.close()

#output the paras
print sess.run(b)
print sess.run(w)
print sess.run(loss)
sess.close()
