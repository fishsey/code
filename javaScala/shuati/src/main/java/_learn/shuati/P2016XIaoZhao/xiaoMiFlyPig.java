package _learn.shuati.P2016XIaoZhao;

import org.junit.Assert;

/**
 * Created by root on 6/7/17.
 */
public class xiaoMiFlyPig
{
    public static void main(String args[])
    {
        int[] prices = {3, 8, 5, 1, 7};
        Assert.assertEquals(12, calculateMax(prices));
    }

    /**
     * 计算你能获得的最大收益
     * @param prices Prices[i]即第i天的股价
     * @return 整型
     */
    public static int calculateMax(int[] prices)
    {
        int firstBuy = Integer.MIN_VALUE;  //表示第一次买入的最大收益，因为是买入所以是负值
        int firstSell = 0; //表示第一次卖出的最大收益
        int secondBuy = Integer.MIN_VALUE; //表示第二次买入的最大收益
        int secondSell = 0; //表示第二次卖出的最大收益

        for (int curPrice : prices)
        {
            if (firstBuy < -curPrice) firstBuy = -curPrice;
            if (firstSell < firstBuy + curPrice) firstSell = firstBuy + curPrice;
            if (secondBuy < firstSell - curPrice)
                secondBuy = firstSell - curPrice;
            if (secondSell < secondBuy + curPrice)
                secondSell = secondBuy + curPrice;
        }

        return secondSell; // secondSell will be the max profit after passing the prices
    }
}
