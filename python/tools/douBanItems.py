# -*- coding: utf-8 -*-
"""
Created on Sun Jul 16 21:54:44 2017

@author: root
"""
import scrapy

def listTostr(lst):
    return '\n'.join([elem.strip() for elem in lst])    

def listTostr2(lst):
    return ' '.join([elem.strip() for elem in lst]) 


class douBanPage(scrapy.Item):
    Id = scrapy.Field()
    url = scrapy.Field()
    title = scrapy.Field()
    author = scrapy.Field()
    
    picUrl = scrapy.Field()#list
    picPath = scrapy.Field()#on the disk
    
    
    tags = scrapy.Field(serializer=listTostr2)
    recysList = scrapy.Field(serializer=listTostr2)
	
    authorIntro = scrapy.Field(serializer=listTostr)
    contentIntro = scrapy.Field(serializer=listTostr)
    catalog = scrapy.Field(serializer=listTostr)