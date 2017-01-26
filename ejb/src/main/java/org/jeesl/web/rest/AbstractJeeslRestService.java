package org.jeesl.web.rest;

import org.jeesl.factory.xml.jeesl.XmlContainerFactory;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.util.query.xml.StatusQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;

public abstract class AbstractJeeslRestService <L extends UtilsLang,D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslRestService.class);
	
	private final UtilsFacade fUtils;
	
	protected final Class<L> cL;
	protected final Class<D> cD;
	
	protected final XmlContainerFactory xfContainer;

	public AbstractJeeslRestService(final UtilsFacade fUtils,final Class<L> cL, final Class<D> cD)
	{
		this.fUtils=fUtils;
		this.cL=cL;
		this.cD=cD;
		
		xfContainer = new XmlContainerFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
	}
		
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus(Class<S> clStatus, Container container, Class<P> clParent)
    {
    	for(Status xml : container.getStatus()){xml.setGroup(clStatus.getSimpleName());}
		AhtStatusDbInit asdi = new AhtStatusDbInit();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(clStatus,cL,cD));
        asdi.setFacade(fUtils);
        DataUpdate dataUpdate = asdi.iuStatus(container.getStatus(), clStatus, cL, clParent);
        asdi.deleteUnusedStatus(clStatus, cL, cD);
        return dataUpdate;
    }
}