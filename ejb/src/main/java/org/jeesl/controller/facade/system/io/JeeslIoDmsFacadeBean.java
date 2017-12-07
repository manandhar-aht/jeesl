package org.jeesl.controller.facade.system.io;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.io.JeeslIoDmsFacade;
import org.jeesl.factory.builder.io.IoDmsFactoryBuilder;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class JeeslIoDmsFacadeBean<L extends UtilsLang,D extends UtilsDescription,
								DMS extends JeeslIoDms<L,D,AS,S>,
								AS extends JeeslAttributeSet<L,D,?,?>,
								S extends JeeslIoDmsSection<L,S>>
					extends UtilsFacadeBean
					implements JeeslIoDmsFacade<L,D,DMS,AS,S>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslIoDmsFacadeBean.class);
	
	private final IoDmsFactoryBuilder<L,D,DMS,AS,S> fbDms;
	
	public JeeslIoDmsFacadeBean(EntityManager em, final IoDmsFactoryBuilder<L,D,DMS,AS,S> fbDms)
	{
		super(em);
		this.fbDms=fbDms;
	}
	
	@Override public S load(S section, boolean recursive)
	{
		section = em.find(fbDms.getClassSection(), section.getId());
		if(recursive)
		{
			for(S s : section.getSections())
			{
				s = this.load(s,recursive);
			}
		}
		return section;
	}
}