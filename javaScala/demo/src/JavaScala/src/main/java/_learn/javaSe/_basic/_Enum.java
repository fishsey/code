package _learn.javaSe._basic;

/**
 * Created by root on 10/24/16.
 */
public enum _Enum
{
    fishsy(001), fish(002), sey(003);
    private int number;
    
    _Enum(int number)
    {
        this.number = number;
    }

    public int getValue()
    {
        return number;
    }

    
    public static void main(String args[])
    {
        _Enum test = _Enum.fish;
        System.out.println(test.getValue());

        System.out.println(test.name());
        System.out.println(test.ordinal());
    }
}
