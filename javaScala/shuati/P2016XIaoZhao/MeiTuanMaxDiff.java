package _learn.shuati.P2016XIaoZhao;

/**
 * Created by root on 8/11/17.
 */
public class MeiTuanMaxDiff
{
    public static void main(String args[])
    {
      int[] a = {10, 5};
        System.out.println(getDis(a, 2));
    }
    
    public  static int getDis(int[] A, int n)
    {
        int maxDiff = 0;
        int lens = A.length;
        int[] maxValueIndexToEnd = initMaxValue(A);
        
        for(int i=0; i<lens; i++)
        {
            maxDiff = maxDiff > maxValueIndexToEnd[i]-A[i]  ? maxDiff : maxValueIndexToEnd[i]-A[i];
        }
        
        return maxDiff;
    }

	//计算 result[i] 表示 i 到 lens 中的最大值
    private static int[] initMaxValue(int[] a)
    {
        int[] result = new int[a.length];
        int lens = result.length;

        result[lens-1] = a[lens-1];
        for (int i=lens-2; i>=0; i--)
        {
            result[i] = a[i] > result[i+1] ? a[i] : result[i+1];
        }
        
        return  result;
    }
}
