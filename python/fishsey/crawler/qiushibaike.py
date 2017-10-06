# -*- coding: utf-8 -*-
"""
Created on Wed Oct 05 16:09:38 2016

@author: fishsey
"""

import urllib
import urllib2
import sys
from bs4 import BeautifulSoup

user_agent = 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36'
headers = { 'User-Agent' : user_agent }

try:
    for number in range(1, 35):
        url = "http://www.qiushibaike.com/hot/page/%d/?s=4918850" % number
        request = urllib2.Request(url, headers=headers)
        response = urllib2.urlopen(request)
        soup = BeautifulSoup(response, "lxml")
        result = soup.find_all(class_="article block untagged mb15")
        for line in result:
            input = raw_input()
            if input.upper() == "Q": 
                sys.exit()
            content = line.find('div', class_="content").span.text
            num = line.find_all('i', class_="number")
            laughNum = int(num[0].text)
            commentNum = int(num[1].text)
            print content
#            print laughNum, commentNum
            print 
except urllib2.URLError, e:
    if hasattr(e, "code"):
        print e.code
    if hasattr(e, "reason"):
        print e.reason
except SystemExit, e:
    print '...exit'
        
