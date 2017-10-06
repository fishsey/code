# -*- coding: utf-8 -*-
"""
Created on Fri Jan  6 20:30:07 2017

@author: root
"""

#==============================================================================
#create some threads manipulate  queues
#==============================================================================

import tensorflow as tf
import numpy as np
seed = 10
batchSize = 1
threadNums = 10

#get a random sample with shape(3, )
sample = tf.random_normal([5], seed=seed, dtype=tf.float32)

queue = tf.RandomShuffleQueue(capacity=30, min_after_dequeue=1, dtypes=[tf.float32]*3)
#queue = tf.FIFOQueue(100, [tf.float32] * 2)

initQueue = queue.enqueue_many([sample * i for i in range(1, 11)])
x = queue.dequeue()
dequeueOp = queue.dequeue(2)#out, each batchSize sample


#create a queueRunner with some threads enqueue
#enqueueRunners = tf.train.QueueRunner(queue, enqueue_ops=[enqueueOp]*threadNums)


#begin session
sess = tf.Session()

#coord = tf.train.Coordinator()
#threads = tf.train.start_queue_runners(sess=sess, coord=coord) 
#enqueueRunnersThreads = enqueueRunners.create_threads(sess, coord, start=True)
sess.run(initQueue)
print sess.run(x)
print sess.run(dequeueOpl)

#coord.request_stop()
#coord.join(threads)