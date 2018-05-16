package org.jeesl.controller.facade.system.io;

import java.util.List;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.io.JeeslIoDomainFacade;
import org.jeesl.factory.builder.io.IoDomainFactoryBuilder;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomain;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainPath;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainQuery;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainSet;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class JeeslIoDomainFacadeBean <L extends UtilsLang, D extends UtilsDescription, 
				
				DOMAIN extends JeeslDomain<L,DENTITY>,
				QUERY extends JeeslDomainQuery<L,D,DOMAIN,PATH>,
				PATH extends JeeslDomainPath<L,D,QUERY,DENTITY,DATTRIBUTE>,
				DENTITY extends JeeslRevisionEntity<L,D,?,?,DATTRIBUTE>,
				DATTRIBUTE extends JeeslRevisionAttribute<L,D,DENTITY,?,?>,
				SET extends JeeslDomainSet<L,D>>
	extends UtilsFacadeBean implements JeeslIoDomainFacade<L,D,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslIoDomainFacadeBean.class);
		
	private final IoDomainFactoryBuilder<L,D,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,SET> fbDomain;
	
	public JeeslIoDomainFacadeBean(EntityManager em,
			final IoDomainFactoryBuilder<L,D,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,SET> fbDomain)
	{
		super(em);
		this.fbDomain=fbDomain;
	}
	
	@Override
	public List<DATTRIBUTE> fDomainAttributes(DENTITY entity)
	{
		entity = em.find(fbDomain.getClassDomainEntity(), entity.getId());
		entity.getAttributes().size();
		return entity.getAttributes();
	}
}