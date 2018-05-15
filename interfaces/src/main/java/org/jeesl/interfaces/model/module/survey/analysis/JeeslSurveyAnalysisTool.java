package org.jeesl.interfaces.model.module.survey.analysis;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.domain.JeeslDomainQuery;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyAnalysisTool<L extends UtilsLang, D extends UtilsDescription,
											QE extends UtilsStatus<QE,L,D>,
											QUERY extends JeeslDomainQuery<L,D,?,?>,
											DATTRIBUTE extends JeeslRevisionAttribute<L,D,?,?,?>,
											AQ extends JeeslSurveyAnalysisQuestion<L,D,?,?>,
											ATT extends UtilsStatus<ATT,L,D>>
			extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
						EjbWithParentAttributeResolver,EjbWithPositionVisible
{
	public enum Attributes{analysisQuestion}
	public enum Elements{selectOne,bool,text,remark}
	
	AQ getAnalysisQuestion();
	void setAnalysisQuestion(AQ analysisQuestion);
	
	ATT getType();
	void setType(ATT type);
	
	QE getElement();
	void setElement(QE element);
	
	DATTRIBUTE getAttribute();
	void setAttribute(DATTRIBUTE attribute);
	
	QUERY getQuery();
	void setQuery(QUERY query);
	
	Integer getCacheExpire();
	void setCacheExpire(Integer cacheExpire);
}