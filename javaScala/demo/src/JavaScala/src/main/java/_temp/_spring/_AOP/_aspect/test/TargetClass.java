package _temp._spring._AOP._aspect.test;

public class TargetClass
{
	public void function(String name)
	{
		System.out.println("now a transaction begin ... " + name);
	}


	public int addUser(String name , String pass)
	{
		System.out.println("TargetClass.addUser: " + name + pass);
		return 20;
	}

}
