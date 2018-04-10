package org.jeesl.doc.ofx.cms;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.jsoup.Jsoup;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.transform.XhtmlTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class OfxParagraphFactory<L extends UtilsLang,D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								CMS extends JeeslIoCms<L,D,CAT,S,LOC>,
								V extends JeeslIoCmsVisiblity,
								S extends JeeslIoCmsSection<L,S>,
								E extends JeeslIoCmsElement<V,S,EC,ET,C>,
								EC extends UtilsStatus<EC,L,D>,
								ET extends UtilsStatus<ET,L,D>,
								C extends JeeslIoCmsContent<V,E,MT>,
								MT extends UtilsStatus<MT,L,D>,
								LOC extends UtilsStatus<LOC,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(OfxParagraphFactory.class);
	
	private final String localeCode;
	private XhtmlTransformer xhtmlTransformer;
	
	public OfxParagraphFactory(String localeCode)
	{
		this.localeCode=localeCode;
		xhtmlTransformer = new XhtmlTransformer();
	}
	
	public Section build(E element)
	{
		logger.info("Building Paragraph ");
		Section section = XmlSectionFactory.build(localeCode);
		
		
		
		if(element.getContent().containsKey(localeCode))
		{
			C c = element.getContent().get(localeCode);
			
			if(c.getMarkup().getCode().equals(JeeslIoCmsMarkupType.Code.text.toString()))
			{
				Paragraph p = XmlParagraphFactory.build(localeCode);
				String conent = element.getContent().get(localeCode).getLang();
				logger.info("\t"+conent);
				conent = Jsoup.parse(conent).text();
				logger.info("\t"+conent);
				p.getContent().add(conent);
				section.getContent().add(p);
			}
			else if(c.getMarkup().getCode().equals(JeeslIoCmsMarkupType.Code.xhtml.toString()))
			{
				Section xml = xhtmlTransformer.process(element.getContent().get(localeCode).getLang());
				section.getContent().addAll(xml.getContent());
				
			}
			else {logger.warn("Unhandled markup Type: "+c.getMarkup().getCode());}
			
		}
		return section;
	}
}