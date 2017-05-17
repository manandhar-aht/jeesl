package org.jeesl.factory.xml.module.inventory.pc;

import java.util.Date;
import org.jeesl.model.xml.module.inventory.pc.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.exlp.util.DateUtil;

public class XmlUpdateFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlUpdateFactory.class);
	
	public static Update build(long id){return build(id,null,null,null);}
	
	public static Update build(String code, Date record, Date description){return build(null,code,record,description);}
	
	public static Update build(Date record, Date description){return build(null,null,record,description);}
	
	public static Update build(Date description){return build(null,null,null,description);}
	
	public static Update build(Long id,String code, Date record, Date description)
	{
		Update xml = new Update();
		if(id!=null){xml.setId(id);}
		if(code!=null){xml.setCode(code);}
		if(record!=null){xml.setRecord(DateUtil.toXmlGc(record));}
		if(description!=null){xml.setDescription(DateUtil.toXmlGc(description));}
		return xml;
	}
}
