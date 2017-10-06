# -*- coding: utf-8 -*-
"""
Created on Tue Jan  3 14:05:44 2017

@author: root
"""

import urllib
import urllib2

url = 'http://192.168.2.253/ac_portal/login.php'
data = {'opr':'pwdLogin', 'userName':'151050064', 'pwd':'0064', 'rememberPwd':'0'}   

urllib2.urlopen(url, urllib.urlencode(data))

