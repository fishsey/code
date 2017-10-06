# -*- coding: utf-8 -*-
"""
Created on Wed Jul 19 17:15:49 2017

@author: root
"""

import requests
from scrapy.selector import Selector
import pymysql

conn = pymysql.connect(host="10.65.1.62", user="fishsey", passwd="0114", db="proxy_ip", charset="utf8")
cursor = conn.cursor()

def crawl_ips():
    get = GetIP()
    #爬取西刺的免费ip代理
    headers = {"User-Agent":"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0"}
    for i in range(1568):
        re = requests.get("http://www.xicidaili.com/nn/{0}".format(i), headers=headers)

        selector = Selector(text=re.text)
        all_trs = selector.css("#ip_list tr")

        ip_list = []
        for tr in all_trs[1:]:
            speed_str = tr.css(".bar::attr(title)").extract()[0]
            if speed_str:
                speed = speed_str.encode('utf-8').split("秒")[0]
            all_texts = tr.css("td::text").extract()

            ip = all_texts[0]
            port = all_texts[1]
            proxy_type = all_texts[5]

            ip_list.append((ip, port, proxy_type, speed))

        for ip_info in ip_list:
            if get.judge_ip(ip_info[0], ip_info[1]):
                print ip_info
                cursor.execute(
                    "insert proxy(ip, port, speed, proxy_type) VALUES('{0}', '{1}', {2}, 'HTTP')".format(
                        ip_info[0], ip_info[1], ip_info[3]
                    )
                )
                conn.commit()

class GetIP(object):
    def delete_ip(self, ip):
        #从数据库中删除无效的ip
        delete_sql = """
            delete from proxy where ip='{0}'
        """.format(ip)
        cursor.execute(delete_sql)
        conn.commit()
        return True
        

    def judge_ip(self, ip, port):
        #判断ip是否可用
        http_url = "https://www.baidu.com"
        proxy_url = "https://{0}:{1}".format(ip, port)
        
        try:
            proxy_dict = {
                "https":proxy_url,
            }
            response = requests.get(http_url, timeout=1, proxies=proxy_dict)
        except:
            self.delete_ip(ip)
            return False
            
        code = response.status_code
        if code >= 200 and code < 300:
            return True
        else:
            self.delete_ip(ip)
            return False


    def get_random_ip(self):
        #从数据库中随机获取一个可用的ip
        random_sql = """
              SELECT ip, port FROM proxy
              ORDER BY RAND()
              LIMIT 1
            """
        cursor.execute(random_sql)
        for ip_info in cursor.fetchall():
            ip = ip_info[0]
            port = ip_info[1]
            print ip, port
            judge_re = self.judge_ip(ip, port)
            if judge_re:
                return "http://{0}:{1}".format(ip, port)
            else:
                return self.get_random_ip()


if __name__ == "__main__":
    crawl_ips()
