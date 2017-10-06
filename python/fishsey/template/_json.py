# -*- coding: utf-8 -*-
"""
Created on Sun Nov 20 14:46:30 2016

@author: fishsey
"""

#==============================================================================
# 序列化、反序列化 对象
#http://www.liaoxuefeng.com/wiki/001374738125095c955c1e6d8bb493182103fac9270762a000/00138683221577998e407bb309542d9b6a68d9276bc3dbe000
#==============================================================================
import json
class obj:
    def __init__(self, name, age):
        self.name = name
        self.age = age
        
    @classmethod
    def objToSer(self, s):
        return {'name':s.name, 'age':s.age}
    
    @classmethod
    def serToObj(self, s):
        return obj(s['name'], s['age'])



obj1 = obj('fishsey', 11)

#方法 default 指明将对象序列化的方法
strs = json.dumps(obj1, default=obj.objToSer)

#使用对象的默认属性 __dict__
print json.dumps(obj1, default=lambda obj: obj.__dict__)

#得到一个字典对象
obj2 = json.loads(strs)

#object_hook, 将字典对象转换为对象
obj3 = json.loads(strs, object_hook=obj.serToObj)
