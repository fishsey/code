# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy
    
def replace(text):
    text = text.replace('\t', '_$t$_')
    text = text.replace('\n', '_$line$_')
    text = text.replace('\r\n', '_$line$_')
    text = text.replace('\r', '_$line$_')
    return text
    

class Wangyinews(scrapy.Item):
    url = scrapy.Field(serializer=replace)
    title = scrapy.Field(serializer=replace)
    text = scrapy.Field(serializer=replace)
    time = scrapy.Field(serializer=replace)
    source = scrapy.Field(serializer=replace)
	
