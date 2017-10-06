package _learn.designPattern._builderPattern;

import java.util.ArrayList;

/**
 * Created by root on 4/14/17.
 */
public class PrototypeMain
{
    public static void main(String[] args)
    {
        ConcretePrototype cp = new ConcretePrototype();
        for (int i = 0; i < 2; i++)
        {
            ConcretePrototype clonecp = (ConcretePrototype) cp.clone();
            System.out.println(clonecp.alist);
            System.out.println(clonecp.alist == cp.alist);//if deep-copy, then return false
            clonecp.show();
        }
    }
}


class Prototype implements Cloneable
{
    public ArrayList<Integer> alist = new ArrayList();
    {
        alist.add(1);
        alist.add(2);
        alist.add(3);
    }
    public Prototype clone()
    {
        Prototype prototype = null;
        try
        {
            //Object.clone is shallow copy
            prototype = (Prototype) super.clone();
            //deep copy by ArrayList.clone()
            prototype.alist = (ArrayList) this.alist.clone();

        } catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return prototype;
    }
}

class ConcretePrototype extends Prototype
{
    public void show()
    {
        System.out.println("prototype pattern");
    }
}
