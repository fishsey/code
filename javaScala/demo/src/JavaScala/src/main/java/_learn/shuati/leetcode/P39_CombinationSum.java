package _learn.shuati.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fishsey on 2017/9/11.
 */
public class P39_CombinationSum
{
    List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> combinationSum(int[] candidates, int target)
    {
        List<Integer> path = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates, 0, target, path);
        return result;
    }

    private void dfs(int[] candidates, int index, int target, List<Integer> path)
    {
        for (int i=index; i<candidates.length; i++)
        {
            int temp = target - candidates[i];
            if (temp < 0)
                continue;
            else if (temp == 0)
            {
                path.add(candidates[i]);
                result.add(new ArrayList<>(path));
                path.remove(path.size()-1);
            }else
            {
                path.add(candidates[i]);
                dfs(candidates, i, temp, path);
                path.remove(path.size()-1);
            }
        }
    }

    @Test
    public void test_()
    {
        combinationSum(new int[]{2, 3, 6, 7}, 7);
        result.forEach(System.out::println);
    }
}


