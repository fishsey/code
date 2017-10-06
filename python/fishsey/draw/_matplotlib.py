# -*- coding: utf-8 -*-
"""
Created on Wed May 17 13:49:44 2017

@author: fishsey
"""
from PIL import Image
import matplotlib.pyplot as plt # plt 用于显示图片
import matplotlib.image as mpimg # mpimg 用于读取图片
import numpy as np
from scipy import misc    
    
def showPic():
    #读取图片
    lena = mpimg.imread('temp/ip.png') 
    print lena.shape #(512, 512, 3)
    
    #显示图片
    plt.imshow(lena) # 显示图片
    plt.axis('off') # 不显示坐标轴
    plt.show()
    
    #保存图像
    plt.imshow(lena)
    plt.axis('off')
    plt.savefig('temp/t1.png')
    #将 array 保存为图像
    misc.imsave('temp/t2.png', lena)
    #直接保存 array
    np.save('temp/lena', lena) # 会在保存的名字后面自动加上.npy
    img = np.load('temp/lena.npy') # 读取前面保存的数组

    # 显示某个通道: 显示图片的第一个通道
    ##热量图: 'hot' 
    lena_1 = lena[:, :, 0]
    plt.imshow(lena_1)
    plt.show()
    
    #添加 cmap 参数，有如下几种添加方法：
    plt.imshow(lena_1, cmap='Greys_r')
    plt.show()
    img = plt.imshow(lena_1)
    img.set_cmap('gray') # 'hot' 是热量图
    plt.show()
    
    

def showPicPIL():
    #读取图片
    im = Image.open('temp/ip.png')
    im_array = np.array(im)
    print im_array.shape
    
    #显示图片
    im.show()
    
    #RGB 转换为灰度图
    L = im.convert('L')
    L.show()
    
    #数据转换
    lena = mpimg.imread('temp/ip.png') # 这里读入的数据是 float32 型的，范围是 0-1
    im = Image.fromarray(np.uinit8(lena*255))#PIL.Image 数据是 uinit8 型的，范围是0-255，所以要进行转换
    im.show()
    #保存图片
    im.save('temp/temp.png')
    

if __name__ == "__main__":
    showPic()    
#    showPicPIL()