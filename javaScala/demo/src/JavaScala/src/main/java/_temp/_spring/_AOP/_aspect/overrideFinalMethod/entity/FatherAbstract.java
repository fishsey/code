package _temp._spring._AOP._aspect.overrideFinalMethod.entity;

/**
 * Created by fishsey on 2017/8/24.
 */

public class FatherAbstract
{
    private int anInt1 = 1;

    public final void print()
    {
        System.out.println("father " + anInt1);
    }
}
