package net.sf.ahtutils.web.rest;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractUtilsRest <L extends UtilsLang, D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractUtilsRest.class);

	protected UtilsFacade fUtils;
	
	protected final Class<L> cL;
	protected final Class<D> cD;
	
	protected final String[] defaultLangs;
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	
	private Map<Class<?>,String> mapGroups;

	public AbstractUtilsRest(UtilsFacade fUtils, final String[] defaultLangs, final Class<L> cL, final Class<D> cD)
	{
		this.fUtils=fUtils;
		this.defaultLangs=defaultLangs;
		this.cL=cL;
		this.cD=cD;
		
        efLang = EjbLangFactory.createFactory(cL);
        efDescription = EjbDescriptionFactory.createFactory(cD);
        
        mapGroups = new Hashtable<Class<?>, String>();
	}
	
	public void addGroupCode(Class<?> c, String code)
	{
		mapGroups.put(c,code);
	}
	
	protected <S extends UtilsStatus<S,L,D>> Aht exportStatus(Class<S> c)
	{
		XmlStatusFactory f = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
		
		Aht xml = new Aht();
		for(S s : fUtils.all(c))
		{
			Status status = f.build(s);
			if(mapGroups.containsKey(c)){status.setGroup(mapGroups.get(c));}
			xml.getStatus().add(status);
		}
		return xml;
	}
	
	protected <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus(Class<S> cS, Class<P> cP, Aht container)
	{
		for(Status xml : container.getStatus()){xml.setGroup(cS.getSimpleName());}
		AhtStatusDbInit<S,L,D> asdi = new AhtStatusDbInit<S,L,D>();
	    asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(cS,cL,cD));
	    asdi.setFacade(fUtils);
	    DataUpdate dataUpdate = asdi.iuStatus(container.getStatus(),cS,cL,cP);
	    asdi.deleteUnusedStatus(cS,cL,cD);
	    return dataUpdate;
	}
}