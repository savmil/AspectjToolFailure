package test;

import java.io.File;
import java.io.IOException;
import provaAspectJ.*;
public class CheckedException 
{
	@Log
	public void m1()
	{
		try
		{
			throw new IOException();
			//File f=new File("c");
			//f.createNewFile();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
