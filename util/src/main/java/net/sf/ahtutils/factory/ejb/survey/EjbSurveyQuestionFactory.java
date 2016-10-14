package net.sf.ahtutils.factory.ejb.survey;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.survey.Question;

import org.jeesl.interfaces.model.survey.JeeslSurvey;
import org.jeesl.interfaces.model.survey.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.survey.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.survey.JeeslSurveyData;
import org.jeesl.interfaces.model.survey.JeeslSurveyOption;
import org.jeesl.interfaces.model.survey.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.survey.JeeslSurveySection;
import org.jeesl.interfaces.model.survey.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.survey.JeeslSurveyTemplateVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyQuestionFactory<L extends UtilsLang,
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
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyQuestionFactory.class);
	
	final Class<QUESTION> cQuestion;
    
	public EjbSurveyQuestionFactory(final Class<QUESTION> cQuestion)
	{       
        this.cQuestion = cQuestion;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,SURVEY extends JeeslSurvey<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,SS extends UtilsStatus<SS,L,D>,TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,TS extends UtilsStatus<TS,L,D>,TC extends UtilsStatus<TC,L,D>,SECTION extends JeeslSurveySection<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,DATA extends JeeslSurveyData<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
	EjbSurveyQuestionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> factory(final Class<QUESTION> cQuestion)
	{
		return new EjbSurveyQuestionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cQuestion);
	}
    
	public QUESTION build(SECTION section,UNIT unit, Question xQuestion)
	{
		return build(section,unit,
				xQuestion.getCode(),
				xQuestion.getPosition(),
				xQuestion.getTopic(),
				xQuestion.getQuestion().getValue(),
				xQuestion.getRemark().getValue());
	}
	
	public QUESTION build(SECTION section){return build(section,null,null,0,null,null,null);}
	public QUESTION build(SECTION section,UNIT unit){return build(section,unit,null,0,null,null,null);}
	
	public QUESTION build(SECTION section,UNIT unit, String code,int position,String topic,String question,String remark)
	{
		QUESTION ejb = null;
		try
		{
			ejb = cQuestion.newInstance();
			ejb.setSection(section);
			ejb.setUnit(unit);
			ejb.setCode(code);
			ejb.setPosition(position);
			ejb.setTopic(topic);
			ejb.setQuestion(question);
			ejb.setRemark(remark);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}