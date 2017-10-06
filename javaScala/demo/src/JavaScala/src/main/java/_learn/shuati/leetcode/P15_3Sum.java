package _learn.shuati.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by fishsey on 2017/9/10.
 */
public class P15_3Sum
{
    List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> threeSum(int[] nums)
    {
        HashSet<Integer> set = new HashSet<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++)
        {
            int temp = nums[i];
            if (set.contains(temp))
                continue;
            else
            {
                set.add(temp);
                getTruple(nums, i, 0-temp);
            }
        }
        return result;
    }

    private void getTruple(int[] nums, int index, int target)
    {
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> setExclude = new HashSet<>();
        for (int i = index+1; i < nums.length; i++)
        {
            if (setExclude.contains(nums[i]))
            {
                continue;
            }

            int temp = target - nums[i];
            if (set.contains(temp))
            {
                List<Integer> ans = new ArrayList<Integer>();
                ans.add(0-target);
                ans.add(temp);
                ans.add(nums[i]);
                result.add(ans);

                set.remove(temp);
                setExclude.add(nums[i]);
                setExclude.add(temp);
            }else
            {
                set.add(nums[i]);
            }
        }
    }

    @Test
    public void test_()
    {
         int[] s = {-1, 0, 1, 2, -1, -4};
        threeSum(s).forEach(System.out::println);

    }
}
