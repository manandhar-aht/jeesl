package org.jeesl.web.mbean.prototype.admin.system.io;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.facade.JeeslIoMailFacade;
import org.jeesl.interfaces.model.system.io.mail.JeeslIoMail;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.prototype.controller.handler.ui.SbMultiStatusHandler;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminIoMailQueueBean <L extends UtilsLang,D extends UtilsDescription,
											CATEGORY extends UtilsStatus<CATEGORY,L,D>,
											MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL,STATUS>,
											STATUS extends UtilsStatus<STATUS,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoMailQueueBean.class);
	
	protected JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS> fMail;
	
	private Class<MAIL> cMail;
	private Class<CATEGORY> cCategory;
	private Class<STATUS> cStatus;

	private List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	private List<MAIL> mails; public List<MAIL> getMails() {return mails;}
	
	private MAIL mail; public MAIL getMail() {return mail;} public void setMail(MAIL mail) {this.mail = mail;}
	
	protected SbMultiStatusHandler<L,D,CATEGORY> sbhCategory; public SbMultiStatusHandler<L,D,CATEGORY> getSbhCategory() {return sbhCategory;}
	protected SbMultiStatusHandler<L,D,STATUS> sbhStatus; public SbMultiStatusHandler<L,D,STATUS> getSbhStatus() {return sbhStatus;}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS> fMail, final Class<L> cLang, final Class<D> cDescription, Class<CATEGORY> cCategory, Class<MAIL> cMail, Class<STATUS> cStatus)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fMail=fMail;
		
		this.cMail=cMail;
		this.cCategory=cCategory;
		this.cStatus=cStatus;
		
		categories = fMail.allOrderedPositionVisible(cCategory);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cCategory,categories));}
		
		sbhCategory = new SbMultiStatusHandler<L,D,CATEGORY>(cCategory,categories);
		sbhCategory.selectAll();
		
		try
		{
			sbhStatus = new SbMultiStatusHandler<L,D,STATUS>(cStatus,fMail.allOrderedPositionVisible(cStatus));
			sbhStatus.select(fMail.fByCode(cStatus,JeeslIoMail.Status.queue));
			sbhStatus.select(fMail.fByCode(cStatus,JeeslIoMail.Status.spooling));
		}
		catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
	}
	
	public void multiToggle(UtilsStatus<?,L,D> o)
	{
		logger.info(AbstractLogMessage.toggle(o)+" Class: "+o.getClass().getSimpleName()+" ");
		if(cCategory.isAssignableFrom(o.getClass())){sbhCategory.multiToggle(o);}
		if(cStatus.isAssignableFrom(o.getClass())){sbhStatus.multiToggle(o);}
		else {logger.warn("No Handling for toggle class "+o.getClass().getSimpleName()+": "+o.toString());}
		reloadMails();
		clear(true);
	}
	
	private void clear(boolean clearMail)
	{
		if(clearMail){mail=null;}
	}
	
	//*************************************************************************************
	protected void reloadMails()
	{
		mails = fMail.fMails(sbhCategory.getSelected());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cMail,mails));}
//		Collections.sort(templates, comparatorTemplate);
	}
		
	public void selectMail()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(mail));}
		
	}
}