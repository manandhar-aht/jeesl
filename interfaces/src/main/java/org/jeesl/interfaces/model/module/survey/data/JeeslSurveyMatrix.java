package org.jeesl.interfaces.model.module.survey.data;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyMatrix<L extends UtilsLang, D extends UtilsDescription,
									ANSWER extends JeeslSurveyAnswer<L,D,?,?,?,OPTION>,
									OPTION extends JeeslSurveyOption<L,D>>
			extends EjbWithId,EjbSaveable
{
	public enum Attributes{answer}
	
	ANSWER getAnswer();
	void setAnswer(ANSWER answer);
	
	OPTION getRow();
	void setRow(OPTION row);
	
	OPTION getColumn();
	void setColumn(OPTION column);
	
	OPTION getOption();
	void setOption(OPTION column);
	
	Boolean getValueBoolean();
	void setValueBoolean(Boolean valueBoolean);
	
	Double getValueDouble();
	void setValueDouble(Double valueDouble);
	
	Integer getValueNumber();
	void setValueNumber(Integer valueNumber);
	
	String getValueText();
	void setValueText(String valueText);
}