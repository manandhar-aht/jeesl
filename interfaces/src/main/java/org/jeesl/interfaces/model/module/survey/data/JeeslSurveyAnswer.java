package org.jeesl.interfaces.model.module.survey.data;

import java.util.List;

import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyAnswer<L extends UtilsLang, D extends UtilsDescription,
							QUESTION extends JeeslSurveyQuestion<L,D,?,?,?,?,?,OPTION,?>,
							MATRIX extends JeeslSurveyMatrix<L,D,?,OPTION>,
							DATA extends JeeslSurveyData<L,D,?,?,?,?,?,?,?,?,QUESTION,?,?,?,?,MATRIX,DATA,?,OPTION,?>,
							OPTION extends JeeslSurveyOption<L,D>
							>
			extends EjbWithId,EjbSaveable
{
	public enum Attributes{data,question,option,valueBoolean}
	
	DATA getData();
	void setData(DATA data);
	
	QUESTION getQuestion();
	void setQuestion(QUESTION question);
	
	Boolean getValueBoolean();
	void setValueBoolean(Boolean valueBoolean);
	
	Double getValueDouble();
	void setValueDouble(Double valueDouble);
	
	Integer getValueNumber();
	void setValueNumber(Integer valueNumber);
	
	String getValueText();
	void setValueText(String valueText);
	
	List<OPTION> getOptions();
	void setOptions(List<OPTION> option);
	
	Double getScore();
	void setScore(Double score);
	
	String getRemark();
	void setRemark(String remark);
	
	OPTION getOption();
	void setOption(OPTION option);
	
	List<MATRIX> getMatrix();
	void setMatrix(List<MATRIX> matrix);
}