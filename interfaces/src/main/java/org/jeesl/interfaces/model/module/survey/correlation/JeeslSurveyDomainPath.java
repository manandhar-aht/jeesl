package org.jeesl.interfaces.model.module.survey.correlation;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyDomainPath<L extends UtilsLang, D extends UtilsDescription,
										QUERY extends JeeslSurveyDomainQuery<L,D,?>,
										DENTITY extends JeeslRevisionEntity<L,D,?,?,?>,
										DATTRIBUTE extends JeeslRevisionAttribute<L,D,?,?,?>
										>
			extends Serializable,EjbWithId,
					EjbSaveable,
					EjbWithPositionParent
{
	public enum Attributes{query}
	
	QUERY getQuery();
	void setQuery(QUERY query);
	
	DENTITY getEntity();
	void setEntity(DENTITY entity);
	
	DATTRIBUTE getAttribute();
	void setAttribute(DATTRIBUTE attribute);
}