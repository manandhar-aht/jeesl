package org.jeesl.interfaces.model.survey;

import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithRemark;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslSurveyQuestion<L extends UtilsLang,
							D extends UtilsDescription,
							SURVEY extends JeeslSurvey<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							SS extends UtilsStatus<SS,L,D>,
							TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							TS extends UtilsStatus<TS,L,D>,
							TC extends UtilsStatus<TC,L,D>,
							SECTION extends JeeslSurveySection<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							DATA extends JeeslSurveyData<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
							CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
			extends EjbWithId,EjbWithCode,EjbWithRemark,EjbWithPosition,EjbWithVisible,EjbSaveable,EjbRemoveable
{
	SECTION getSection();
	void setSection(SECTION section);
	
	String getTopic();
	void setTopic(String topic);
	
	String getQuestion();
	void setQuestion(String question);
	
	UNIT getUnit();
	void setUnit(UNIT unit); 
	
	List<OPTION> getOptions();
	void setOptions(List<OPTION> options);
	
	Boolean getCalculateScore();
	void setCalculateScore(Boolean calculateScore);
	
	Double getMaxScore();
	void setMaxScore(Double maxScore);
	
	Double getMinScore();
	void setMinScore(Double maxScore);
	
	
	Boolean getShowBoolean();
	void setShowBoolean(Boolean showBoolean);
	
	Boolean getShowInteger();
	void setShowInteger(Boolean showInteger);
	
	Boolean getShowDouble();
	void setShowDouble(Boolean showDouble);
	
	Boolean getShowText();
	void setShowText(Boolean showText);
	
	Boolean getShowScore();
	void setShowScore(Boolean showScore);
	
	Boolean getShowRemark();
	void setShowRemark(Boolean showRemark);
	
	Boolean getShowSelectOne();
	void setShowSelectOne(Boolean showSelectOne);
	
	Boolean getShowSelectMulti();
	void setShowSelectMulti(Boolean showSelectMulti);
}