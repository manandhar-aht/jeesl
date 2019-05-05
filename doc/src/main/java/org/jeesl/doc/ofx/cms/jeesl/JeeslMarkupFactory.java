package org.jeesl.doc.ofx.cms.jeesl;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jsoup.Jsoup;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.transform.XhtmlTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class JeeslMarkupFactory
{
	final static Logger logger = LoggerFactory.getLogger(JeeslMarkupFactory.class);
	
	private boolean debug = true;
	
	private final XhtmlTransformer xhtmlTransformer;

	public JeeslMarkupFactory()
	{
		xhtmlTransformer = new XhtmlTransformer();
	}
	
	public Section build(String type, String content)
	{
		if(debug)
		{
			logger.info("Building Markup");
			logger.info(content);
		}
		
		
		Section section = XmlSectionFactory.build();

		if(type.equals(JeeslIoCmsMarkupType.Code.text.toString()))
		{
			Paragraph p = XmlParagraphFactory.build();
			content = Jsoup.parse(content).text();
			p.getContent().add(content);
			section.getContent().add(p);
		}
		else if(type.equals(JeeslIoCmsMarkupType.Code.xhtml.toString()))
		{
			Section xml = xhtmlTransformer.process(content);
			if(debug)
			{
				JaxbUtil.info(xml);
			}
			section.getContent().addAll(xml.getContent());
		}
		else {logger.warn("Unhandled markup Type: "+type);}
		return section;
	}
}