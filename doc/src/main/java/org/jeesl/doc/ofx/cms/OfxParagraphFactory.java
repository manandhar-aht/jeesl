package org.jeesl.doc.ofx.cms;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.jsoup.Jsoup;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class OfxParagraphFactory<L extends UtilsLang,D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								CMS extends JeeslIoCms<L,D,CAT,V,S,M,LOC>,
								V extends JeeslIoCmsVisiblity,
								S extends JeeslIoCmsSection<L,S>,
								E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,EC,ET,C,M,LOC>,
								EC extends UtilsStatus<EC,L,D>,
								ET extends UtilsStatus<ET,L,D>,
								C extends JeeslIoCmsContent<L,D,V,S,E,EC,ET,C,M,LOC>,
								M extends UtilsStatus<M,L,D>,
								LOC extends UtilsStatus<LOC,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(OfxParagraphFactory.class);
	
	private final String localeCode;
	
	public OfxParagraphFactory(String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	public Paragraph build(E element)
	{
		logger.info("Building Paragraph ");
		
		
		Paragraph p = XmlParagraphFactory.build(localeCode);
		
		if(element.getContent().containsKey(localeCode))
		{
			String conent = element.getContent().get(localeCode).getLang();
			logger.info("\t"+conent);
			conent = Jsoup.parse(conent).text();
			logger.info("\t"+conent);
			p.getContent().add(conent);
			
		}
		
		return p;
	}
}