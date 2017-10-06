# -*- coding: utf-8 -*-
"""
Created on Mon Apr 11 14:57:07 2016

@author: fishsey
"""
import numpy as np
from matplotlib import pyplot as plt
import unittest
from mpl_toolkits.mplot3d import Axes3D   
import unittest   
from matplotlib import cm
def plt3D():
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')
    u = np.linspace(-1, 1, 100)
    x, y = np.meshgrid(u, u)
    z = x**2 + y**2
    ax.plot_surface(x, y, z, rstride=4, cstride=5, cmap=cm.YlGnBu_r)


def pltBars3dDemo():
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')
    for c, z in zip(['r', 'g', 'b', 'y'], [30, 20, 10, 0]):
        xs = np.arange(20)
        ys = np.random.rand(20)
        # You can provide either a single color or an array. To demonstrate this,
        # the first bar of each set will be colored cyan.
        cs = [c] * len(xs)
        ax.bar(xs, ys, zs=z, zdir='y', color=cs, alpha=0.8)
    ax.set_xlabel('X')
    ax.set_ylabel('Y')
    ax.set_zlabel('Z')
    plt.show()

class testClass(unittest.TestCase):
    def test1(self):
        plt3D()
    def test2(self):
        pltBars3dDemo()


if __name__ == '__main__':
    unittest.main()