package org.jeesl.api.bean;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface JeeslCmsCacheBean <S extends JeeslIoCmsSection<?,S>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslCmsCacheBean.class);
	
	void clearCache(S section);
}