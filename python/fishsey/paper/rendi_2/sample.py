# -*- coding: utf-8 -*-
"""
Created on Thu Jan 12 11:12:32 2017

@author: root
"""
import numpy as np


trainFile = "/root/AAA/dataset/rendi2/train/sparseness%s/training%d.txt" % (5, 1)   

data = np.loadtxt(trainFile)[:, 2]