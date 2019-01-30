package org.jeesl.interfaces.model.module.survey.question;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.EjbWithRendered;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithRemark;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslSurveyQuestion<L extends UtilsLang, D extends UtilsDescription,
										SECTION extends JeeslSurveySection<L,D,?,SECTION,?>,
										CONDITION extends JeeslSurveyCondition<?,QE,OPTION>,
										VALIDATION extends JeeslSurveyValidation<?,?>,
										QE extends UtilsStatus<QE,L,D>,
										SCORE extends JeeslSurveyScore<L,D,?,?>,
										UNIT extends UtilsStatus<UNIT,L,D>,
										OPTIONS extends JeeslSurveyOptionSet<L,D,?,OPTION>,
										OPTION extends JeeslSurveyOption<L,D>,
										AQ extends JeeslSurveyAnalysisQuestion<L,D,?,?>
										>
			extends Serializable,EjbWithCode,EjbWithRemark,EjbWithPosition,EjbWithVisible,EjbSaveable,EjbRemoveable,
					EjbWithRendered,EjbWithParentAttributeResolver,
					EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{section,visible,position,optionSet}
	
	public enum Elements{selectOne};
	public enum AttributeTypeCode{text,number,bool};
	
	SECTION getSection();
	void setSection(SECTION section);
	
	String getTopic();
	void setTopic(String topic);
	
	public Map<String,D> getText();
	public void setText(Map<String,D> name);
	
	String getQuestion();
	void setQuestion(String question);
	
	UNIT getUnit();
	void setUnit(UNIT unit); 
	
	OPTIONS getOptionSet();
	void setOptionSet(OPTIONS optionSet);
	
	List<OPTION> getOptions();
	void setOptions(List<OPTION> options);
	
	Boolean getCalculateScore();
	void setCalculateScore(Boolean calculateScore);
	
	Double getMinScore();
	void setMinScore(Double maxScore);
	
	Double getMaxScore();
	void setMaxScore(Double maxScore);
	
	Boolean getBonusScore();
	void setBonusScore(Boolean bonusScore);
	
	Boolean getShowBoolean();
	void setShowBoolean(Boolean showBoolean);
	
	Boolean getShowDate();
	void setShowDate(Boolean showDate);
	
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
	
	Boolean getShowMatrix();
	void setShowMatrix(Boolean showMatrix);
	
	List<SCORE> getScores();
	void setScores(List<SCORE> scores);
	
	String getRenderCondition();
	void setRenderCondition(String renderCondition);
	
	List<CONDITION> getConditions();
	void setConditions(List<CONDITION> conditions);
	
	Boolean getMandatory();
	void setMandatory(Boolean mandatory);
	
	Boolean getShowEmptyOption();
	void setShowEmptyOption(Boolean showEmptyOption);
}