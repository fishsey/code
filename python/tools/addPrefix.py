# -*- coding: utf-8 -*-
"""
Created on Fri Jul 28 01:36:38 2017

@author: root
"""
import numpy as np

prefix="lib/"
fileName="addPrefix"
data = np.loadtxt(fileName, dtype=str)
for index, elem in enumerate(data):
    data[index] = prefix + data[index]
np.savetxt(fileName, data, fmt="%s", delimiter="\n")