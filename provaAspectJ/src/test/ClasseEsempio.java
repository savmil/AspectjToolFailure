package test;

import java.io.IOException;
import provaAspectJ.*;
public class ClasseEsempio 
{
	@Log
	public void m1(int i,int numero)
	{
		if(i%2==0)
		{
			System.out.println("numero "+i);
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
				
			}
		}
		else
		{
			System.out.println("numero "+i);
			try
			{
				Thread.sleep(500);
			}
			catch(InterruptedException e)
			{
				
			}
		}
	}

}
