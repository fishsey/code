package _learn.javaSe._reflex._basic.entity;

/**
 * Created by fishsey on 2017/3/17.
 */
public class PrivateEntity
{
    int val = 10;
    private String priStr = "private string";

    public int getVal()
    {
        return val;
    }

    private void privateMethod(String arg)
    {
        System.out.println("private method invoked");
        System.out.println("arg: " + arg);
    }

    public void setVal(int val)
    {
        this.val = val;
    }

}
