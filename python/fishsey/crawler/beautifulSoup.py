# -*- coding: utf-8 -*-
"""
Created on Wed Oct 05 16:29:12 2016

@author: fishsey
"""

from bs4 import BeautifulSoup
import urllib, urllib2

html = """
<html><head><title>The Dormouse's story</title></head>
<body>
<p class="title" name="dromouse"><b>The Dormouse's story</b></p>
<p class="story">Once upon a time there were three little sisters; and their names were
<a href="http://example.com/elsie" class="sister" id="link1"><!-- Elsie --></a>,
<a href="http://example.com/lacie" class="sister" id="link2">Lacie</a> and
<a href="http://example.com/tillie" class="sister" id="link3">Tillie</a>;
and they lived at the bottom of a well.</p>
<p class="story">...</p>
"""


url = "https://book.douban.com/tag/?view=type&icn=index-sorttags-all"
request = urllib2.Request(url)
response = urllib2.urlopen(request)
soup = BeautifulSoup(response, "lxml")

for text in soup.strings:
    print text