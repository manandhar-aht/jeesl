package org.jeesl.factory.xml.module.ts;

import java.util.Date;

import org.jeesl.model.xml.module.ts.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.DateUtil;

public class XmlDataFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlDataFactory.class);
		
	public static Data build(long id){return build(id,null,null);}
	
	public static Data build(Date record, Double value){return build(null,record,value);}
	
	public static Data build(Long id, Date record, Double value)
	{
		Data xml = new Data();
		if(id!=null){xml.setId(id);}
		if(record!=null){xml.setRecord(DateUtil.toXmlGc(record));}
		if(value!=null){xml.setValue(value);}
		return xml;
	}
}