package net.sf.ahtutils.monitor;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.factory.xml.sync.XmlExceptionFactory;
import net.sf.ahtutils.factory.xml.sync.XmlExceptionsFactory;
import net.sf.ahtutils.xml.status.Type;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.sync.Result;
import net.sf.exlp.util.DateUtil;

public class DataUpdateTracker implements net.sf.ahtutils.interfaces.controller.DataUpdateTracker
{
	final static Logger logger = LoggerFactory.getLogger(DataUpdateTracker.class);
	
	public static enum Code {success,fail,partial}
	
	private DataUpdate update;
	
	private Map<String,Integer> mapSuccess;
	private Map<String,Integer> mapFail;
	
	public DataUpdateTracker()
	{
		this(false);
	}
	public DataUpdateTracker(boolean autoStart)
	{
		update = new DataUpdate();
		
		update.setResult(new Result());
		update.getResult().setSuccess(0);
		update.getResult().setFail(0);
		update.getResult().setTotal(0);
		
		mapSuccess = new HashMap<String,Integer>();
		mapFail = new HashMap<String,Integer>();
		
		if(autoStart){start();}
	}
	
	public void start()
	{
		update.setBegin(DateUtil.getXmlGc4D(new Date(), true));
	}
	
	public void stop()
	{
		update.setFinished(DateUtil.getXmlGc4D(new Date(), true));
		update.getResult().setTotal(update.getResult().getSuccess()+update.getResult().getFail());
	}
	
	public void success()
	{
		update.getResult().setSuccess(update.getResult().getSuccess()+1);
	}
	
	@Override public void createSuccess(Class<?> c){}
	
	@Override public void updateSuccess(Class<?> c, long id)
	{
		if(!mapSuccess.containsKey(c.getName())){mapSuccess.put(c.getName(), 0);}
		mapSuccess.put(c.getName(), mapSuccess.get(c.getName())+1);
	}
	
	@Override public void createFail(Class<?> c, Throwable t){}
	@Override public void updateFail(Class<?> c, long id, Throwable t)
	{
		if(!mapFail.containsKey(c.getName())){mapFail.put(c.getName(), 0);}
		mapFail.put(c.getName(), mapFail.get(c.getName())+1);
	}
	
	public void fail(Throwable t, boolean printStackTrace)
	{
		if(printStackTrace){t.printStackTrace();}
		update.getResult().setFail(update.getResult().getFail()+1);
		if(!update.isSetExceptions()){update.setExceptions(XmlExceptionsFactory.build());}
		update.getExceptions().getException().add(XmlExceptionFactory.build(t));
	}
	
	public void add(DataUpdate dataUpdate)
	{
		update.getResult().setFail(update.getResult().getFail()+dataUpdate.getResult().getFail());
		update.getResult().setSuccess(update.getResult().getSuccess()+dataUpdate.getResult().getSuccess());
	}
	
	public void setType(Type type)
	{
		update.setType(type);
	}
	
	public DataUpdate getUpdate() {return update;}
	
	public DataUpdate toDataUpdate()
	{
		if(!update.isSetFinished()){stop();}
		
		if(update.getResult().getSuccess()==update.getResult().getTotal()){update.getResult().setStatus(XmlStatusFactory.create(Code.success.toString()));}
		else if(update.getResult().getFail()==update.getResult().getTotal()){update.getResult().setStatus(XmlStatusFactory.create(Code.fail.toString()));}
		else if(update.getResult().getFail()!=0){update.getResult().setStatus(XmlStatusFactory.create(Code.partial.toString()));}
		
		return update;
	}
	
	public void process(boolean debug)
	{
		Set<String> setClassNames = new HashSet<String>();
		
		setClassNames.addAll(mapSuccess.keySet());
		setClassNames.addAll(mapFail.keySet());
		
		for(String c : setClassNames)
		{
			StringBuffer sb = new StringBuffer();
			sb.append(c);
			sb.append(" success:");if(mapSuccess.containsKey(c)){sb.append(mapSuccess.get(c));}else{sb.append(0);}
			sb.append(" fail:");if(mapFail.containsKey(c)){sb.append(mapFail.get(c));}else{sb.append(0);}
			logger.info(sb.toString());
		}
	}
}