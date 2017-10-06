# -*- coding: utf-8 -*-
"""
Created on Wed Oct 05 16:29:12 2016

@author: fishsey
"""

from bs4 import BeautifulSoup

import urllib
import urllib2
import re
import unittest
from  _timer import timerFunc
import time
import copy

class BaiduTieba:
    #初始化，传入基地址，是否只看楼主的参数
    def __init__(self,baseUrl, seeLZ):
        self.baseURL = baseUrl
        self.seeLZ = '?see_lz=' + str(seeLZ)

    #传入页码，获取该页帖子的代码
    def getPage(self, pageNum):
        try:
            url = self.baseURL+ self.seeLZ + '&pn=' + str(pageNum)
            request = urllib2.Request(url)
            response = urllib2.urlopen(request)
            soup = BeautifulSoup(response, 'lxml')
            return soup
        except urllib2.URLError, e:
            if hasattr(e,"reason"):
                print u"连接百度贴吧失败,错误原因",e.reason
                return None
     #获取帖子标题
    def getTitle(self, soup):
        titleTag = soup.select("div.left_section .core_title_wrap_bright .core_title_txt")
        return titleTag[0].text
        
     #获取帖子标题
    def getPageNums(self, soup):
        pageNumsTag = soup.select("ul.l_posts_num .l_reply_num .red")
        return pageNumsTag[1].text
 

@timerFunc
def testParse(bdtb, soup):
    print "bs4"
    for _ in range(int(1)):
        title = bdtb.getPageNums(soup)
    print title
        


if __name__ == "__main__":
    baseURL = 'http://tieba.baidu.com/p/3138733512'
    bdtb = BaiduTieba(baseURL, 1)
    soup = bdtb.getPage(1) 
    testParse(bdtb, soup)
