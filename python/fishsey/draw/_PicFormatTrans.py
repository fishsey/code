# -*- coding: utf-8 -*-
"""
Created on Wed May 17 23:19:11 2017

@author: root
"""

from PIL import Image
import os
import os.path
import threadpool
import time


def transAndSave(imgPath):
    global processNum
    im = Image.open(os.path.join(imgDir,imgPath))
    im.save(os.path.join(imgToDir,imgPath.split(".")[0] + toFormat))
    processNum += 1
    print processNum
    
start = time.time()
fromFormat = ".jpg"
toFormat = ".png"
imgDir = "/root/AAA/dataset/zong.backup" #the source dir
imgToDir = "/root/AAA/dataset/png"  #the target dir
threadNum = 80  
processNum = 0 #the img number current process

if not os.path.exists(imgToDir):
    os.makedirs(imgToDir)
allFilesList = os.listdir(imgDir)
imgPaths = [imgPath for imgPath in allFilesList if imgPath.endswith(fromFormat)]

pool = threadpool.ThreadPool(threadNum)
requests = threadpool.makeRequests(transAndSave, imgPaths)
[pool.putRequest(req) for req in requests]
pool.wait()

end = time.time()
print "during time: ", end - start , 's'
        



    
