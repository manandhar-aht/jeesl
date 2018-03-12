package org.jeesl.factory.xml.dev.qa;

import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.qa.UtilsQaTestInfo;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.qa.Comment;
import net.sf.ahtutils.xml.qa.Info;

public class XmlInfoFactory<L extends UtilsLang, D extends UtilsDescription,
							QATI extends UtilsQaTestInfo<QATC>,
							QATC extends UtilsStatus<QATC,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlInfoFactory.class);
		
	private Info q;
	private XmlStatusFactory<QATC,L,D> xfCondition;
	
	public XmlInfoFactory(Info q)
	{
		this.q=q;
		if(q.isSetStatus()){xfCondition = new XmlStatusFactory<QATC,L,D>(null,q.getStatus());}
	}
	
	public Info build(QATI info)
	{
		Info xml = new Info();
	
		if(q.isSetStatus()){xml.setStatus(xfCondition.build(info.getCondition()));}
		
		if(q.isSetComment())
		{
			xml.setComment(new Comment());
			xml.getComment().setValue(info.getDescription());
			if(xml.getComment().getValue()==null){xml.getComment().setValue("");}
		}
		
		return xml;
	}
}