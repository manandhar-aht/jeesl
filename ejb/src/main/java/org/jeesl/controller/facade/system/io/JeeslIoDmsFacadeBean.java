package org.jeesl.controller.facade.system.io;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.io.JeeslIoDmsFacade;
import org.jeesl.factory.builder.io.IoDmsFactoryBuilder;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsDocument;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsLayer;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsView;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainSet;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslIoDmsFacadeBean<L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
								DMS extends JeeslIoDms<L,D,STORAGE,AS,DS,S>,
								STORAGE extends JeeslFileStorage<L,D,?,?>,
								AS extends JeeslAttributeSet<L,D,?,?>,
								DS extends JeeslDomainSet<L,D,?>,
								S extends JeeslIoDmsSection<L,D,S>,
								F extends JeeslIoDmsDocument<L,S,FC,AC>,
								VIEW extends JeeslIoDmsView<L,D,DMS>,
								LAYER extends JeeslIoDmsLayer<VIEW,AI>,
								FC extends JeeslFileContainer<?,?>,
								AI extends JeeslAttributeItem<?,AS>,
								AC extends JeeslAttributeContainer<?,?>>
					extends UtilsFacadeBean
					implements JeeslIoDmsFacade<L,D,LOC,DMS,STORAGE,AS,DS,S,F,VIEW,FC,AC>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(JeeslIoDmsFacadeBean.class);
	
	private final IoDmsFactoryBuilder<L,D,LOC,DMS,STORAGE,S,F,VIEW,LAYER> fbDms;
	
	public JeeslIoDmsFacadeBean(EntityManager em, final IoDmsFactoryBuilder<L,D,LOC,DMS,STORAGE,S,F,VIEW,LAYER> fbDms)
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