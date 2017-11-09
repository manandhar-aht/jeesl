package org.jeesl.web.mbean.prototype.admin.system.io;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoMailFacade;
import org.jeesl.api.handler.sb.SbDateIntervalSelection;
import org.jeesl.controller.handler.sb.SbDateHandler;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.io.mail.JeeslIoMail;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminIoMailQueueBean <L extends UtilsLang,D extends UtilsDescription,
											CATEGORY extends UtilsStatus<CATEGORY,L,D>,
											MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL,STATUS,RETENTION>,
											STATUS extends UtilsStatus<STATUS,L,D>,
											RETENTION extends UtilsStatus<RETENTION,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean,SbDateIntervalSelection
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoMailQueueBean.class);
	
	protected JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS,RETENTION> fMail;
	
	private Class<MAIL> cMail;
	private Class<CATEGORY> cCategory;
	private Class<STATUS> cStatus;

	private List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	private List<MAIL> mails; public List<MAIL> getMails() {return mails;}
	
	private MAIL mail; public MAIL getMail() {return mail;} public void setMail(MAIL mail) {this.mail = mail;}
	
	protected SbMultiHandler<CATEGORY> sbhCategory; public SbMultiHandler<CATEGORY> getSbhCategory() {return sbhCategory;}
	protected SbMultiHandler<STATUS> sbhStatus; public SbMultiHandler<STATUS> getSbhStatus() {return sbhStatus;}
	private final SbDateHandler sbhDate; public SbDateHandler getSbhDate() {return sbhDate;}

	public AbstractAdminIoMailQueueBean(final Class<L> cL, final Class<D> cD)
	{
		super(cL,cD);
		sbhDate = new SbDateHandler(this);
		sbhDate.initWeeksToNow(2);
	}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS,RETENTION> fMail, final Class<L> cLang, final Class<D> cDescription, Class<CATEGORY> cCategory, Class<MAIL> cMail, Class<STATUS> cStatus)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fMail=fMail;
		
		this.cMail=cMail;
		this.cCategory=cCategory;
		this.cStatus=cStatus;
		
		categories = fMail.allOrderedPositionVisible(cCategory);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cCategory,categories));}
		
		sbhCategory = new SbMultiHandler<CATEGORY>(cCategory,categories,this);
		sbhCategory.selectAll();
		
		try
		{
			sbhStatus = new SbMultiHandler<STATUS>(cStatus,fMail.allOrderedPositionVisible(cStatus),this);
			sbhStatus.select(fMail.fByCode(cStatus,JeeslIoMail.Status.queue));
			sbhStatus.select(fMail.fByCode(cStatus,JeeslIoMail.Status.spooling));
		}
		catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
	}
	
	public void toggled(Class<?> c)
	{
		logger.info(AbstractLogMessage.toggled(c));
		if(cCategory.isAssignableFrom(c)){logger.info(cCategory.getName());}
		else if(cStatus.isAssignableFrom(c)){logger.info(cStatus.getName());}
		reloadMails();
		clear(true);
	}
	
	@Override
	public void callbackDateChanged()
	{
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
		mails = fMail.fMails(sbhCategory.getSelected(),sbhStatus.getSelected(),sbhDate.getDate1(),sbhDate.getDate2());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cMail,mails));}
//		Collections.sort(templates, comparatorTemplate);
	}
		
	public void selectMail()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(mail));}
		
	}
}