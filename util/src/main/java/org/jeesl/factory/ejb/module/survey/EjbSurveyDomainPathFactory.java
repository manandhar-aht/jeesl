package org.jeesl.factory.ejb.module.survey;

import java.util.List;

import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbSurveyDomainPathFactory<L extends UtilsLang, D extends UtilsDescription,
										DOMAIN extends JeeslSurveyDomain<L,D,DENTITY>,
										PATH extends JeeslSurveyDomainPath<L,D,?,DENTITY>,
										DENTITY extends JeeslRevisionEntity<L,D,?,?,?>
										>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyDomainPathFactory.class);
	
	private final Class<PATH> cPath;
    
	public EjbSurveyDomainPathFactory(final Class<PATH> cPath)
	{       
        this.cPath = cPath;
	}
    
	public PATH build(DOMAIN domain, List<PATH> list)
	{
		PATH ejb = null;
		try
		{
			ejb = cPath.newInstance();
//			ejb.setDomain(domain);
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}