package _learn.designPattern._actionsPattern;

/**
 * Created by root on 4/16/17.
 */
public class TemplateMethod
{
    public static void main(String args[])
    {
        int[] a = {1, 2, 10, 3, 4, 5};

        AbstractSort sort = new ConcreteSort();
        sort.showSortResult(a);
    }
}

abstract class AbstractSort
{
    /**
     * 将数组array由小到大排序
     * @param array
     */
    protected abstract void sort(int[] array);
    
    public void showSortResult(int[] array)
    {
        this.sort(array);
        System.out.print("排序结果：");
        for (int i = 0; i < array.length; i++)
        {
            System.out.printf("%3s", array[i]);
        }
    }
}

class ConcreteSort extends AbstractSort
{
    @Override
    protected void sort(int[] array)
    {
        for (int i = 0; i < array.length - 1; i++)
        {
            selectSort(array, i);
        }
    }
    /*
    * put min-value in array[index, len-1] at array[index]
    * */
    private void selectSort(int[] array, int index)
    {
        int MinValue = Integer.MAX_VALUE; // 最小值变量
        int indexMin = -1; // 最小值索引变量
        int Temp; // 暂存变量
        for (int i = index; i < array.length; i++)
        {
            if (array[i] <= MinValue)// 找到最小值
            {
                MinValue = array[i];
                indexMin = i;
            }
        }
        Temp = array[index]; //交换两数值
        array[index] = array[indexMin];
        array[indexMin] = Temp;
    }
}
