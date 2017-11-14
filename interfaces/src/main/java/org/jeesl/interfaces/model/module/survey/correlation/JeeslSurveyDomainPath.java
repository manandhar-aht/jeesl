package org.jeesl.interfaces.model.module.survey.correlation;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyDomainPath<L extends UtilsLang, D extends UtilsDescription,
										DOMAIN extends JeeslSurveyDomain<L,D,PATH,DENTITY>,
										PATH extends JeeslSurveyDomainPath<L,D,DOMAIN,PATH,DENTITY>,
										DENTITY extends JeeslRevisionEntity<L,D,?,?,?>
										>
			extends Serializable,EjbWithId,EjbSaveable
{

}