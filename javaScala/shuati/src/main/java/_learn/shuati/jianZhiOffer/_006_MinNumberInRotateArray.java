package _learn.shuati.jianZhiOffer;

/**
 * Created by root on 5/23/17.
 */
public class _006_MinNumberInRotateArray
{
    //fist elem < pre-elem is the min-elem
	public int minNumberInRotateArray(int [] array)
	{
        int min = 0;
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] < min)
                return array[i];
            min = array[i];
        }
        return min;
    }

    public static void main(String args[])
    {
        _006_MinNumberInRotateArray obj = new _006_MinNumberInRotateArray();
        System.out.println(obj.minNumberInRotateArray(new int[]{3, 4, 5, 1, 2}));
    }
}
