package org.jeesl.factory.ejb.module.survey;

import java.util.List;

import org.jeesl.interfaces.model.system.io.domain.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.system.io.domain.JeeslSurveyDomainQuery;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbSurveyDomainPathFactory<L extends UtilsLang, D extends UtilsDescription,
										QUERY extends JeeslSurveyDomainQuery<L,D,?,PATH>,
										PATH extends JeeslSurveyDomainPath<L,D,QUERY,DENTITY,DATTRIBUTE>,
										DENTITY extends JeeslRevisionEntity<L,D,?,?,?>,
										DATTRIBUTE extends JeeslRevisionAttribute<L,D,DENTITY,?,?>
										>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyDomainPathFactory.class);
	
	private final Class<PATH> cPath;
    
	public EjbSurveyDomainPathFactory(final Class<PATH> cPath)
	{       
        this.cPath = cPath;
	}
    
	public PATH build(QUERY query, DENTITY entity, List<PATH> list)
	{
		PATH ejb = null;
		try
		{
			ejb = cPath.newInstance();
			ejb.setQuery(query);
			ejb.setEntity(entity);
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}