package org.jeesl.interfaces.model.module.survey.question;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.EjbWithRendered;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.EjbWithLevel;
import net.sf.ahtutils.interfaces.model.with.EjbWithRemark;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslSurveySection<L extends UtilsLang, D extends UtilsDescription,
									TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,SECTION,?,?>,
									SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
									QUESTION extends JeeslSurveyQuestion<L,D,SECTION,?,?,?,?,?,?>>
			extends Serializable,EjbSaveable,EjbRemoveable
			,EjbWithCode,EjbWithRemark,EjbWithPosition,EjbWithLevel,EjbWithVisible,EjbWithRendered,
					EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{template,visible,position}
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	SECTION getSection();
	void setSection(SECTION section);
	
	List<SECTION> getSections();
	void setSections(List<SECTION> sections);
	
	List<QUESTION> getQuestions();
	void setQuestions(List<QUESTION> questions);
	
	String getColumnClasses();
	void setColumnClasses(String columnClasses);
	
	Double getScoreLimit();
	void setScoreLimit(Double scoreLimit);
	
	Double getScoreNormalize();
	void setScoreNormalize(Double scoreNormalize);
}