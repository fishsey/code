# -*- coding: utf-8 -*-
"""
Created on Mon Dec 19 19:51:46 2016
@author: root
文件读取
"""
import tensorflow as tf
import os

def readSample():
    '''
    文本文件
    单个Reader，单个样本
    '''
    #生成一个先入先出队列(但每次所有文件进入队列的顺序不固定)和一个QueueRunner
    filename_queue = tf.train.string_input_producer(allFilesList, shuffle=False)
    #定义Reader
    #key:对应下一次读取对应的位置（b'D:/baiduYun/syn/Github/dataset/tf/readerTest/1:101'）
    #value:对应每一行的内容
    reader = tf.TextLineReader()
    key, value = reader.read(filename_queue)
    #定义Decoder
    #解析每一行的内容，Python3.0里所有的字符串都是Unicode(默认UTF-8格式)编码
    #得到 byte 需要解码 bs.decode('gb2312')/bs.decode()
    col1, col2, col3 = tf.decode_csv(value, record_defaults=[['0'], ['0'], ['0']], field_delim=' ')
    #运行Graph
    sess = tf.Session()
    coord = tf.train.Coordinator()  #创建一个协调器，管理线程
    #启动QueueRunner, 此时文件名队列已经进队。
    threads = tf.train.start_queue_runners(sess, coord=coord)  
    #取样本的时候，一个Reader先从文件名队列中取出文件名，读出数据，Decoder解析后进入样本队列。
    #文件按照入队顺序一次被完整读取
    for i in range(2):
        v1, v2, v3 = sess.run([col1, col2, col3])
        print (v1, v2, v3)   
    coord.request_stop()
    coord.join(threads)

def readSampleBatch():
    '''
    文本文件
    单个Reader，多个样本
    '''
    filename_queue = tf.train.string_input_producer(allFilesList, shuffle=False)
    reader = tf.TextLineReader()
    key, value = reader.read(filename_queue)
    col1, col2, col3 = tf.decode_csv(value, record_defaults=[['0'], ['0'], ['0']], field_delim=' ')
    
    #使用tf.train.batch()会多加了一个样本队列和一个QueueRunner。Decoder解后数据会进入这个队列，再批量出队。
    #虽然这里只有一个Reader，但可以设置多线程，相应增加线程数会提高读取速度，但并不是线程越多越好。
    col1_batch, col2_batch, col3_batch = tf.train.batch([ col1, col2, col3], batch_size=5)
    
    sess = tf.Session()
    coord = tf.train.Coordinator()  
    threads = tf.train.start_queue_runners(sess, coord=coord)  
    for i in range(2):
        v1, v2, v3 = sess.run([col1_batch, col2_batch, col3_batch])
        print (v1) 
        print (v2)   
        print (v3)   
        print()
    coord.request_stop()
    coord.join(threads)
    
    
def readSampleBatchMulReader():
    '''
    文本文件
    多Reader，多个样本
    '''
    filename_queue = tf.train.string_input_producer(allFilesList, shuffle=False)
    reader = tf.TextLineReader()
    key, value = reader.read(filename_queue)
    sampelList = [tf.decode_csv(value, record_defaults=[['0'], ['0'], ['0']], field_delim=' ') for _ in range(2)]#Reader设置为2
    
    #使用tf.train.batch_join()，可以使用多个reader，并行读取数据。
    #每个Reader使用一个线程。
    c1_batch, c2_batch, c3_batch = tf.train.shuffle_batch_join(sampelList, batch_size=5, capacity=40, min_after_dequeue=20)
#    c1_batch, c2_batch, c3_batch = tf.train.batch_join(sampelList, batch_size=5)
    
    #迭代控制
    iterNum = 30
    init_local_op = tf.local_variables_initializer()
    with tf.Session() as sess:
        sess.run(init_local_op)   # 初始化本地变量 
        coord = tf.train.Coordinator()
        threads = tf.train.start_queue_runners(coord=coord)
        try:
            while not coord.should_stop():
                for i in range(iterNum):
                    v1, v2, v3 = sess.run([ c1_batch, c2_batch, c3_batch])
                    if i % 10 == 0:
                        print (v1) 
                        print (v2)   
                        print (v3)   
                        print("----------")
                    if i == iterNum-1:
                        coord.request_stop()
        except tf.errors.OutOfRangeError:
            print('Epochs Complete!')
        finally:
            coord.request_stop()
        coord.join(threads)
        coord.request_stop()
        coord.join(threads)
        
def readPicture():
    with tf.Session() as sess:
#        picPath = "temp/pic/1.gif"
        picPath = "temp/pic/2.png"
        imagecontent = tf.read_file(picPath)
#        image = tf.image.decode_gif(imagecontent) 
        image = tf.image.decode_png(imagecontent, channels=3, dtype=tf.uint8) 
        image = tf.cast(image, tf.float32)  
        print(sess.run(image).shape)
        #这里是对像素值归到128的均值，即对每个channel分别除以均值乘以128  
        reShape = tf.reshape(tf.reduce_mean(image, [0, 1]), [1, 1, 3]) 
        image = image / reShape * 128
        image = tf.random_crop(image, [28, 28, 3]) 
        
#        images, labels_batch = tf.train.shuffle_batch([image, labels], batch_size=batch_size, num_threads=6, capacity=3 * batch_size+3000, min_after_dequeue=3000) 
        
        image = sess.run(image)
        print(image.shape)
    

if __name__ == "__main__":
    fileDir = "D:/baiduYun/syn/Github/dataset/tf/readerTest" #the dataset dir
    allFilesList = os.listdir(fileDir)
    allFilesList = [os.path.join(fileDir,file) for file in allFilesList]
    #allFilesList = ["D:/baiduYun/syn/Github/dataset/tf/readerTest/0"]
#    readSample()
#    print("----------------------------------------------------------")
#    readSampleBatch()
#    print("----------------------------------------------------------")
    readPicture()
    

#
#
#
#
#
#
#
#
