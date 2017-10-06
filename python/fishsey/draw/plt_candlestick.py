# -*- coding: utf-8 -*-
"""
Created on Sat Apr 09 11:50:40 2016

@author: fishsey
"""
from matplotlib.dates import DateFormatter
from matplotlib.dates import DayLocator
from matplotlib.dates import MonthLocator
from matplotlib.finance import quotes_historical_yahoo_ohlc
from matplotlib.finance import candlestick_ohlc
from datetime import date
import unittest
from matplotlib import pyplot as plt

def pltPoly(symbol = 'DISH'):
    today = date.today()
    start = (today.year - 1, today.month, today.day)
    month_formatter = DateFormatter("%b %Y")
    alldays = DayLocator()              
    months = MonthLocator()
    quotes = quotes_historical_yahoo_ohlc(symbol, start, today)
    fig = plt.figure()
    ax = fig.add_subplot(111)
    ax = plt.subplot(111)
    ax.xaxis.set_major_locator(months)
    ax.xaxis.set_minor_locator(alldays)
    ax.xaxis.set_major_formatter(month_formatter)
    candlestick_ohlc(ax, quotes)
    fig.autofmt_xdate()
    plt.show()


    
class testClass(unittest.TestCase):
    def test1(self):
        pltPoly()
if __name__ == '__main__':
    unittest.main()