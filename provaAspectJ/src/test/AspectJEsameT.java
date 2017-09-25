package test;
import provaAspectJ.*;
public class AspectJEsameT extends Thread
{
	private ClasseEsempio ce;
	private ClasseEsempio ce1;
	private int numero;
	private int val;
	
	public AspectJEsameT(ClasseEsempio c1,ClasseEsempio c2,int i,int j)
	{
		
		ce=c1;
		ce1=c2;
		val=i;
		numero=j;
		start();
	}
	
	@Log
	public void run()
	{
		if(val==1)
		{
			try
			{
				Thread.sleep(2000);
				
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			Thread.sleep(100);
			
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		ce.m1(0,numero);
		if(ce1!=null)
		{
			if(numero==7)
			{
				//ce.m1(1,numero);
				System.out.println("numero " +numero);
				AspectJEsameT1 th=new AspectJEsameT1(ce1,0,0,numero);
			}
			else
			{
				
				System.out.println("numero " +numero);
				AspectJEsameT1 th=new AspectJEsameT1(ce1,0,0,numero);
			}
		}
	}

}
