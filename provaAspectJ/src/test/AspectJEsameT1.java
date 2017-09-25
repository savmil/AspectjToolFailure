package test;
import provaAspectJ.*;
public class AspectJEsameT1 extends Thread
{
	private ClasseEsempio ce;
	private int val;
	private int tm1;
	private int numero;
	public AspectJEsameT1(ClasseEsempio c1,int i,int j,int n)
	{
		ce=c1;
		val=i;
		tm1=j;
		numero=n;
		start();
	}
	@Log
	public void run()
	{
			try
			{
				Thread.sleep(100);
				
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		System.out.println("numero "+numero);
		ce.m1(tm1,numero);
	}


}
