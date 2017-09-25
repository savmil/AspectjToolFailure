package test;

import java.io.IOException;
import provaAspectJ.*;
public class ThrowsCheckedException 
{
	@Log
	public void m1(int i) throws IOException
	{
		if(i==0)
		{
			throw new IOException();
		}
		else
		{
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{
			
			}
		}
	}
}
