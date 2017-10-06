# -*- coding: utf-8 -*-
"""
Created on Tue Sep 27 15:57:39 2016

@author: fishsey
"""
import MySQLdb

db = MySQLdb.connect("localhost","root","0114","titanic" )
cursor = db.cursor()
flag = 1
with open("dataset/Titanic/train.csv") as pf:
    for s in pf:
        if flag == 1:
            flag = 0
            continue
        sArrays = s.split(",")
        sArrays[3:5] = [sArrays[3].replace('"', "") + ","+ sArrays[4].replace('"', "")]
        sArrays[0] = int(sArrays[0])
        #sql 插入语句
        sql = """insert into origin(PassengerId,Survived,Pclass,Name,Sex,Age,SibSp,Parch,Ticket,Fare,Cabin,Embarked)
        values('%d', '%s', '%s',"%s",'%s','%s','%s','%s','%s','%s','%s','%s')""" % tuple(sArrays)
        try:
           # 执行sql语句
           cursor.execute(sql)
           # 提交到数据库执行
           db.commit()
        except Exception, e:
           # Rollback in case there is any error
           db.rollback()
           print sql
# 关闭数据库连接
db.close()


