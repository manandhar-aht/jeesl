package org.jeesl.interfaces.model.module.survey.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyAnswer<L extends UtilsLang, D extends UtilsDescription,
									QUESTION extends JeeslSurveyQuestion<L,D,?,?,?,?,?,?,OPTION,?>,
									MATRIX extends JeeslSurveyMatrix<L,D,?,OPTION>,
									DATA extends JeeslSurveyData<L,D,?,?,?>,
									OPTION extends JeeslSurveyOption<L,D>>
			extends Serializable,EjbWithId,EjbSaveable
{
	public enum Attributes{data,question,option,valueBoolean}
	
	DATA getData();
	void setData(DATA data);
	
	QUESTION getQuestion();
	void setQuestion(QUESTION question);
	
	Boolean getValueBoolean();
	void setValueBoolean(Boolean valueBoolean);
	
	Date getValueDate();
	void setValueDate(Date getValueDate);
	
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
	
	Double getScoreBonus();
	void setScoreBonus(Double scoreBonus);
	
	String getRemark();
	void setRemark(String remark);
	
	OPTION getOption();
	void setOption(OPTION option);
	
	List<MATRIX> getMatrix();
	void setMatrix(List<MATRIX> matrix);
}