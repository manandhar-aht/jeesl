package org.jeesl.controller.facade.system;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class JeeslIoCmsFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									CMS extends JeeslIoCms<L,D,CMS,V>,
									V extends JeeslIoCmsVisiblity<L,D,CMS,V>
									>
					extends UtilsFacadeBean
					implements JeeslIoCmsFacade<L,D,CMS,V>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslIoCmsFacadeBean.class);
		
	
	public JeeslIoCmsFacadeBean(EntityManager em)
	{
		super(em);
	}
}