package org.jeesl.util.db;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jeesl.controller.monitor.DataUpdateTracker;
import org.jeesl.factory.ejb.system.status.EjbStatusFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsDeveloperException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.exlp.util.xml.JaxbUtil;

public class JeeslStatusDbUpdater <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription, G extends JeeslGraphic<L,D,G,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslStatusDbUpdater.class);
	
	private final Map<String,Set<Long>> mDbAvailableStatus;
	private Set<Long> sDeleteLangs,sDeleteDescriptions;

	private final JeeslGraphicDbUpdater<G> dbuGraphic;
	private EjbStatusFactory<S,L,D> statusEjbFactory;
	private UtilsFacade fStatus;

	public JeeslStatusDbUpdater()
	{
		mDbAvailableStatus = new Hashtable<String,Set<Long>>();
		sDeleteLangs = new HashSet<Long>();
		sDeleteDescriptions = new HashSet<Long>();
		dbuGraphic = new JeeslGraphicDbUpdater<G>();
	}
	
	public void setStatusEjbFactory(EjbStatusFactory<S,L,D> statusEjbFactory) {this.statusEjbFactory = statusEjbFactory;}
	public void setFacade(UtilsFacade fStatus)
	{
		this.fStatus=fStatus;
		dbuGraphic.setFacade(fStatus);
	}
	
	
	public List<Status> getStatus(String xmlFile) throws FileNotFoundException
	{
		Aht aht = (Aht)JaxbUtil.loadJAXB(xmlFile, Aht.class);
		logger.debug("Loaded "+aht.getStatus().size()+" Elements from "+xmlFile);
		return aht.getStatus();
	}
	
	private boolean isGroupInMap(String group)
	{
		boolean inMap = mDbAvailableStatus.containsKey(group);
		return inMap;
	}
	
	private void savePreviousDbEntries(String key, List<UtilsStatus<S,L,D>> availableStatus)
	{
		Set<Long> dbStatus = new HashSet<Long>();
		for(UtilsStatus<S,L,D> ejbStatus : availableStatus)
		{
			dbStatus.add(ejbStatus.getId());
		}
		logger.debug("Saved existing DB entries for "+key+": "+dbStatus.size());
		mDbAvailableStatus.put(key, dbStatus);
	}
	
	public UtilsStatus<S,L,D> addVisible(UtilsStatus<S,L,D> ejbStatus, Status status)
	{
		boolean visible=true;
		if(status.isSetVisible()){visible=status.isVisible();}
		ejbStatus.setVisible(visible);
		return ejbStatus;
	}
	
	private UtilsStatus<S,L,D> addLangsAndDescriptions(UtilsStatus<S,L,D> ejbStatus, Status status) throws InstantiationException, IllegalAccessException, UtilsConstraintViolationException
	{
		UtilsStatus<S,L,D> ejbUpdateInfo = statusEjbFactory.create(status);		
		ejbStatus.setName(ejbUpdateInfo.getName());
		ejbStatus.setDescription(ejbUpdateInfo.getDescription());
		return ejbStatus;
	}
	
	public void removeStatusFromDelete(String key, long id)
	{
		mDbAvailableStatus.get(key).remove(id);
	}
	
	public List<Long> getDeleteStatusIds()
	{
		List<Long> result = new ArrayList<Long>();
		for(String group : mDbAvailableStatus.keySet())
		{
			Set<Long> delIds = mDbAvailableStatus.get(group);
			Iterator<Long> iterator = delIds.iterator();
			while(iterator.hasNext())
			{
				result.add(iterator.next());
			}
		}
		return result;
	}
	
	public void deleteUnusedStatus(Class<S> cStatus, Class<L> cLang, Class<D> cDescription)
	{
		logger.debug("Deleting unused childs of Status: "+cLang.getName()+":"+sDeleteLangs.size());
		for(long id : sDeleteLangs)
		{
			try
			{
				logger.trace("Deleting "+cLang.getName()+": "+id);
				L lang = fStatus.find(cLang, id);
				logger.trace("\t"+lang);
				fStatus.rm(lang);
			}
			catch (UtilsNotFoundException e) {logger.error("",e);}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
		}
		for(long id : sDeleteDescriptions)
		{
			try
			{
				logger.debug("Deleting "+cDescription.getName()+": "+id);
				D d = fStatus.find(cDescription, id);
				fStatus.rm(d);
			}
			catch (UtilsNotFoundException e) {logger.error("",e);}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
		}
		
		 for(String group : mDbAvailableStatus.keySet())
		 {
			 Set<Long> sIds = mDbAvailableStatus.get(group);
			 logger.trace("Deleting Group "+group+": "+sIds.size());
			 for(long id : sIds)
			 {
				 try
				 {
					 logger.trace("Deleting status: "+id);
					 S status = fStatus.find(cStatus, id);
					 fStatus.rm(status);
				 }
				 catch (UtilsConstraintViolationException e) {logger.error("Error with following ID:"+id,e);}
				 catch (UtilsNotFoundException e)  {logger.error("Error with following ID:"+id,e);}
			 }
		 }
	}
	
	public  void iuStatus(List<Status> list, Class<S> cStatus, Class<L> cLang)
	{
		if(fStatus==null){logger.warn("No Handler available");return;}
		else {logger.debug("Updating "+cStatus.getSimpleName()+" with "+list.size()+" entries");}
		iuStatusEJB(list, cStatus, cLang);
	}
	
	public <P extends UtilsStatus<P,L,D>> DataUpdate iuStatus(List<Status> list, Class<S> cStatus, Class<L> cLang, Class<P> cParent)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cStatus.getName(),"Status-DB Import"));
		
		if(fStatus==null){dut.fail(new UtilsDeveloperException("No Facade available for "+cStatus.getName()), true);}
		else {logger.debug("Updating "+cStatus.getSimpleName()+" with "+list.size()+" entries");}
		iuStatusEJB(list, cStatus, cLang);
		
		for(Status xml : list)
		{
			try
			{
				if(xml.isSetParent() && cParent!=null)
				{
					logger.trace("Parent: "+xml.getParent().getCode());
					S ejbStatus = fStatus.fByCode(cStatus,xml.getCode());
					ejbStatus.setParent(fStatus.fByCode(cParent, xml.getParent().getCode()));
					ejbStatus = fStatus.update(ejbStatus);
					dut.success();
				}
			}
			catch (UtilsConstraintViolationException e){dut.fail(e,true);}
			catch (UtilsLockingException e) {dut.fail(e,true);}
			catch (UtilsNotFoundException e) {dut.fail(e,true);}
		}
		return dut.toDataUpdate();
	}
	
	private void iuStatusEJB(List<Status> list, Class<S> cStatus, Class<L> cLang)
	{
		for(Status xml : list)
		{
			if(!xml.isSetGroup()) {xml.setGroup(cStatus.getName());}
			try
			{
				logger.debug("Processing "+xml.getGroup()+" with "+xml.getCode());
				S ejb;
				if(!isGroupInMap(xml.getGroup()))
				{	// If a new group occurs, all entities are saved in a (delete) pool where
					// they will be deleted if in the current list no entity with this key exists.
					List<UtilsStatus<S,L,D>> l = new ArrayList<UtilsStatus<S,L,D>>();
					for(S s : fStatus.all(cStatus)){l.add(s);}
					savePreviousDbEntries(xml.getGroup(), l);
					logger.debug("Delete Pool: "+mDbAvailableStatus.get(xml.getGroup()).size());
				}
				try
				{
					ejb = fStatus.fByCode(cStatus,xml.getCode());
					
					//UTILS-145 Don't do unnecessary entity updates in AhtStatusDbInit
					ejb = removeData(ejb);
					ejb = fStatus.update(ejb);
					ejb = fStatus.find(cStatus, ejb.getId());
					removeStatusFromDelete(xml.getGroup(), ejb.getId());
					logger.trace("Now in Pool: "+mDbAvailableStatus.get(xml.getGroup()).size());
					logger.trace("Found: "+ejb);
				}
				catch (UtilsNotFoundException e)
				{
					ejb = cStatus.newInstance();
					ejb.setCode(xml.getCode());
					ejb = fStatus.persist(ejb);
					logger.trace("Added: "+ejb);
				}
				
				try
				{
					addLangsAndDescriptions(ejb,xml);
					ejb.setSymbol(xml.getSymbol());
					if(xml.isSetImage()){ejb.setImage(xml.getImage());}
					if(xml.isSetStyle()){ejb.setStyle(xml.getStyle());}
				}
				catch (InstantiationException e) {logger.error("",e);}
				catch (IllegalAccessException e) {logger.error("",e);}
				catch (UtilsConstraintViolationException e) {logger.error("",e);}
		        
				if(xml.isSetPosition()){ejb.setPosition(xml.getPosition());}
		        else{ejb.setPosition(0);}
				
				if(xml.isSetVisible()){ejb.setVisible(xml.isVisible());}
				else{ejb.setVisible(false);}
				
				ejb = fStatus.update(ejb);
			}
			catch (UtilsConstraintViolationException e){logger.error("",e);}
			catch (InstantiationException e) {logger.error("",e);}
			catch (IllegalAccessException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
		}
	}
	
	private S removeData(S ejbStatus)
	{
		Map<String,L> dbLangMap = ejbStatus.getName();
		ejbStatus.setName(null);
		for(UtilsLang lang : dbLangMap.values()){sDeleteLangs.add(lang.getId());}
		
		if(ejbStatus.getDescription()!=null)
		{
			Map<String,D> dbDescrMap = ejbStatus.getDescription();
			ejbStatus.setDescription(null);
			for(UtilsDescription d : dbDescrMap.values()){sDeleteDescriptions.add(d.getId());}
		}
		
		return ejbStatus;
	}
}