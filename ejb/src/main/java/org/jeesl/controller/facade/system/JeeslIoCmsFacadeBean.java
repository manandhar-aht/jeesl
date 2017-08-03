package org.jeesl.controller.facade.system;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslIoCmsFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									CAT extends UtilsStatus<CAT,L,D>,
									CMS extends JeeslIoCms<L,D,CAT,CMS,V,S,E,T,C,M>,
									V extends JeeslIoCmsVisiblity<L,D,CAT,CMS,V,S,E,T,C,M>,
									S extends JeeslIoCmsSection<L,D,CAT,CMS,V,S,E,T,C,M>,
									E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,E,T,C,M>,
									T extends UtilsStatus<T,L,D>,
									C extends JeeslIoCmsContent<L,D,CAT,CMS,V,S,E,T,C,M>,
									M extends UtilsStatus<M,L,D>>
					extends UtilsFacadeBean
					implements JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,T,C,M>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslIoCmsFacadeBean.class);
	
	private final Class<S> cSection;
	
	public JeeslIoCmsFacadeBean(EntityManager em,final Class<S> cSection)
	{
		super(em);
		this.cSection=cSection;
	}
	
	@Override public S load(S section, boolean recursive)
	{
		section = em.find(cSection, section.getId());
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