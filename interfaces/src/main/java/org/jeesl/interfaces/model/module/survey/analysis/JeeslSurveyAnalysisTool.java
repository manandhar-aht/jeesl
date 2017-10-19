package org.jeesl.interfaces.model.module.survey.analysis;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyAnalysisTool<L extends UtilsLang, D extends UtilsDescription,
					QE extends UtilsStatus<QE,L,D>,
					AQ extends JeeslSurveyAnalysisQuestion<L,D,?,?>,
					ATT extends UtilsStatus<ATT,L,D>>
			extends EjbWithId,EjbWithParentAttributeResolver,EjbSaveable,
					EjbWithPositionVisible
{
	public enum Attributes{analysisQuestion}
	public enum Elements{selectOne,bool,text,remark}
	
	AQ getAnalysisQuestion();
	void setAnalysisQuestion(AQ analysisQuestion);
	
	ATT getType();
	void setType(ATT type);
	
	QE getElement();
	void setElement(QE element);
}