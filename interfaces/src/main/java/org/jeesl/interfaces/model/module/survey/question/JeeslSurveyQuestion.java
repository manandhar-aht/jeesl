package org.jeesl.interfaces.model.module.survey.question;

import java.util.List;
import java.util.Map;

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
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithRemark;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslSurveyQuestion<L extends UtilsLang, D extends UtilsDescription,
					SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					SS extends UtilsStatus<SS,L,D>,
					SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX>,
					TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,?>,
					VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX>,
					TS extends UtilsStatus<TS,L,D>,
					TC extends UtilsStatus<TC,L,D>,
					SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,AQ>,
					QE extends UtilsStatus<QE,L,D>,
					SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,VERSION,SECTION,QUESTION,ANSWER,MATRIX,DATA,OPTIONS,OPTION>,
					OPTION extends JeeslSurveyOption<L,D>,
					CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					AQ extends JeeslSurveyAnalysisQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,?,?,?,?,AQ,?,?>
					
					>
			extends EjbWithId,EjbWithCode,EjbWithRemark,EjbWithPosition,EjbWithVisible,EjbSaveable,EjbRemoveable,
					EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{section,visible,position,optionSet}
	
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
	
	Boolean getShowMatrix();
	void setShowMatrix(Boolean showMatrix);
	
	List<SCORE> getScores();
	void setScores(List<SCORE> scores);
}