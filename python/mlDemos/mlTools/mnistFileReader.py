# -*- coding: utf-8 -*-
"""
Created on Sat Sep 30 20:17:01 2017

@author: fishsey
"""
#abPath = "/root/AAA/"  
abPath = "D:/syn/" 

IMAGE_SIZE = 28
NUM_CHANNELS = 1
PIXEL_DEPTH = 255
    
def extract_data(filename, num_images):
  """
  values are rescaled from [0, 255] down to [-0.5, 0.5].
  """
  print('Extracting', filename)
  with gzip.open(filename) as bytestream:
    bytestream.read(16)
    buf = bytestream.read(IMAGE_SIZE * IMAGE_SIZE * num_images * NUM_CHANNELS)
    data = numpy.frombuffer(buf, dtype=numpy.uint8).astype(numpy.float32)
    data = (data - (PIXEL_DEPTH / 2.0)) / PIXEL_DEPTH
    data = data.reshape(num_images, IMAGE_SIZE * IMAGE_SIZE)
    return data


def extract_labels(filename, num_images):
  """Extract the labels into a vector of int64 label IDs."""
  
  print('Extracting', filename)
  with gzip.open(filename) as bytestream:
    bytestream.read(8)
    buf = bytestream.read(1 * num_images)
    labels = numpy.frombuffer(buf, dtype=numpy.uint8).astype(numpy.int64)
  return labels
  
  
def loadTrainAndTestDatasetWithLabelEncode():
    '''
    x: (60000, 784)
    y: (60000, 10)
    tx: (10000, 784)
    ty: (10000, 1)
    '''
    from sklearn.preprocessing import LabelBinarizer
    lb = LabelBinarizer()
    
    trainfile_X = abPath + 'dataset/mnist/train-images-idx3-ubyte.gz'
    trainfile_y = abPath + 'dataset/mnist/train-labels-idx1-ubyte.gz'
    testfile_X = abPath + 'dataset/mnist/t10k-images-idx3-ubyte.gz'
    testfile_y = abPath + 'dataset/mnist/t10k-labels-idx1-ubyte.gz'
    
    train_X = extract_data(trainfile_X, 60000)
    train_y = extract_labels(trainfile_y, 60000)
    test_X = extract_data(testfile_X, 10000)
    test_y = extract_labels(testfile_y, 10000)
    
    # handle trainLabels with oneHotEncoder
    train_y = lb.fit_transform(train_y)

    return train_X, train_y, test_X, test_y

  
import numpy
import gzip
if __name__ == "__main__":
    tx, ty, x, y = loadTrainAndTestDatasetWithLabelEncode()