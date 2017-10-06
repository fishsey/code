package _learn.shuati.leetcode;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fishsey on 2017/9/20.
 */
public class P34_SearchRange
{
    //public T a;
    //public static T a; //会报错，静态字段不允许泛型

    public static <T extends Comparable<T>> int binarySearchLow(List<T> nums, T target, int begin, int end)
    {
        if (begin > end)
            return begin;

        int mid = begin + (end - begin) / 2;

        if (nums.get(mid).compareTo(target) < 0)
            return binarySearchLow(nums, target, mid + 1, end);
        else
            return binarySearchLow(nums, target, begin, mid - 1);
    }

    public static <T extends Comparable<T>> int binarySearchUp(List<T> nums, T target, int begin, int end)
    {
        if (begin > end)
            return end;

        int mid = begin + (end - begin) / 2;

        if (nums.get(mid).compareTo(target) > 0)
            return binarySearchUp(nums, target, begin, mid - 1);
        else
            return binarySearchUp(nums, target, mid + 1, end);
    }

    public static <T extends Comparable<T>> int[] searchRange(List<T> nums, T target)
    {
        int[] res = new int[2];
        res[0] = -1;
        res[1] = -1;
        if (nums.isEmpty())
            return res;
        int high = binarySearchUp(nums, target, 0, nums.size() - 1);
        int low = binarySearchLow(nums, target, 0, nums.size() - 1);
        if (high >= low)
        {
            res[0] = low;
            res[1] = high;
        }
        return res;
    }

    @Test
    public void test_() throws IOException
    {
        List<Integer> alst = Arrays.asList(1, 2, 4, 5, 5, 5, 6);
        int[] range = P34_SearchRange.searchRange(alst, 2);
        System.out.println(range[0] + " " + range[1]);
    }
}
