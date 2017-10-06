package tools._algorithm._dynamicProgramming;

/**
 * Created by fishsey on 2017/1/23.
 */
public class _JuXinFuGai
{
    public static void main(String args[])
    {
        int target = 4;
        System.out.println(RectCover(target));
    }


    public static int RectCover(int target)
    {
        if (target == 0)
            return 0;
        if (target == 1)
            return 1;
        else if (target == 2)
            return 2;
        else
        {
            int fn_2 = 1;
            int fn_1 = 2;
            int fn = -1;
            for (int i = 3; i <= target; i++)
            {
                fn = fn_1 + fn_2;
                fn_2 = fn_1;
                fn_1 = fn;
            }
            return fn;
        }
    }
}
