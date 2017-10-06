# -*- coding: utf-8 -*-
"""
Created on Sun May 29 19:52:45 2016

@author: fishsey
"""

from matplotlib import pyplot as plt
import numpy as np

n = 20
Z = np.random.uniform(0,1,n)
plt.pie(Z)
plt.show()