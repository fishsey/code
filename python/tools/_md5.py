# -*- coding: utf-8 -*-
"""
Created on Mon Mar  6 19:15:34 2017

@author: root
"""
import hashlib

def get_md5(url):
    '''md5加密'''
    if isinstance(url, str):
        url = url.encode("utf-8")
    m = hashlib.md5()
    m.update(url)
    return m.hexdigest()
                
                
        
