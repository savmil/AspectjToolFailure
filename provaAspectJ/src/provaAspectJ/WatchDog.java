package provaAspectJ;

import java.util.*;
import java.util.logging.*;

public class WatchDog extends TimerTask
{
	private static Logger logger;
	//private FileHandler logFile;
	private String m;
	public WatchDog(String method)
	{
		m=method;
		logger = Logger.getLogger(Loggable.class.getPackage().getName());
	}
	public void run()
	{
		System.out.println("SER error fuori tempo!!!! "+m);
		LogRecord record=new LogRecord(Level.WARNING, "SER  "+m);
		logger.log(record);
	}
}
