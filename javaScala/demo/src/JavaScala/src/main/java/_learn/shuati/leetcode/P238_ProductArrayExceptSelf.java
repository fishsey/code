package _learn.shuati.leetcode;

/**
 * Created by root on 2/27/17.
 * 给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],其中B中的元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。
 * 不能使用除法。
 */
public class P238_ProductArrayExceptSelf
{
    public static void main(String args[])
    {
        int[] A = {1, 2, 3, 4};
        int[] B = multiply(A);
    
        for (int i : B)
        {
            System.out.println(i);
        }
        
    }
    
    private static int[] multiply(int[] A)
    {
        int[] B = new int[A.length];

        //B[i] = A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]
    
        //B[i] = A[0]*....A[i-1]
        //leftMul = A[0]*....A[i]
        int leftMul = 1;
        for (int i=0; i<A.length; i++)
        {
            B[i] = leftMul;
            leftMul *= A[i];
        }
        //B[i] *= A[n-1] * ... * A[i+1]
        //rightMul = A[n]*....A[i]
        int rightMul = 1;
        for (int i=A.length-1; i>=0; i--)
        {
            B[i] *= rightMul;
            rightMul *= A[i];
        }
    
        return B;
    }
    
}
