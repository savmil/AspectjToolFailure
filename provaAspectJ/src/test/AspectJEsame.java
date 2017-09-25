package test;

import java.io.IOException;
public class AspectJEsame {

	public static void main(String[] args) 
	{
		/*CheckedException ce=new CheckedException();
		ce.m1();
		try {
			ThrowsCheckedException tce=new ThrowsCheckedException();
			tce.m1(0);
		} catch (IOException e) {
			System.out.println("sono qui");
		}
		/*UncheckedException ue=new UncheckedException();
		ue.m1();
		*/
		ClasseEsempio c1=new ClasseEsempio();
		ClasseEsempio c2=new ClasseEsempio();
		//c1.m1(0);
		AspectJEsameT th[]=new AspectJEsameT[10];
		for (int i=0;i<10;i++)
		{
			try
			{
				Thread.sleep(100);
				
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			if(i==8)
			{
				th[i]=new AspectJEsameT(c1,c2,0,i);
			}
			else
			{
				th[i]=new AspectJEsameT(c1,c2,0,i);
			}
		}
		
	}

}
