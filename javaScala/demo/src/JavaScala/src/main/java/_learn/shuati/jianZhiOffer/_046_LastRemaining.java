package _learn.shuati.jianZhiOffer;

/**
 * Created by root on 2/25/17.
 * 报数，求最后留下的数字
 */
public class _046_LastRemaining
{
    public static void main(String args[])
    {
        int n = 14, m=2;
        System.out.println(lastRemaining(n, m));
        
    }
    
    private static int lastRemaining(int n, int m)
    {
        
        int[] flag = new int[n];
        for (int i = 0; i < flag.length; i++)
        {
            flag[i] = 0;//flag array, 1 means out of game, 0 means in game
        }
        
        int curPos = 0;//the position ready for report
        int remaid = n; //the numbers of remain people
        int lastReport = -1;//previous position reported
        while (remaid > 0)
        {
            int count = 0;
            while (count < m)
            {
                if (flag[curPos] != 1)
                {
                    lastReport = curPos;
                    count++;
                }
                curPos = (curPos+1) % n; //next pos
            }
            
            flag[lastReport] = 1; //the position out of game
            remaid--;
        }
        
        return lastReport;
    }
}
