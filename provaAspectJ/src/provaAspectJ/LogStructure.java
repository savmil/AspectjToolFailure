package provaAspectJ;
import java.util.*;
public class LogStructure 
{
	class RecordTime
	{
		private Map<String,Date> SENT; //tempo di inizio esecuzione del tempo
		private Vector<Date> deltat;
		private int dimensione;
		private final int numberofmethod=10;//tiene traccia del tempo d' esecuzione delle ultime x terminazioni del metodo
		private Map<String,Integer>mt;//tiene traccia del numero di esecuzioni per il metodo
		public RecordTime()
		{
			SENT=new HashMap<String, Date>();
			deltat=new Vector<Date>();
			dimensione=-1;
			mt=new HashMap<String,Integer>();
		}
		public Date getSENT(String m) {
			return SENT.get(m);
		}
		public void setSENT(String m,Date SENT) {
			this.SENT.put(m,SENT);
		}
		public Vector<Date> getDeltat() {
			return deltat;
		}
		public void setDeltat(Date deltat) {
			dimensione=(++dimensione)%numberofmethod;
			this.deltat.add(dimensione,deltat) ;
		}
	}
	private Map<String,RecordTime> record;
	private Map<String, Timer> timerrecord;
	private final double alpha=0.2;
	private final int numberofexecution=5;//tiene conto dopo quante x esecuzioni si inizia a tenere traccia del metodo
	public LogStructure()
	{
		record=new HashMap<String,RecordTime>();
		timerrecord=new HashMap<String,Timer>();
	}
	public Map<String, RecordTime> getRecord() {
		return record;
	}
	public void setRecord(Map<String, RecordTime> record) {
		this.record = record;
	}
	//method definisce il metodo richiamato, t l' istante di inizio esecuzione ed istanza
	// il nome del thread che esegue tale metodo
	public synchronized void setSSTT(String method,Date t,String istanza,int oggetto)
	{
		System.out.println("ciaooooooo");
		if(record.containsKey(method+oggetto)==false)
		{
			
			//System.out.println(" method"+method+);
			timerrecord.put(method+oggetto+istanza, new Timer());
			System.out.println("INIZIO"+method+istanza+oggetto);
			RecordTime rt=new RecordTime();
			//rt.setSENT(method+istanza,t);
			//System.out.println("mt 1 "+method);
			rt.mt.put(method+oggetto, 0);
			rt.setSENT(method+oggetto+istanza,t);
			record.put(method+oggetto, rt);
			//System.out.println(rt);
		}
		else
		{
			
			//System.out.println("gia inserito"+method);
			long deltat=getDeltaT(method,oggetto);
			
			//System.out.println("valore di deltat"+deltat+method);
			RecordTime rt=record.get(method+oggetto);
			Integer i=rt.mt.get(method+oggetto);
			System.out.println("sono qui rt"+i+" "+method+" "+istanza);
			if(i>=numberofexecution)
			{
				TimerTask wd=new WatchDog(method+" "+istanza+" "+oggetto);
				System.out.println("watchdog per "+method+" "+wd);
				System.out.println("valore del timer"+deltat+method+istanza+oggetto);
				Timer tim=new Timer();
				//System.out.println("INIZIO"+method+istanza);
				tim.schedule(wd, deltat);
				System.out.println("tempo timer"+deltat+" "+istanza);
				timerrecord.put(method+oggetto+istanza, tim);
			}
			
			System.out.println("ho settato il valore");
			rt.setSENT(method+oggetto+istanza,t);
			record.put(method+oggetto, rt);
		}
		
		//RecordTime rt=record.get(method);
		//System.out.println("tempo salvato al SST"+rt+t.getTime()+rt.getSENT(method));
	}
	
	public synchronized Date setSEN(String method,Date t,String istanza,int oggetto)
	{
		//System.out.println("mt 2 "+method);
		RecordTime rt=record.get(method+oggetto);
		
		//System.out.println("il metodo"+ method);
		Integer i=rt.mt.get(method+oggetto);
		
	
		if(i>=numberofexecution)
		{
			Timer tim=timerrecord.get(method+oggetto+istanza);
			System.out.println("FINE"+" "+method+oggetto+istanza);
			try
			{
				tim.cancel();
			}
			catch(NullPointerException e)
			{
				System.out.println("FINE timer"+" "+method+oggetto+istanza);
				System.out.println("timer non definito");
			}
		}
		else
		{
			i++;
			System.out.println("iiiii "+i);
			rt.mt.put(method+oggetto,i);
			record.put(method+oggetto, rt);
		}
		//System.out.println("ho interrotto");
		//System.out.println("tempo al SEN"+t.getTime());
		long time=(long)(t.getTime()-rt.getSENT(method+oggetto+istanza).getTime());
		System.out.println("tempo getSENT"+time+method+istanza+oggetto);
		Date deltat=new Date(time);
		//System.out.println("tempo di delta t calcolato"+deltat.getTime());
		rt.setDeltat(deltat);
		record.put(method+oggetto, rt);
		return deltat;
	}
	public synchronized long getDeltaT(String method,int oggetto)
	{
		long deltat=0;
		RecordTime rt=record.get(method+oggetto);
		for(int i=0;i<rt.getDeltat().size()-1;i++)
		{
			deltat=(long)(deltat+(1-alpha)*rt.getDeltat().get(i).getTime());
			//System.out.println("tempo in get deltat"+deltat);
		}
		if(rt.getDeltat().size()>0)
		{
			deltat=(long)(deltat+alpha*(long)rt.getDeltat().get(rt.getDeltat().size()-1).getTime());
			deltat=deltat/rt.getDeltat().size();
			deltat=3*deltat;//+deltat+deltat;
			//System.out.println("dimensione tempo"+rt.getDeltat().size()+" "+method);
			return deltat;
		}
		return 0;
	}
}
