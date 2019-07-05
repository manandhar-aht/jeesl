package org.jeesl.controller.facade.system.io;

import java.util.List;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslIoCmsFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									CAT extends UtilsStatus<CAT,L,D>,
									CMS extends JeeslIoCms<L,D,CAT,S,LOC>,
									V extends JeeslIoCmsVisiblity,
									S extends JeeslIoCmsSection<L,S>,
									E extends JeeslIoCmsElement<V,S,EC,ET,C,FC>,
									EC extends UtilsStatus<EC,L,D>,
									ET extends UtilsStatus<ET,L,D>,
									C extends JeeslIoCmsContent<V,E,MT>,
									MT extends JeeslIoCmsMarkupType<L,D,MT,?>,
									FC extends JeeslFileContainer<?,?>,
									LOC extends UtilsStatus<LOC,L,D>>
					extends UtilsFacadeBean
					implements JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,FC,LOC>
{	
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(JeeslIoCmsFacadeBean.class);
	
	private final Class<S> cSection;
	private final Class<E> cElement;
	
	public JeeslIoCmsFacadeBean(EntityManager em, final Class<S> cSection, final Class<E> cElement)
	{
		super(em);
		this.cSection=cSection;
		this.cElement=cElement;
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

	@Override public List<E> fCmsElements(S section) {return this.allForParent(cElement,section);}
}