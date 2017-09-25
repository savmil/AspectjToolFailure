package test;
import provaAspectJ.*;
public class UncheckedException 
{
	@Log
	public void m1()
	{
		ClasseEsempio c=new ClasseEsempio();
		c=null;
		//c.m1(0);
		System.out.println("ciao ciao bambina");
	}
}
