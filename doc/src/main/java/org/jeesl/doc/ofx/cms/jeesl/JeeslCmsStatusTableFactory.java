package org.jeesl.doc.ofx.cms.jeesl;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.openfuxml.content.table.Table;
import org.openfuxml.factory.xml.table.XmlTableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslCmsStatusTableFactory<E extends JeeslIoCmsElement<?,?,?,?,C,?>,
										C extends JeeslIoCmsContent<?,E,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslCmsStatusTableFactory.class);
	
	public JeeslCmsStatusTableFactory()
	{

	}
	
	public Table build(String localeCode, E element)
	{
		logger.info("Building Paragraph ");
		Table table = XmlTableFactory.build();
		
		return table;
	}
}