package org.jeesl.factory.json.module.survey;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.model.json.survey.Answer;
import org.jeesl.model.json.survey.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JsonSurveyAnswerFactory<L extends UtilsLang,D extends UtilsDescription,
									SECTION extends JeeslSurveySection<L,D,?,SECTION,QUESTION>,
									QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,?>,
									QE extends UtilsStatus<QE,L,D>,
									SCORE extends JeeslSurveyScore<L,D,?,QUESTION>,
									UNIT extends UtilsStatus<UNIT,L,D>,
									ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
									MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
									DATA extends JeeslSurveyData<L,D,?,ANSWER,?>,
									OPTIONS extends JeeslSurveyOptionSet<L,D,?,OPTION>,
									OPTION extends JeeslSurveyOption<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(JsonSurveyAnswerFactory.class);
	
	private final Answer q;
	
	private JsonSurveyQuestionFactory<L,D,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION> jfQuestion;
	
	public JsonSurveyAnswerFactory(Answer q)
	{
		this.q=q;
		if(q.getQuestion()!=null) {jfQuestion = new JsonSurveyQuestionFactory<L,D,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION>(q.getQuestion());}
	}
	
	public Answer build(ANSWER answer)
	{
		Answer json = build();
		if(q.getId()>0) {json.setId(answer.getId());}
		if(q.getQuestion()!=null) {json.setQuestion(jfQuestion.build(answer.getQuestion()));}
		
		if(q.getValueText()!=null && answer.getValueText()!=null) {json.setValueText(answer.getValueText());}
		return json;
	}
	
	public static Answer build(){return new Answer();}
	public static Answer build(Question question){Answer json = build();json.setQuestion(question);return json;}
	
	
	public static Answer build(long id)
	{
		Answer json = build();
		json.setId(id);
		return json;
	}
}