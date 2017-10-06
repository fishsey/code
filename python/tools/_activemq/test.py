# -*- coding: utf-8 -*-
"""
Created on Sat Jul  8 18:10:11 2017

@author: root
"""

import time
import sys
import stomp

class urlListener(object):
    def on_message(self, headers, message):
        print 'received a url:....................... ',  message


class statusListener(object):
    def on_message(self, headers, message):
        print 'received a status ....................... ', message
        
urlPullQueue = '/queue/douban.url.slave.0001'
statusPullQueue = '/queue/douban.status.slave.0001'


connUrl = stomp.Connection10([('10.65.1.62',61613)])  
connUrl.set_listener('', urlListener())
connUrl.start()
connUrl.connect()
connStatus = stomp.Connection10([('10.65.1.62',61613)]) 
connStatus.set_listener('', statusListener())
connStatus.start()
connStatus.connect()

connUrl.send(body='hello, serena!', destination=urlPullQueue)
connStatus.send(body='hello, serena!', destination=statusPullQueue)

#connUrl.subscribe(destination=urlPullQueue, ack='auto')
#connStatus.subscribe(destination=statusPullQueue, ack='auto')

def sleep():
    time.sleep(60)

sleep()
connUrl.disconnect()
connStatus.disconnect()