package org.jeesl.web.mbean.prototype.system.io.mail;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoMailFacade;
import org.jeesl.api.handler.sb.SbDateIntervalSelection;
import org.jeesl.controller.handler.sb.SbDateHandler;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.io.IoMailFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslIoMail;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslMailStatus;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractSettingsIoMailQueueBean <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
											CATEGORY extends UtilsStatus<CATEGORY,L,D>,
											MAIL extends JeeslIoMail<L,D,CATEGORY,STATUS,RETENTION,FRC>,
											STATUS extends JeeslMailStatus<L,D,STATUS,?>,
											RETENTION extends UtilsStatus<RETENTION,L,D>,
											FRC extends JeeslFileContainer<?,?>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean,SbDateIntervalSelection
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSettingsIoMailQueueBean.class);
	
	protected JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS,RETENTION,FRC> fMail;
	
	private Class<MAIL> cMail;
	private Class<CATEGORY> cCategory;
	private Class<STATUS> cStatus;

	private List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	private List<MAIL> mails; public List<MAIL> getMails() {return mails;}
	
	private MAIL mail; public MAIL getMail() {return mail;} public void setMail(MAIL mail) {this.mail = mail;}
	
	protected final SbMultiHandler<CATEGORY> sbhCategory; public SbMultiHandler<CATEGORY> getSbhCategory() {return sbhCategory;}
	protected SbMultiHandler<STATUS> sbhStatus; public SbMultiHandler<STATUS> getSbhStatus() {return sbhStatus;}
	private final SbDateHandler sbhDate; public SbDateHandler getSbhDate() {return sbhDate;}

	public AbstractSettingsIoMailQueueBean(IoMailFactoryBuilder<L,D,CATEGORY,MAIL,STATUS,RETENTION,FRC> fbMail)
	{
		super(fbMail.getClassL(),fbMail.getClassD());
		sbhDate = new SbDateHandler(this);
		sbhDate.setEnforceStartOfDay(true);
		sbhDate.initWeeksToNow(2);
		
		sbhCategory = new SbMultiHandler<CATEGORY>(cCategory,this);
	}
	
	protected void postConstructMailQueue(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage, JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS,RETENTION,FRC> fMail, final Class<L> cLang, final Class<D> cDescription, Class<CATEGORY> cCategory, Class<MAIL> cMail, Class<STATUS> cStatus)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fMail=fMail;
		
		this.cMail=cMail;
		this.cCategory=cCategory;
		this.cStatus=cStatus;
		
		categories = fMail.allOrderedPositionVisible(cCategory);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cCategory,categories));}

		try
		{
			sbhStatus = new SbMultiHandler<STATUS>(cStatus,fMail.allOrderedPositionVisible(cStatus),this);
			sbhStatus.select(fMail.fByCode(cStatus,JeeslMailStatus.Status.queue));
			sbhStatus.select(fMail.fByCode(cStatus,JeeslMailStatus.Status.spooling));
		}
		catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
		initPageConfiguration();
		reloadMails();
	}
	
	protected void initPageConfiguration()
	{
		sbhCategory.setList(categories);
		sbhCategory.selectAll();
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
		DateTime dt = new DateTime(sbhDate.getDate2());
		
		mails = fMail.fMails(sbhCategory.getSelected(),sbhStatus.getSelected(),sbhDate.getDate1(),dt.plusDays(1).toDate(),null);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cMail,mails));}
//		Collections.sort(templates, comparatorTemplate);
	}
		
	public void selectMail()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(mail));}
		
	}
}