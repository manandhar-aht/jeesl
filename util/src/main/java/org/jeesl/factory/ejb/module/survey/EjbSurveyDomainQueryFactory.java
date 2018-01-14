package org.jeesl.factory.ejb.module.survey;

import java.util.List;

import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbSurveyDomainQueryFactory<L extends UtilsLang, D extends UtilsDescription,
										DOMAIN extends JeeslSurveyDomain<L,?>,
										QUERY extends JeeslSurveyDomainQuery<L,D,DOMAIN,?>
										>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyDomainQueryFactory.class);
	
	private final Class<QUERY> cQuery;
    
	public EjbSurveyDomainQueryFactory(final Class<QUERY> cQuery)
	{       
        this.cQuery = cQuery;
	}
    
	public QUERY build(DOMAIN domain, List<QUERY> list)
	{
		QUERY ejb = null;
		try
		{
			ejb = cQuery.newInstance();
			ejb.setDomain(domain);
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}