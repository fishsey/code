package _learn.shuati.jianZhiOffer;

/**
 * Created by root on 5/23/17.
 */
public class _001_Find
{
    public static void main(String args[])
    {
        int[][] numbers = {
                {1, 2, 3},
                {4, 5, 6},
                {10, 11, 12}
        };
        System.out.println(Find(10, numbers));

    }

    public static boolean Find(int target, int[][] array)
    {
        int rowNum = array.length;
        int columnNum = array[0].length;
        int currentRow = rowNum - 1;
        int currentColumn = 0;
        //current position in right-upper
        while (currentColumn < columnNum && currentRow >= 0)
        {
            int currentValue = array[currentRow][currentColumn];
            if (currentValue == target)
            {
                return true;
            }
            else if (currentValue < target)
            {
                currentColumn++;
            }else
            {
                currentRow--;
            }
        }

        return false;
    }
}
