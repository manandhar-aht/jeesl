package org.jeesl.interfaces.model.module.survey.correlation;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyDomainQuery<L extends UtilsLang, D extends UtilsDescription,
										DOMAIN extends JeeslSurveyDomain<L,D,?>
										>
			extends Serializable,EjbWithId,
					EjbSaveable,
					EjbWithPositionParent
{
	public enum Attributes{domain}
	
	DOMAIN getDomain();
	void setDomain(DOMAIN domain);
}