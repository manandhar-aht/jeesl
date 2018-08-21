package org.jeesl.doc.ofx.cms.jeesl;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jsoup.Jsoup;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Table;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.table.XmlTableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslCmsStatusTableFactory<E extends JeeslIoCmsElement<?,?,?,?,C>, C extends JeeslIoCmsContent<?,E,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslCmsStatusTableFactory.class);
	
	private final String localeCode;
	
	public JeeslCmsStatusTableFactory(String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	public Table build(E element)
	{
		logger.info("Building Paragraph ");
		Table table = XmlTableFactory.build();
		
		return table;
	}
}