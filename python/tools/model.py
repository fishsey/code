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

###�½����ڱ����������ݼ�ͼƬ���ļ���
os.mkdir('train_set')
os.mkdir('valid_set')
os.mkdir('test_set')
a = load_data('mnist.pkl.gz')
n_train = len(a[0][0])#ѵ����ͼƬ����
n_valid = len(a[1][0])#��֤��ͼƬ����
n_test = len(a[2][0])#���Լ�ͼƬ����

###����ѵ���������ͼƬ
for i in range(n_train):   
    img = a[0][0][i].reshape(28,28)
    #print len(load_data('mnist.pkl.gz')[2][1])
    img = img*255
    cv2.imwrite('train_set\%s.jpg'%i,img)
    
###������֤�������ͼƬ
for i in range(n_valid):   
    img = a[1][0][i].reshape(28,28)
    #print len(load_data('mnist.pkl.gz')[2][1])
    img = img*255
    cv2.imwrite('valid_set\%s.jpg'%i,img)
    
###���ɲ��Լ������ͼƬ
for i in range(n_test):   
    img = a[2][0][i].reshape(28,28)
    #print len(load_data('mnist.pkl.gz')[2][1])
    img = img*255
    cv2.imwrite('test_set\%s.jpg'%i,img)









