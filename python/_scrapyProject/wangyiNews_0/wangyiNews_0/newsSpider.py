# -*- coding: utf-8 -*-
"""
Created on Sun Jul 16 17:34:54 2017

@author: fishsey
"""
import scrapy
from items import Wangyinews
import logging
import urlparse
from Queue import Queue
import os
import stomp
import re
import time

class urlListener(object):
    def __init__(self, queue):
        self.queue = queue
    def on_message(self, headers, message):
        print 'received a url:....................... ',  message
        self.queue.put(str(message))


class statusListener(object):
    def __init__(self, status):
        self.status = status
    def on_message(self, headers, message):
        print 'received a status ....................... ', message
        if message == '1':
            self.status[0] = 1
            
    

class wangyiSpider(scrapy.Spider):
    
    name = os.path.abspath(__file__).split('/')[-2]
    
    queue = Queue()
    start_urls = ['http://www.163.com/', 
	'http://news.163.com/', 'http://news.163.com/latest/', 'http://news.163.com/special/0001386F/rank_whole.html',
	'http://sports.163.com/', 
	'http://tech.163.com/', 
	'http://auto.163.com/',
	'http://zj.news.163.com/', 
	'http://gongyi.163.com/',
	'http://ent.163.com/', 
	'http://money.163.com/', 
	'http://fashion.163.com/',
	'http://hz.house.163.com/',
	'http://jiankang.163.com/',
	'http://travel.163.com/',
	'http://art.163.com/']
	
    startShellPath = "/root/AAA/code/shell/wangyi/" + name + ".sh"
	
    urlPushQueue = '/queue/wangyi.url.master'
    statusPushQueue = '/queue/wangyi.status.master'
    
    urlPullQueue = '/queue/wangyi.url.slave.' + name
    statusPullQueue = '/queue/wangyi.status.slave.' + name
    
    urlPullQueueName = 'wangyi.url.slave.' + name
    statusPullQueueName = 'wangyi.status.slave.' + name
    
    status = [0]
	#message = "3{"+ spider.name + ":" + self.fileName + '}'
    s9 = "9{name:" + name + "}"
    s0 = "0{" + name + ":" +  startShellPath + ":" + urlPullQueueName + ":" + statusPullQueueName + "}";
    s2 = "2{name:" + name + "}"
   
    #allowed_domains = ['news.163.com', 'home.163.com']
    
    def getUrl(self, timeout=1):
        TIMEOUT = 60
        if int(time.time()) - self.time > 60*5:
                self.initUrls()
                self.time = int(time.time())
                
        if not self.queue.empty():
            return self.queue.get()
        else:
           logging.info("pass a message to control ...........................")
           self.connStatus.send(body=self.s2, destination=self.statusPushQueue)
           try:
               return self.queue.get(timeout=timeout)
           except:
               logging.info(timeout)
               if timeout > TIMEOUT:
                   return None
               self.getUrl(timeout*2)
       

    def initConn(self):
        connUrl = stomp.Connection10([('10.65.1.62',61613)])  
        connUrl.set_listener('', urlListener(self.queue))
        connUrl.start()
        connUrl.connect()
        connUrl.subscribe(destination=self.urlPullQueue, ack='auto')
        
        connStatus = stomp.Connection10([('10.65.1.62',61613)]) 
        connStatus.set_listener('', statusListener(self.status))
        connStatus.start()
        connStatus.connect()
        connStatus.subscribe(destination=self.statusPullQueue, ack='auto')
        
        return connUrl, connStatus
        
        
    def initMessageQueue(self):
        logging.info("initMessageQueue .....................................")
        delay = 3
        while self.status[0] != 1:
            self.connStatus.send(body=self.s0, destination=self.statusPushQueue)
            logging.info("waiting for ack .....................................")
            time.sleep(delay)
            
    def initUrls(self):
        from bs4 import BeautifulSoup as bf
        import urllib2
        logging.info("initUrls .....................................")
        
        allUrls = []
        for initUrl in self.start_urls:
            print initUrl
            soup = bf(urllib2.urlopen(initUrl, timeout=60), "lxml")
            la = soup.find_all('a')
            for l in la:
                try:
                    allUrls.append(l['href'])
                except Exception:
                    continue
            
        result = []
        for url in allUrls:
            if self.pattern.match(url):
                result.append(url)
        for url in result:
            self.connUrl.send(body=url, destination=self.urlPushQueue)
            
        
    
            
        
    def __init__(self, **kwargs):
        self.connUrl, self.connStatus = self.initConn()
        self.pattern = re.compile(r'^http://[a-zA-Z]+?.163.com/[0-9]{2,2}/[0-9]{4,4}/[0-9]{2,2}/[0-9a-zA-Z]+.html$')
        self.initMessageQueue()
        self.initUrls()
        self.time = int(time.time())
        
        
     
    def getAllLinks(self, response):
        allUrls = response.xpath("//a/@href").extract()
        result = []
        for url in allUrls:
            if self.pattern.match(url):
                result.append(url)
        for url in result:
            self.connUrl.send(body=url, destination=self.urlPushQueue)
            
        
    def start_requests(self):
        logging.info("start_requests ... ............................")
        url = self.getUrl()
        while url is not None:
            logging.info("start_requests ... ............................" + url)
            yield scrapy.Request(url, callback=self.parse, dont_filter=False)
            url = self.getUrl()
        
        
        
    def parse(self, response):
        self.getAllLinks(response)
        logging.info("parse ... ............................" + response.url)
        item = Wangyinews()
        
        
        item['url'] = response.url
        
        
        title = response.xpath('//*[@id="epContentLeft"]/h1/text()').extract()
        if len(title) > 0:
            item['title'] = title[0]
        else:
            item['title'] = ''
            
            
            
        item['text'] = ''.join(t.strip() for t in response.xpath('//*[@id="endText"]/p/text()').extract())
		
  
  
        time = response.xpath('//*[@id="epContentLeft"]/div[@class="post_time_source"]/text()').extract()
        time = ''.join(t.strip() for t in time)
        ts = time.split()
        if len(ts) > 1:
            item['time'] = ts[0] + ts[1]
        else:
            item['time'] = ''
        
        
        source = response.xpath('//*[@id="ne_article_source"]/@href').extract()
        if len(source) > 0:
            item['source'] = source[0]
        else:
            item['source'] = ''
        
        yield item
            

        
		
		
        
        
    
   
            
    
    
    
    
        
