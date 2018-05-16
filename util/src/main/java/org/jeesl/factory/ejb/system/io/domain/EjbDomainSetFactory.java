package org.jeesl.factory.ejb.system.io.domain;

import java.util.List;

import org.jeesl.interfaces.model.system.io.domain.JeeslDomain;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbDomainSetFactory<L extends UtilsLang, D extends UtilsDescription,	
				DOMAIN extends JeeslDomain<L,?>,
				SET extends JeeslDomainSet<L,D,DOMAIN>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbDomainSetFactory.class);
	
	private final Class<SET> cSet;
    
	public EjbDomainSetFactory(final Class<SET> cSet)
	{       
        this.cSet = cSet;
	}
    
	public SET build(DOMAIN domain, List<SET> list)
	{
		SET ejb = null;
		try
		{
			ejb = cSet.newInstance();
			ejb.setDomain(domain);
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}