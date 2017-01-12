package org.jeesl.web.rest.system;

import org.jeesl.factory.xml.jeesl.XmlContainerFactory;
import org.jeesl.interfaces.facade.JeeslIoMailFacade;
import org.jeesl.interfaces.model.system.io.mail.JeeslIoMail;
import org.jeesl.interfaces.rest.system.mail.JeeslIoMailRestExport;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.util.query.xml.StatusQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class IoMailRestService <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL>>
					implements JeeslIoMailRestExport
{
	final static Logger logger = LoggerFactory.getLogger(IoMailRestService.class);
	
	private JeeslIoMailFacade<L,D,CATEGORY,MAIL> fMail;
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<CATEGORY> cCategory;
	private final Class<MAIL> cMail;

	private XmlContainerFactory xfContainer;
	
//	private EjbLangFactory<L> efLang;
//	private EjbDescriptionFactory<D> efDescription;
	
	private IoMailRestService(JeeslIoMailFacade<L,D,CATEGORY,MAIL> fTemplate,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<MAIL> cMail)
	{
		this.cL=cL;
		this.cD=cD;
		this.cCategory=cCategory;
		this.cMail=cMail;
	
		xfContainer = new XmlContainerFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL>>
		IoMailRestService<L,D,CATEGORY,MAIL>
			factory(JeeslIoMailFacade<L,D,CATEGORY,MAIL> fMail,final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<MAIL> cMail)
	{
		return new IoMailRestService<L,D,CATEGORY,MAIL>(fMail,cL,cD,cCategory,cMail);
	}
	
	@Override public Container exportSystemIoMailCategories() {return xfContainer.build(fMail.allOrderedPosition(cCategory));}
}