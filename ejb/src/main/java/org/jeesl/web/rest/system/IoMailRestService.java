package org.jeesl.web.rest.system;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jeesl.api.facade.io.JeeslIoMailFacade;
import org.jeesl.api.rest.system.io.mail.JeeslIoMailRestExport;
import org.jeesl.api.rest.system.io.mail.JeeslIoMailRestImport;
import org.jeesl.api.rest.system.io.mail.JeeslIoMailRestSpooler;
import org.jeesl.factory.xml.mail.XmlMailFactory;
import org.jeesl.factory.xml.system.io.mail.XmlMailsFactory;
import org.jeesl.interfaces.model.system.io.mail.JeeslIoMail;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.model.xml.system.io.mail.Mail;
import org.jeesl.model.xml.system.io.mail.Mails;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.exlp.util.xml.JaxbUtil;

public class IoMailRestService <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL,STATUS>, STATUS extends UtilsStatus<STATUS,L,D>>
					extends AbstractJeeslRestService<L,D>
					implements JeeslIoMailRestExport,JeeslIoMailRestImport,JeeslIoMailRestSpooler
{
	final static Logger logger = LoggerFactory.getLogger(IoMailRestService.class);
	
	private JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS> fMail;
	
	private final Class<CATEGORY> cCategory;
	private final Class<MAIL> cMail;
	private final Class<STATUS> cStatus;
	
	private IoMailRestService(JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS> fMail,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<MAIL> cMail, final Class<STATUS> cStatus)
	{
		super(fMail,cL,cD);
		this.fMail=fMail;
		this.cCategory=cCategory;
		this.cMail=cMail;
		this.cStatus=cStatus;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL,STATUS>, STATUS extends UtilsStatus<STATUS,L,D>>
		IoMailRestService<L,D,CATEGORY,MAIL,STATUS>
		factory(JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS> fMail,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<MAIL> cMail, final Class<STATUS> cStatus)
	{
		return new IoMailRestService<L,D,CATEGORY,MAIL,STATUS>(fMail,cL,cD,cCategory,cMail,cStatus);
	}
	
	@Override public Container exportSystemIoMailCategories() {return xfContainer.build(fMail.allOrderedPosition(cCategory));}
	@Override public Container exportSystemIoMailStatus() {return xfContainer.build(fMail.allOrderedPosition(cStatus));}
	
	@Override public DataUpdate importSystemIoMailCategories(Container categories){return importStatus(cCategory,categories,null);}
	@Override public DataUpdate importSystemIoMailStatus(Container categories){return importStatus(cStatus,categories,null);}

	@Override public Mails spool()
	{
		List<MAIL> eMails = fMail.fSpoolMails(1);
		Mails xml = XmlMailsFactory.build();
		xml.setQueue(fMail.cQueue());

		for(MAIL eMail : eMails)
		{
			try
			{
				eMail = fMail.find(cMail,eMail);
				eMail.setRecordSpool(new Date());
				eMail.setCounter(eMail.getCounter()+1);
				if(eMail.getCounter()>5)
				{
					eMail.setStatus(fMail.fByCode(cStatus, JeeslIoMail.Status.failed));
					eMail = fMail.update(eMail);
				}
				else
				{
					eMail.setStatus(fMail.fByCode(cStatus, JeeslIoMail.Status.spooling));
					eMail = fMail.update(eMail);
					Mail xMail = JaxbUtil.loadJAXB(IOUtils.toInputStream(eMail.getXml(), "UTF-8"), Mail.class);
					xMail.setId(eMail.getId());
					xml.getMail().add(xMail);
				}
			}
			catch (UtilsConstraintViolationException e) {e.printStackTrace();}
			catch (UtilsLockingException e) {logger.warn(e.getMessage());}
			catch (IOException e) {logger.error(e.getMessage());}
			catch (UtilsNotFoundException e) {logger.error(e.getMessage());}
		}		
		return xml;
	}

	@Override
	public Mail confirm(long id)
	{
		Mail xMail = XmlMailFactory.build("pending");
		try
		{
			MAIL eMail = fMail.find(cMail,id);
			eMail.setStatus(fMail.fByCode(cStatus, JeeslIoMail.Status.sent));
			eMail.setRecordSent(new Date());
			eMail = fMail.update(eMail);
			xMail.setId(eMail.getId());
			xMail.setCode("confirmed");
		}
		catch (UtilsNotFoundException e) {xMail.setCode("error");}
		catch (UtilsConstraintViolationException e) {xMail.setCode("error");}
		catch (UtilsLockingException e) {xMail.setCode("error");}
		return xMail;
	}
}