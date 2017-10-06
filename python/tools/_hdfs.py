# -*- coding: utf-8 -*-
"""
Created on Tue Aug  1 21:39:23 2017

@author: root
"""
from hdfs import *
import time
import os

cl = Client("http://localhost:50070", root="root", timeout=100, session=False)

dirPath = "/root/AAA/dataset/spider/doc/wangyi/csv/wangyiNews_0/"
hdfsPath = "/user/root/dataset/wangyi/spark"


for _file in os.listdir(dirPath):
    sourceFile = os.path.join(dirPath, _file)
    print sourceFile
    cl.upload(hdfsPath, sourceFile)
    time.sleep(1)