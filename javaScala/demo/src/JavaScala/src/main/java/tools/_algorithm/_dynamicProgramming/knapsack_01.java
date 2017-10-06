package tools._algorithm._dynamicProgramming;

import java.util.ArrayList;

/**
 *
 */
/*
 * m(i,j) = （包容量为 j 且 可选前 i 个元素时的最有解）
  * max{m(i-1,j), m(i-1,j-wi)+vi}(j>=wi)
  *     m(i-1,j): 容量为 j 时，不选 i 的策略
  *     m(i-1,j-wi)+vi：容量为 j 时，选择 i 的策略（选了 j 后， 容量剩下 j-wi）
  * m(i-1,j)(j<wi)
 **/
public class knapsack_01
{
    /*
    * 返回结果 result 中的值对应第几个（不是索引值）， 范围为 1-lens
    * 使用 二维数组 存储结果，可能会有内存溢出
    * m: the capacity of bag
    * */
    public static ArrayList<Integer> knapsackForward(int[] weight, int[] val, int m)
    {
        int n = weight.length; //样本个数
        int[][] f = new int[n + 1][m + 1]; //f[i][j]表示前 i个物品能装入容量为j的背包中的最大价值
        int[][] path = new int[n + 1][m + 1];//存储所以被选中过的元素

        //初始化结果矩阵：第一行和第一列
        //背包容量为 0, 选择前 i 个物品
        for (int i = 0; i <= n; i++)
        {
            f[i][0] = 0;
        }
        //背包容量为 i, 选择前 0 个物品
        for (int i = 0; i <= m; i++)
        {
            f[0][i] = 0;
        }
        //通过公式迭代计算, i is the  items can be selected
        for (int i = 1; i <= n; i++)
        {
            //按照背包容量增加顺序更新，即行优先顺序
            for (int j = 1; j <= m; j++)
            {
                if (j < weight[i - 1]) //if j<wi
                    f[i][j] = f[i - 1][j];
                else
                {
                    if (f[i - 1][j] < f[i - 1][j - weight[i - 1]] + val[i - 1])
                    {
                        f[i][j] = f[i - 1][j - weight[i - 1]] + val[i - 1];
                        path[i][j] = 1;
                    } else
                    {
                        f[i][j] = f[i - 1][j];
                    }
                }
            }
        }

        //获取最有解
        ArrayList<Integer> result = new ArrayList<>();
        int i = n, j = m;
        while (i > 0 && j > 0)
        {
            //容量为 j 时， 如果 i 选中，则更新容量 j， 否则查看容量为 j 时，最后一个被选中的
            if (path[i][j] == 1)
            {
                j -= weight[i - 1];
                result.add(i);
            }
            i--;
        }


        return result;
    }


    /*
    * 一维数组法（无须装满）
    * */
    public static int knapsackForwardNotFull(int[] weight, int[] val, int m)
    {
        int n = val.length; //物品个数
        int[] f = new int[m + 1];//背包容量为i时的最有解
        for (int i = 0; i < f.length; i++)
        {
            f[i] = 0;//不必装满则初始化为0
        }
        for (int i = 0; i < n; i++)
        {
            for (int j = f.length - 1; j >= weight[i]; j--)
            {
                //f(n, j) = max(f(n-1, j), f(n-1, j-w[n])+val[n])
                //f(n, j)：背包容量为j时，可选前 n 个的最有策略
                //f(n-1, j)：背包容量为j时，可选前 n-1 个的最有策略(不选第n个)
                if (f[j] < f[j - weight[i]] + val[i])
                {
                    f[j] = f[j - weight[i]] + val[i];//背包容量为 j 时的最优解选中了 i
                }
            }
        }
        return f[m];
    }


    /*
    * 一维数组法（必须装满）
    * */
    public static int knapsackForwardMustFull(int[] weight, int[] val, int m)
    {
        int n = val.length; //物品个数
        //存储中间结果
        int[] f = new int[m + 1];
        for (int i = 0; i < f.length; i++)
        {
            f[i] = Integer.MIN_VALUE;
            ;//不必装满则初始化为0
        }
        //迭代更新
        for (int i = 0; i < n; i++)
        {
            for (int j = f.length - 1; j >= weight[i]; j--)
            {
                f[j] = Math.max(f[j], f[j - weight[i]] + val[i]);
            }
        }
        return f[m];
    }


    /*
   * 一维数组法（完全背包，无须装满）
   * */
    public static int fullKnapsackForward(int[] weight, int[] val, int m)
    {
        int n = val.length; //物品个数
        int[] f = new int[m + 1];//背包容量为i时的最有解
        for (int i = 0; i < f.length; i++)
        {
            f[i] = 0;//不必装满则初始化为0
        }
        for (int i = 0; i < n; i++)
        {
            for (int j = weight[i]; j < f.length; j++)
            {
                //f(n, j) = max(f(n-1, j), f(n-1, j-w[n])+val[n])
                //f(n, j) = max(f(n-1, j), f(n-1, j-w[n])+val[n])
                //f(n, j)：背包容量为j时，可选前 n 个的最有策略
                //f(n-1, j)：背包容量为j时，可选前 n-1 个的最有策略(不选第n个)
                if (f[j] < f[j - weight[i]] + val[i])
                {
                    f[j] = f[j - weight[i]] + val[i];//背包容量为 j 时的最优解选中了 i
                }
            }
        }
        return f[m];
    }


    /**
     * @param: q is the restrict condition
     */
    public static int getMaxValue(int[] val, int[] weight, int[] q, int n, int w)
    {
        int[][] dp = new int[n + 1][w + 1];
        for (int i = 1; i <= n; i++) //n items
        {
            for (int j = 1; j <= w; j++) // w capacity
            {
                if (q[i - 1] == 0)// 主件
                {
                    if (weight[i - 1] <= j) // 用j这么多钱去买 i 件商品 可以获得最大价值
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i - 1]] + val[i - 1]);
                } else//附件
                {
                    if (weight[i - 1] + weight[q[i - 1]] <= j) //附件的话 加上主件一起算
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i - 1]] + val[i - 1]);
                }
            }
        }
        return dp[n][w];
    }


    public static void main(String args[])
    {
        int[] weight = {2, 2, 6, 5, 4}; //物品重量
        int[] val = {6, 3, 5, 4, 6}; //物品价值
        int m = 10; //背包容量
        ArrayList path = knapsackForward(weight, val, m);
        System.out.println(path);


        //System.out.println(knapsackForwardNotFull(weight, val, m));
    }
}
