package org.jeesl.web.rest.system.io;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jeesl.api.facade.io.JeeslIoMailFacade;
import org.jeesl.api.rest.system.io.mail.JeeslIoMailRestExport;
import org.jeesl.api.rest.system.io.mail.JeeslIoMailRestImport;
import org.jeesl.api.rest.system.io.mail.JeeslIoMailRestInterface;
import org.jeesl.factory.xml.system.io.mail.XmlMailFactory;
import org.jeesl.factory.xml.system.io.mail.XmlMailsFactory;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslIoMail;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslMailRetention;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslMailStatus;
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
								MAIL extends JeeslIoMail<L,D,CATEGORY,STATUS,RETENTION,FRC>,
								STATUS extends JeeslMailStatus<L,D,STATUS,?>,
								RETENTION extends JeeslMailRetention<L,D,RETENTION,?>,
								FRC extends JeeslFileContainer<?,?>>
					extends AbstractJeeslRestService<L,D>
					implements JeeslIoMailRestInterface,JeeslIoMailRestExport,JeeslIoMailRestImport
{
	final static Logger logger = LoggerFactory.getLogger(IoMailRestService.class);
	
	private JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS,RETENTION,FRC> fMail;
	
	private final Class<CATEGORY> cCategory;
	private final Class<MAIL> cMail;
	private final Class<STATUS> cStatus;
	private final Class<RETENTION> cRetention;
	
	private IoMailRestService(JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS,RETENTION,FRC> fMail,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<MAIL> cMail, final Class<STATUS> cStatus, final Class<RETENTION> cRetention)
	{
		super(fMail,cL,cD);
		this.fMail=fMail;
		this.cCategory=cCategory;
		this.cMail=cMail;
		this.cStatus=cStatus;
		this.cRetention=cRetention;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					MAIL extends JeeslIoMail<L,D,CATEGORY,STATUS,RETENTION,FRC>,
					STATUS extends JeeslMailStatus<L,D,STATUS,?>,
					RETENTION extends JeeslMailRetention<L,D,RETENTION,?>,
					FRC extends JeeslFileContainer<?,?>>
		IoMailRestService<L,D,CATEGORY,MAIL,STATUS,RETENTION,FRC>
		factory(JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS,RETENTION,FRC> fMail,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<MAIL> cMail, final Class<STATUS> cStatus, final Class<RETENTION> cRetention)
	{
		return new IoMailRestService<L,D,CATEGORY,MAIL,STATUS,RETENTION,FRC>(fMail,cL,cD,cCategory,cMail,cStatus,cRetention);
	}
	
	@Override public Container exportSystemIoMailCategories() {return xfContainer.build(fMail.allOrderedPosition(cCategory));}
	@Override public Container exportSystemIoMailStatus() {return xfContainer.build(fMail.allOrderedPosition(cStatus));}
	@Override public Container exportSystemIoMailRetention() {return xfContainer.build(fMail.allOrderedPosition(cRetention));}
	
	@Override public DataUpdate importSystemIoMailCategories(Container categories){return importStatus(cCategory,categories,null);}
	@Override public DataUpdate importSystemIoMailStatus(Container categories){return importStatus(cStatus,categories,null);}
	@Override public DataUpdate importSystemIoMailRetention(Container categories){return importStatus(cRetention,categories,null);}

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
					eMail.setStatus(fMail.fByCode(cStatus, JeeslMailStatus.Code.failed));
					eMail = fMail.update(eMail);
				}
				else
				{
					eMail.setStatus(fMail.fByCode(cStatus, JeeslMailStatus.Code.spooling));
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
			eMail.setStatus(fMail.fByCode(cStatus, JeeslMailStatus.Code.sent));
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