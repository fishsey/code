package _temp._spring._AOP._springAop;

import org.springframework.stereotype.Component;

@Component
public class TargetClass
{
    public java.lang.StringBuffer text = new StringBuffer("string");

    public StringBuffer getText()
    {
        return text;
    }

    public void function()
	{
	    System.out.println("now a transaction begin ... ");
	}

	public int addUser(String name , String pass)
	{
		System.out.println("TargetClass.addUser: " + name + pass);
		return 20;
	}

	public StringBuffer get(String  s)
    {
        return text;
    }
}
