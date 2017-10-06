# -*- coding: utf-8 -*-
import os

spidername = os.path.abspath(__file__).split('/')[-2]

BOT_NAME = spidername 
SPIDER_MODULES = [spidername] 
NEWSPIDER_MODULE = spidername 


##----------------   scrapy.log
##---------------- 
#LOG_ENABLED	#默认: True
#LOG_ENCODING	#默认: 'utf-8'
#LOG_FILE		#默认: None（标准错误输出(standard error)）
LOG_LEVEL='INFO'	#CRITICAL、 ERROR、WARNING、INFO、DEBUG
#LOG_STDOUT		#默认: False， 如果为 True ，进程所有的标准输出(及错误)将会被重定向到log中


##---------------- robots.txt rules
##---------------- 
ROBOTSTXT_OBEY = False	#如果启用，Scrapy将会尊重 robots.txt策略。


##---------------- user-agent
##----------------
#USER_AGENT = '_scrapyProject (+http://www.yourdomain.com)'


##----------  request headers:
##----------------
#DEFAULT_REQUEST_HEADERS = {
#   'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
#   'Accept-Language': 'zh-CN,zh;q=0.8,zh-HK;q=0.6',
#}  #Scrapy HTTP Request使用的默认header。由 DefaultHeadersMiddleware 产生


##----------------    cookies (enabled by default)
##----------------
COOKIES_ENABLED = False #默认 true
#COOKIES_DEBUG 	#默认: False, 如果启用，Scrapy将记录所有在request(Cookie 请求头)发送的cookies及response接收到的cookies(Set-Cookie 接收头)


##----------------   HTTP caching (disabled by default)
##----------------
#HTTPCACHE_ENABLED = True	#默认: False
#HTTPCACHE_EXPIRATION_SECS = 0	#默认: 0（不超时）, 缓存的request的超时时间，单位秒，超过这个时间的缓存request将会被重新下载。
#HTTPCACHE_DIR = 'httpcache'	#默认: 'httpcache',   存储(底层的)HTTP缓存的目录
#HTTPCACHE_IGNORE_HTTP_CODES = [] 	#默认: [], 不缓存设置中的HTTP返回值(code)的request
#HTTPCACHE_IGNORE_MISSING		#默认: False, 如果启用，在缓存中没找到的request将会被忽略，不下载
#HTTPCACHE_IGNORE_SCHEMES	#默认: ['file'], 不缓存这些URI标准(scheme)的response
#HTTPCACHE_STORAGE = 'scrapy.extensions.httpcache.FilesystemCacheStorage'	#实现缓存存储后端的类。
#HTTPCACHE_POLICY  	#缓存策略： scrapy.contrib.httpcache.DummyPolicy、scrapy.contrib.httpcache.RFC2616Policy



##----------------  图片设置
##----------------
#IMAGES_URLS_FIELD = "picUrl"
#IMAGES_STORE = "/root/AAA/dataset/douban/pic/" + spidername + "/"
#IMAGES_EXPIRES = 90 #90天的图片失效期限
#自动创建下载图片的缩略图
# IMAGES_THUMBS = {
    # 'small': (120, 120),
    # 'big': (240, 240),
# } 
#IMAGES_MIN_HEIGHT = 110	#丢掉那些过小的图片
#IMAGES_MIN_WIDTH = 110


##----------------  item，pipeline
##----------------
#DEFAULT_ITEM_CLASS  #默认: 'scrapy.item.Item'， the Scrapy shell 中实例化item使用的默认类
ITEM_PIPELINES = {
    spidername + '.pipelines.CSVExportPipeline': 2,
    #spidername + '.pipelines.doubanImagePipeline': 1,
}	#默认: {}， 保存项目中启用的pipeline及其顺序的字典
#ITEM_PIPELINES_BASE
# MYSQL_HOST = "10.65.1.62"
# MYSQL_DBNAME = "bookrecys"
# MYSQL_USER = "fishsey"
# MYSQL_PASSWORD = "0114"



##----------------  深度优先
##----------------
# = 0 #默认: 0，爬取网站最大允许的深度(depth)值。如果为0，则没有限制
#DEPTH_PRIORITY  #默认: 0， 整数值。用于根据深度调整request优先级。
				 #如果为0，则不根据深度进行优先级调整。
#DEPTH_STATS 	#默认: True， 是否收集最大深度数据
#DEPTH_STATS_VERBOSE	#默认: False， 是否收集详细的深度数据。如果启用，每个深度的请求数将会被收集在数据中。


##----------------  下载器
##----------------
#DNSCACHE_ENABLED	#默认: True, 是否启用DNS内存缓存(DNS in-memory cache)。
#DOWNLOADER		#默认: 'scrapy.core.downloader.Downloader'， 用于crawl的downloader.
#DOWNLOADER_STATS  #默认: True， 是否收集下载器数据
#DOWNLOAD_HANDLERS		#默认: {}，保存项目中启用的下载处理器(request downloader handler)的字典
#DOWNLOAD_HANDLERS_BASE 	#保存项目中默认启用的下载处理器(request downloader handler)的字典。 
							#永远不要在项目中修改该设定，而是修改 DOWNLOADER_HANDLERS 。
							#如果需要关闭上面的下载处理器，您必须在项目中的 DOWNLOAD_HANDLERS 设定中设置该处理器，并为其赋值为 None
DOWNLOAD_TIMEOUT = 10	#默认: 180s, 下载器超时时间(单位: 秒)
					#该超时值可以使用 download_timeout 来对每个spider进行设置(使用 meta)
					#也可以使用 download_timeout Request.meta key 来对每个请求进行设置.
#DOWNLOAD_MAXSIZE  	#Default: 1073741824 (1024MB), The maximum response size (in bytes) that downloader will download.
#DOWNLOAD_WARNSIZE	#Default: 33554432 (32Mb)， The response size (in bytes) that downloader will start to warn.



##----------------  重定向
##----------------
#REDIRECT_MAX_TIMES		#默认: 20，定义request允许重定向的最大次数。超过该限制后该request直接返回获取到的结果。 对某些任务我们使用Firefox默认值。
#REDIRECT_MAX_METAREFRESH_DELAY		#默认: 100, 有些网站使用 meta-refresh 重定向到session超时页面， 因此我们限制自动重定向到最大延迟(秒)。
#REDIRECT_PRIORITY_ADJUST	#默认: +2, 修改重定向请求相对于原始请求的优先级。 正数意味着更多优先级


##----------------  并发配置
CONCURRENT_REQUESTS = 16  #default: 16, Scrapy downloader 并发请求(concurrent requests)的最大值
#CONCURRENT_REQUESTS_PER_DOMAIN = 16 #默认8，对单个网站进行并发请求的最大值
#CONCURRENT_REQUESTS_PER_IP = 16 #默认 0，对单个IP进行并发请求的最大值
								 #如果非0，则忽略 CONCURRENT_REQUESTS_PER_DOMAIN 设定， 使用该设定。
								 #该设定也影响 DOWNLOAD_DELAY: 如果非0，下载延迟应用在IP而不是网站上
#CONCURRENT_ITEMS  #默认100，Item Processor(即 Item Pipeline) 同时处理(每个response的)item的最大值
								 

##---------------- 下载延迟
DOWNLOAD_DELAY = 1 #default: 0s,下载器在下载同一个网站下一个页面前需要等待的时间
					#当 CONCURRENT_REQUESTS_PER_IP 非0时，延迟针对的是每个ip而不是网站
					#可以通过spider的 download_delay 属性为每个spider设置该设定
RANDOMIZE_DOWNLOAD_DELAY=True #默认true, 若 DOWNLOAD_DELAY 为0(默认值)，该选项将不起作用
							   # 0.5-1.5 * DOWNLOAD_DELAY
							   

##---------- AutoThrottle(自动限速)
#AUTOTHROTTLE_ENABLED = True  #false，启用AutoThrottle扩展
#AUTOTHROTTLE_START_DELAY = 5  #5，初始下载延迟(单位:秒)
#AUTOTHROTTLE_MAX_DELAY = 60 #60，在高延迟情况下最大的下载延迟(单位秒)
#AUTOTHROTTLE_TARGET_CONCURRENCY = 1.0 #The average number of requests Scrapy 
									   #should be sending in parallel to
									   #each remote server
#AUTOTHROTTLE_DEBUG = False #flase，起用AutoThrottle调试(debug)模式，展示每个接收到的response。 
							#您可以通过此来查看限速参数是如何实时被调整的。


							
##----------------  内存调试(memory debugging)
##----------------
#MEMDEBUG_ENABLED	#默认: False， 是否启用内存调试(memory debugging)
#MEMDEBUG_NOTIFY	#默认: []，如果该设置不为空，当启用内存调试时将会发送一份内存报告到指定的地址；否则该报告将写到log中。
					#MEMDEBUG_NOTIFY = ['user@example.com']
#MEMUSAGE_ENABLED	#默认: False，是否启用内存使用插件。当Scrapy进程占用的内存超出限制时，该插件将会关闭Scrapy进程， 同时发送email进行通知
#MEMUSAGE_LIMIT_MB	#默认: 0，在关闭Scrapy之前所允许的最大内存数(单位: MB)(如果 MEMUSAGE_ENABLED为True)。 
					#如果为0，将不做限制。
#MEMUSAGE_NOTIFY_MAIL	#默认: False，达到内存限制时通知的email列表
						#MEMUSAGE_NOTIFY_MAIL = ['user@example.com']
#MEMUSAGE_REPORT	#默认: False，每个spider被关闭时是否发送内存使用报告。
#MEMUSAGE_WARNING_MB	#默认: 0，在发送警告email前所允许的最大内存数(单位: MB)(如果 MEMUSAGE_ENABLED为True)。 
						#如果为0，将不发送警告。

											
##----------------  其他设置
##----------------
#DUPEFILTER_CLASS = None	#用于检测过滤重复请求的类。 #默认的 (RFPDupeFilter) 过滤器基于 scrapy.utils.request.request_fingerprint 函数生成的请求fingerprint(指纹)
#DUPEFILTER_DEBUG	#默认: False， 默认情况下， RFPDupeFilter 只记录第一次重复的请求。 
					#设置 DUPEFILTER_DEBUG 为 True 将会使其记录所有重复的requests。
#EDITOR		#默认: depends on the environment
			#执行 edit 命令编辑spider时使用的编辑器
#SCHEDULER	#默认: 'scrapy.core.scheduler.Scheduler'，用于爬取的调度器。


##---------- spider 
##---------- 
#SPIDER_CONTRACTS
#SPIDER_CONTRACTS_BASE
#SPIDER_MANAGER_CLASS



##---------- stats 
##---------- 		
#STATS_CLASS
#STATS_DUMP
#STATSMAILER_RCPTS
#TEMPLATES_DIR	#默认: scrapy模块内部的 templates, 使用 startproject 命令创建项目时查找模板的目录。
#URLLENGTH_LIMIT	#默认: 2083, 爬取URL的最大长度
	
		
##---------- spider middlewares
##---------- 
#SPIDER_MIDDLEWARES = {
#   '_scrapyProject.middlewares._downloadFake.RotateUserAgentMiddleware': 400,
#}  #保存项目中启用的下载中间件及其顺序的字典
	#SPIDER_MIDDLEWARES 设置会与Scrapy定义的 SPIDER_MIDDLEWARES_BASE 设置合并(但不是覆盖)
	
#SPIDER_MIDDLEWARES_BASE
# {
    # 'scrapy.contrib.spidermiddleware.httperror.HttpErrorMiddleware': 50,
    # 'scrapy.contrib.spidermiddleware.offsite.OffsiteMiddleware': 500,
    # 'scrapy.contrib.spidermiddleware.referer.RefererMiddleware': 700,
    # 'scrapy.contrib.spidermiddleware.urllength.UrlLengthMiddleware': 800,
    # 'scrapy.contrib.spidermiddleware.depth.DepthMiddleware': 900,
# }


##---------- downloader middlewares
##---------- 
#RANDOM_UA_TYPE = 'random'
DOWNLOADER_MIDDLEWARES = {
	 spidername +'.middlewares.RotateUserAgentMiddleware': 501,
	 #spidername +'.middlewares.RotateIPMiddleware': 502,
	 'scrapy.downloadermiddlewares.useragent.UserAgentMiddleware': None, #这里要设置原来的scrapy的useragent为None，否者会被覆盖掉
} 		#默认:: {}， 保存项目中启用的下载中间件及其顺序的字典
		#DOWNLOADER_MIDDLEWARES 设置会与Scrapy定义的 DOWNLOADER_MIDDLEWARES_BASE 设置合并(但不是覆盖)
		
#DOWNLOADER_MIDDLEWARES_BASE 
# {
    # 'scrapy.contrib.downloadermiddleware.robotstxt.RobotsTxtMiddleware': 100,
    # 'scrapy.contrib.downloadermiddleware.httpauth.HttpAuthMiddleware': 300,
    # 'scrapy.contrib.downloadermiddleware.downloadtimeout.DownloadTimeoutMiddleware': 350,
    # 'scrapy.contrib.downloadermiddleware.useragent.UserAgentMiddleware': 400,
    # 'scrapy.contrib.downloadermiddleware.retry.RetryMiddleware': 500,
    # 'scrapy.contrib.downloadermiddleware.defaultheaders.DefaultHeadersMiddleware': 550,
    # 'scrapy.contrib.downloadermiddleware.redirect.MetaRefreshMiddleware': 580,
    # 'scrapy.contrib.downloadermiddleware.httpcompression.HttpCompressionMiddleware': 590,
    # 'scrapy.contrib.downloadermiddleware.redirect.RedirectMiddleware': 600,
    # 'scrapy.contrib.downloadermiddleware.cookies.CookiesMiddleware': 700,
    # 'scrapy.contrib.downloadermiddleware.httpproxy.HttpProxyMiddleware': 750,
    # 'scrapy.contrib.downloadermiddleware.chunked.ChunkedTransferMiddleware': 830,
    # 'scrapy.contrib.downloadermiddleware.stats.DownloaderStats': 850,
    # 'scrapy.contrib.downloadermiddleware.httpcache.HttpCacheMiddleware': 900,
# }#包含Scrapy默认启用的下载中间件的字典。 
							 #永远不要在项目中修改该设定，而是修改 DOWNLOADER_MIDDLEWARES
							 

##----------  extensions
##---------- 
# See http://scrapy.readthedocs.org/en/latest/topics/extensions.html
# EXTENSIONS = {
    # 'scrapy.extensions.telnet.TelnetConsole': None,
# }  #默认:: {}， 保存项目中启用的插件及其顺序的字典。
	
#EXTENSIONS_BASE	#可用的插件列表。
					#需要注意，有些插件需要通过设定来启用。
					#默认情况下， 该设定包含所有稳定(stable)的内置插件。

##----------  Telnet Console (enabled by default)
##----------  
#TELNETCONSOLE_ENABLED = False	#默认: True, 表明 telnet 终端 (及其插件)是否启用的布尔值
#TELNETCONSOLE_PORT
#



