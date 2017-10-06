# -*- coding: utf-8 -*-
"""
Created on Sun Jul 16 21:24:56 2017

@author: root
"""

import logging

from scrapy import signals
from scrapy.exporters import CsvItemExporter
import time
import os

class CSVExportPipeline(object):
    def __init__(self):
        self.files = {}
        self.count = 0
        self.numsPerFile = 100
        self.fileno = 1
        self.fileName = ''
        
        
    @classmethod
	#If present, this classmethod is called to create a pipeline instance from a Crawler
    def from_crawler(cls, crawler):
        pipeline = cls()
        crawler.signals.connect(pipeline.spider_opened, signals.spider_opened)
        crawler.signals.connect(pipeline.spider_closed, signals.spider_closed)
        return pipeline
        

	#open_spider(self, spider) #当spider被开启时，这个方法被调用
	#close_spider(spider)	#当spider被关闭时，这个方法被调用
    def spider_opened(self, spider):
        logging.info("spider_opened ... ")
        filePath = "/root/AAA/dataset/spider/wangyi/csv/" + spider.name + "/"
        if not os.path.exists(filePath):
            os.makedirs(filePath)
        fileName = filePath +str(self.fileno) + '-' + str(time.time()) + ".csv"
        self.fileName = fileName
        self.fileno += 1
        pf = open(fileName, 'w+b')
        self.files[spider] = pf
        self.exporter = CsvItemExporter(pf, encoding="utf-8", delimiter="\t", include_headers_line=False)
        self.exporter.start_exporting()
        

    def spider_closed(self, spider):
        logging.info("spider_closed .... ")
        self.exporter.finish_exporting()
        pf = self.files.pop(spider)
        pf.close()
        spider.connStatus.send(body=spider.s9, destination=spider.statusPushQueue)
        
        
    def processDataClose(self, spider):
        self.exporter.finish_exporting()
        pf = self.files.pop(spider)
        pf.close()
        
        
    def processDataInit(self, spider):
        filePath = "/root/AAA/dataset/spider/wangyi/csv/" + spider.name + "/"
        fileName = filePath +str(self.fileno) + '-' + str(time.time()) + ".csv"
        self.fileName = fileName
        self.fileno += 1
        pf = open(fileName, 'w+b')
        self.files[spider] = pf
        self.exporter = CsvItemExporter(pf, encoding="utf-8", delimiter="\t", include_headers_line=False)
        self.exporter.start_exporting()
        
        
		
	#每个item pipeline组件都需要调用该方法，
	#返回一个 Item (或任何继承类)对象， 
	#或是抛出 DropItem 异常，被丢弃的item将不会被之后的pipeline组件所处理
    def process_item(self, item, spider):
        logging.info("process_item ........................................... ")
        if self.count % self.numsPerFile == 0 and self.count != 0:
            self.processDataClose(spider)
            self.processDataInit(spider)
            self.count = 0
            message = "3{"+ spider.name + ":" + self.fileName + '}'
            spider.conn.send(body=message, destination=spider.statusPushQueue)
        self.exporter.export_item(item)
        self.count += 1
        return item