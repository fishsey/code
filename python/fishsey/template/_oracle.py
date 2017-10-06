# -*- coding: utf-8 -*-
"""
Created on Tue Aug 30 15:17:53 2016

@author: fishsey
""" 
import sys
import cx_Oracle

connection = cx_Oracle.Connection('hzfc365soar', 'hzfc365soar', '(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=172.16.1.199)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=orcl)))')
connection.ping()

#cursor = connection.cursor()
#
#try:
#    cursor.execute("select 1 / 0 from dual")
#    dataAll = cursor.fetchall()
#    
#except cx_Oracle.DatabaseError, exc:
#    error, = exc.args
#    print >> sys.stderr, "Oracle-Error-Code:", error.code
#    print >> sys.stderr, "Oracle-Error-Message:", error.message