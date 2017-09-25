package provaAspectJ;
import java.util.logging.*;
import java.util.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CatchClauseSignature;
import org.aspectj.lang.reflect.MethodSignature;
import java.io.*;
public aspect Loggable 
{
	private static Logger logger;
	private FileHandler logFile;
	private static LogStructure logs;
	private static Vector<String> nex;
	public Loggable() 
	{
		logger = Logger.getLogger(Loggable.class.getPackage().getName());
		logs=new LogStructure();
		nex=new Vector<String>();
		try
		{
			logFile=new FileHandler(Loggable.class.getPackage().getName()+".txt",true);
			LogFormatter format = new LogFormatter();  
	        logFile.setFormatter(format);
			logger.addHandler(logFile);
			//logger.setUseParentHandlers(false);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized static void writeLog(JoinPoint g,String avviso,String tn)
	{
		String cl=MethodSignature.class.cast(g.getSignature()).toShortString();
		int ob=Object.class.cast(g.getThis()).hashCode();
		if((avviso.compareTo("SSTR")==0)||(avviso.compareTo("SENR")==0))
		{
			avviso=avviso.replace("SSTR", "SST");
			avviso=avviso.replace("SENR", "SEN");
			System.out.println("avviso  "+avviso);
			ob=0;
		}
		if(avviso.compareTo("SST")==0)
		{
			logs.setSSTT(cl,new Date(System.currentTimeMillis()),tn,ob);
			LogRecord record=new LogRecord(Level.INFO, avviso+" "+cl+" "+ob+" "+tn);
			System.out.println("SST"+" "+ob);
			logger.log(record);
		}
		else if(avviso.compareTo("SEN")==0)
		{
			Date deltat=logs.setSEN(cl,new Date(System.currentTimeMillis()),tn,ob);
			LogRecord record=new LogRecord(Level.INFO,avviso+" "+cl+" "+ob+" "+tn+" "+deltat.getTime());
			System.out.println("SEN"+" "+ob);
			logger.log(record);
		}
		
	}
	public synchronized static void writeLogCMP(JoinPoint g,String ex,String tn)
	{
		Object cl=g.getThis();
		LogRecord record=new LogRecord(Level.WARNING,"CMP "+cl+" "+" "+ex);
		logger.log(record);
	}
	public synchronized static void writeLogCE(JoinPoint g,String ex,String tn)
	{
		String cl=CatchClauseSignature.class.cast(g.getSignature()).getDeclaringType().getName();
		LogRecord record=new LogRecord(Level.INFO,"gestione eccezione "+cl+" "+" "+ex);
		logger.log(record);
	}
	public synchronized static void writeLogm(JoinPoint g,String avviso)
	{
		if(avviso.compareTo("SST")==0)
		{
			LogRecord record=new LogRecord(Level.INFO, avviso+" "+g.getSignature().getName()+" "+g.getSignature().getDeclaringTypeName());
			logger.log(record);
		}
		else if(avviso.compareTo("SEN")==0)
		{
			LogRecord record=new LogRecord(Level.INFO,avviso+" "+g.getSignature().getName() );
			record.setMessage(avviso+" "+g.getSignature().getName());
			logger.log(record);
		}
	}
	//declare soft: Exception :execution(* *(..));
	pointcut allMethod():execution(* *(..));
	pointcut allMethode():execution(* *(..)throws *);
	pointcut he():handler(*);
	pointcut LogStructure():execution(public synchronized Date setSEN(..));
	pointcut hre():handler(RuntimeException);
	pointcut main(): execution(public static void main(..));
	pointcut run() : execution(public void run(..));       
	before() : main()
	{
		Loggable.writeLogm(thisJoinPoint, "SST");
	}
	after() : main()
	{
		Loggable.writeLogm(thisJoinPoint, "SEN");
	}
	before() : run() 
	{
		System.out.print("sono quiiiiiiiiiiiiii");
		System.out.println("SST"+Thread.currentThread().getName());
		Loggable.writeLog(thisJoinPoint, "SSTR",Thread.currentThread().getName());
	}
	after() : run() 
	{ 
		System.out.println("SEN"+Thread.currentThread().getName());
		Loggable.writeLog(thisJoinPoint, "SENR",Thread.currentThread().getName());
	}
	before(): allMethod() && @annotation(provaAspectJ.Log)  && !main() && !run()
	{
		Loggable.writeLog(thisJoinPoint, "SST",Thread.currentThread().getName());
	}
	
	before(): he() 
	{
		String e=CatchClauseSignature.class.cast(thisJoinPoint.getSignature()).toLongString();
		Loggable.writeLogCE(thisJoinPoint,e,Thread.currentThread().getName());
		System.out.println("qui");
	}
	after() throwing(Exception e) : allMethod() && @annotation(provaAspectJ.Log) 
	{
		nex.add(thisJoinPoint.getSignature().toShortString()+thisJoinPoint.getThis().toString());
		System.out.println("sono eccezione"+e.toString());
		Loggable.writeLogCMP(thisJoinPoint,e.toString(),Thread.currentThread().getName());
	}
	after() : allMethod() && @annotation(provaAspectJ.Log)  && !main() && !run() && if(!nex.contains(thisJoinPoint.getSignature().toShortString()+thisJoinPoint.getThis().toString()+Thread.currentThread().getName())) 
	{
		Loggable.writeLog(thisJoinPoint, "SEN",Thread.currentThread().getName());
	}
	/*after() returning(Object r): allMethod() && @annotation(provaAspectJ.Log) && !main()
	{
		System.out.println("sono qui");
		Loggable.writeLog(thisJoinPoint, "CMP", Thread.currentThread().getName());
	}*/
	/*Object around():allMethod() && @annotation(provaAspectJ.Log)  && !main()
	{
		Object o=new Object();
		try
		{
			o=proceed();
		}
		catch(Exception e)
		{
			System.out.println("sono un eccezione");
			Loggable.writeLog(thisJoinPoint, "CMP",Thread.currentThread().getName());
		}
		catch(Error e)
		{
			System.out.println("sono un errore");
			Loggable.writeLog(thisJoinPoint, "CMP",Thread.currentThread().getName());
		}
		return o;
	}*/
	//declare precedence;
	//allMethode() && @annotation(provaAspectJ.Log)  && !main()
	
	
}
