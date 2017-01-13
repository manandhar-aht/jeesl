package org.jeesl.web.rest.system;

import org.jeesl.interfaces.facade.JeeslIoMailFacade;
import org.jeesl.interfaces.model.system.io.mail.JeeslIoMail;
import org.jeesl.interfaces.rest.system.io.mail.JeeslIoMailRestExport;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class IoMailRestService <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL,STATUS>, STATUS extends UtilsStatus<STATUS,L,D>>
					extends AbstractJeeslRestService<L,D>
					implements JeeslIoMailRestExport
{
	final static Logger logger = LoggerFactory.getLogger(IoMailRestService.class);
	
	private JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS> fMail;
	
	private final Class<CATEGORY> cCategory;
	private final Class<MAIL> cMail;
	private final Class<STATUS> cStatus;
	
//	private EjbLangFactory<L> efLang;
//	private EjbDescriptionFactory<D> efDescription;
	
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
}