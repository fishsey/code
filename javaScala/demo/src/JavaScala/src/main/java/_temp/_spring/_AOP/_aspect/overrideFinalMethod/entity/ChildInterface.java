package _temp._spring._AOP._aspect.overrideFinalMethod.entity;

/**
 * Created by root on 8/24/17.
 */
public class ChildInterface implements FatherInterface
{
    private int anInt1 = 1;

    public final void print()
    {
        System.out.println("father " + anInt1);
    }
}
