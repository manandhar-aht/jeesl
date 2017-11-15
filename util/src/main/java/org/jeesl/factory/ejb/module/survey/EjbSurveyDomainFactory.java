package org.jeesl.factory.ejb.module.survey;

import java.util.List;

import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbSurveyDomainFactory<L extends UtilsLang, D extends UtilsDescription,	
				DOMAIN extends JeeslSurveyDomain<L,D,?,DENTITY>,
				DENTITY extends JeeslRevisionEntity<L,D,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyDomainFactory.class);
	
	private final Class<DOMAIN> cDomain;
    
	public EjbSurveyDomainFactory(final Class<DOMAIN> cDomain)
	{       
        this.cDomain = cDomain;
	}
    
	public DOMAIN build(DENTITY entity, List<DOMAIN> list)
	{
		DOMAIN ejb = null;
		try
		{
			ejb = cDomain.newInstance();
//			ejb.setEntity(entity);
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}