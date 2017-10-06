package tools._datastructure._tree._util;

/**
 * Created by fishsey on 2017/1/26.
 * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。
 * 如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
 */
public class _VerifyPostOrderSequenceOfBST
{
    public static void main(String args[])
    {
        int[] seq = {2, 4, 3, 6, 8, 7, 5};
        System.out.println(VerifyPostOrderOfBST(seq));
    }

    public static boolean VerifyPostOrderOfBST(int [] sequence)
    {
        if (sequence.length == 0)
            return false;
        return assertPost(sequence, 0, sequence.length-1);
    }

    private static boolean assertPost(int[] sequence, int left, int right)
    {
        if (left >= right)
            return true;

        int root = right; //the rightest value is the root
        int middle = right-1; //init the middle split position

        while (middle>=left && sequence[middle]>sequence[root])
        {
            middle--;
        }
        for (int i=middle; i>=left; i--)
        {
            //if left tree larger than root, then return false
            if (sequence[i] > sequence[root])
                return false;
        }
        //recur assert left tree and right tree
        return assertPost(sequence, left, middle) && assertPost(sequence, middle+1, right-1);

    }
}
