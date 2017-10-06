# -*- coding: utf-8 -*-

# Define here the models for your spider middleware
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/spider-middleware.html

import logging
logger = logging.getLogger(__name__)
import random
from  scrapy.downloadermiddlewares.useragent import UserAgentMiddleware

			
class RotateUserAgentMiddleware(UserAgentMiddleware):  
  
    def __init__(self, user_agent=''):  
        self.user_agent = user_agent  
  
	#当每个request通过下载中间件时，该方法被调用
	#必须返回其中之一: 返回 None 、返回一个 Response 对象、返回一个 Request 对象或raise IgnoreRequest 
    def process_request(self, request, spider):  
        ua = random.choice(self.user_agent_list)  
        if ua:  
            #显示当前使用的useragent  
            #print "********Current UserAgent:%s************" %ua  
            #记录  
            #log.msg('Current UserAgent: '+ua, level='INFO')  
            request.headers.setdefault('User-Agent', ua)  
			
  
    #the default user_agent_list composes chrome,I E,firefox,Mozilla,opera,netscape  
    #for more user agent strings,you can find it in http://www.useragentstring.com/pages/useragentstring.php  
    user_agent_list = [
		"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 "  
        "(KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1",  
        "Mozilla/5.0 (X11; CrOS i686 2268.111.0) AppleWebKit/536.11 "  
        "(KHTML, like Gecko) Chrome/20.0.1132.57 Safari/536.11",  
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 "  
        "(KHTML, like Gecko) Chrome/20.0.1092.0 Safari/536.6",  
        "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.6 "  
        "(KHTML, like Gecko) Chrome/20.0.1090.0 Safari/536.6",  
        "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.1 "  
        "(KHTML, like Gecko) Chrome/19.77.34.5 Safari/537.1",  
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 "  
        "(KHTML, like Gecko) Chrome/19.0.1084.9 Safari/536.5",  
        "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 "  
        "(KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5",  
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 "  
        "(KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",  
        "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.3 "  
        "(KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",  
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/536.3 "  
        "(KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",  
        "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 "  
        "(KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",  
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 "  
        "(KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",  
        "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 "  
        "(KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",  
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 "  
        "(KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",  
        "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.3 "  
        "(KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",  
        "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 "  
        "(KHTML, like Gecko) Chrome/19.0.1061.0 Safari/536.3",  
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.24 "  
        "(KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24",  
        "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/535.24 "  
        "(KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24"  
       ]     
   
   
class RotateIPMiddleware(object):
    Ips =[
['107.161.80.199', '12393'], ['199.180.133.210', '12393'], 
['45.32.48.182', '12393'], ['139.162.99.150', '12393'], 
['61.195.96.162', '12393'], ['50.2.81.148', '12393'], 
['104.238.223.169', '12393'], ['107.167.5.204', '12393'], 
['133.130.49.42', '12393'], ['192.161.52.161', '12393'],  
    ]
    def getIp(self):
        ip, port = random.choice(self.Ips)
        return ip, port
        
    def process_request(self, request, spider):
        ip, port = self.getIp()
        request.meta["proxy"] =  "http://{0}:{1}".format(ip, port)
        
		
from selenium.common.exceptions import TimeoutException
from scrapy.http import HtmlResponse  #传递js加载后的源代码,不会返回给download
class JSPageMiddleware(object):
    #通过chrome请求动态网页
    def process_request(self, request, spider):
        if spider.name == "JobBole":
            try:
                spider.browser.get(request.url)
            except TimeoutException:
                print('30秒timeout之后，直接结束本页面')
                spider.browser.execute_script('window.stop()')
            import time
            time.sleep(3)
            print("访问:{0}".format(request.url))

            return HtmlResponse(url=spider.browser.current_url, body=spider.browser.page_source, encoding="utf-8", request=request)
            '''编码默认是unicode'''