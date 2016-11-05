package org.jeesl.factory.xml.jeesl;

import java.util.List;

import org.jeesl.model.xml.jeesl.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Status;

public class XmlContainerFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlContainerFactory.class);
	
	public static Container buildStatusList(List<Status> list)
    {
    	Container xml = build();
    	xml.getStatus().addAll(list);
    	return xml;
    }
	
    public static Container build()
    {
    	Container xml = new Container();
    	return xml;
    }
}