package tools._algorithm._dynamicProgramming;

/**
 * Created by root on 5/23/17.
 * https://www.nowcoder.com/practice/8c82a5b80378478f9484d87d1c5f12a4?tpId=13&tqId=11161&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
 */
public class _JumpFloor
{

    /**
     * https://www.nowcoder.com/practice/8c82a5b80378478f9484d87d1c5f12a4?tpId=13&tqId=11161&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
     * 依据最后一跳的策略：f(n) = f(n-1) + f(n-2)
     */
    public static int JumpFloor(int target)
    {
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

    /**
     * https://www.nowcoder.com/practice/22243d016f6b47f2a6928b4313c85387?tpId=13&tqId=11162&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
     * f(n) = f(n-1) + f(n-2) + .... + f(1) + f(0) = 2 * f(n-1)
     */
    public static int JumpFloorII(int target)
    {

        if (target == 1)
            return 1;
        else
        {
            int fn_1 = 1;
            int fn = -1;
            for (int i = 2; i <= target; i++)
            {

                fn = 2 * fn_1;
                fn_1 = fn;
            }
            return fn;
        }

    }
}
