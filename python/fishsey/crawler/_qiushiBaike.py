# -*- coding: utf-8 -*-
"""
Created on Wed Oct 05 16:09:38 2016

@author: fishsey
"""

import urllib
import urllib2
import sys
from bs4 import BeautifulSoup
import urlparse
import re
from PIL import Image
import matplotlib.pyplot as plt # plt 用于显示图片
import matplotlib.image as mpimg # mpimg 用于读取图片
import numpy as np
from scipy import misc 

#from __future__ import print_function2

user_agent = 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36'
headers = { 'User-Agent' : user_agent }

try:
    for number in range(1, 2):
        url = "http://www.qiushibaike.com/hot/page/%d/?s=4983282" % number
        request = urllib2.Request(url, headers=headers)
        response = urllib2.urlopen(request, timeout=10)
        soup = BeautifulSoup(response, 'lxml')
        
        textResults = soup.find_all(href=re.compile("^/article/"), class_="contentHerf")
        imgResults = soup.select("div.article .thumb ")
        for index, line in enumerate(imgResults):
            print index, line.img['src']
            if index > 5:
                break
            img =  urllib2.urlopen(urlparse.urljoin("http:", line.img['src'])).read()
            with open("temp/" + str(number) + "-" + str(index) + ".jpg", 'wb') as f:
                f.write(img)
        for index, line in enumerate(textResults):
            print index, line.text
            if index > 5:
                break
            #显示图片
#            input = raw_input()
#            if input.upper() == "Q": 
#                sys.exit()
#            content = line.find('div', class_="content").span.text
#            num = line.find_all('i', class_="number")
#            laughNum = int(num[0].text)
#            commentNum = int(num[1].text)
#            print content
##            print laughNum, commentNum
#            print 
except urllib2.URLError, e:
    if hasattr(e, "code"):
        print e.code
    if hasattr(e, "reason"):
        print e.reason
except SystemExit, e:
    print '...exit'
        
