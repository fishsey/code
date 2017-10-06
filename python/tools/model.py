# -*- coding: cp936 -*-
import cPickle as pickle
import gzip,cv2,os
import numpy as np
def load_data(dataset):
    #print '....loading data'
    f = gzip.open(dataset,'rb')
    train_set,valis_set,test_set = pickle.load(f)
    f.close()
    return(train_set,valis_set,test_set)

###新建用于保存三个数据集图片的文件夹
os.mkdir('train_set')
os.mkdir('valid_set')
os.mkdir('test_set')
a = load_data('mnist.pkl.gz')
n_train = len(a[0][0])#训练集图片数量
n_valid = len(a[1][0])#验证集图片数量
n_test = len(a[2][0])#测试集图片数量

###生成训练集里面的图片
for i in range(n_train):   
    img = a[0][0][i].reshape(28,28)
    #print len(load_data('mnist.pkl.gz')[2][1])
    img = img*255
    cv2.imwrite('train_set\%s.jpg'%i,img)
    
###生成验证集里面的图片
for i in range(n_valid):   
    img = a[1][0][i].reshape(28,28)
    #print len(load_data('mnist.pkl.gz')[2][1])
    img = img*255
    cv2.imwrite('valid_set\%s.jpg'%i,img)
    
###生成测试集里面的图片
for i in range(n_test):   
    img = a[2][0][i].reshape(28,28)
    #print len(load_data('mnist.pkl.gz')[2][1])
    img = img*255
    cv2.imwrite('test_set\%s.jpg'%i,img)









