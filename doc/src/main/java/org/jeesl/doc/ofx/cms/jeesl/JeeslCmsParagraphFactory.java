package org.jeesl.doc.ofx.cms.jeesl;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jsoup.Jsoup;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.transform.XhtmlTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslCmsParagraphFactory<E extends JeeslIoCmsElement<?,?,?,?,C>,
								C extends JeeslIoCmsContent<?,E,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslCmsParagraphFactory.class);
	
	private final String localeCode;
	private XhtmlTransformer xhtmlTransformer;
	
	public JeeslCmsParagraphFactory(String localeCode)
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