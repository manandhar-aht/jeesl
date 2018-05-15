package org.jeesl.api.facade.io;

import java.util.List;

import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainQuery;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslIoDomainFacade <L extends UtilsLang, D extends UtilsDescription,
											
											DOMAIN extends JeeslSurveyDomain<L,DENTITY>,
											QUERY extends JeeslSurveyDomainQuery<L,D,DOMAIN,PATH>,
											PATH extends JeeslSurveyDomainPath<L,D,QUERY,DENTITY,DATTRIBUTE>,
											DENTITY extends JeeslRevisionEntity<L,D,?,?,DATTRIBUTE>,
											DATTRIBUTE extends JeeslRevisionAttribute<L,D,DENTITY,?,?>>
	extends UtilsFacade
{
	List<DATTRIBUTE> fDomainAttributes(DENTITY entity);
}