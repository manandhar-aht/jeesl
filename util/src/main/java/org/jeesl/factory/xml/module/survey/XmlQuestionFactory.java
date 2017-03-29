package org.jeesl.factory.xml.module.survey;

import org.jeesl.interfaces.model.module.survey.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.JeeslSurveySection;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyTemplateVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlUnitFactory;
import net.sf.ahtutils.factory.xml.text.XmlRemarkFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.survey.Question;

public class XmlQuestionFactory<L extends UtilsLang,D extends UtilsDescription,SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,SS extends UtilsStatus<SS,L,D>,SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>, TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,TS extends UtilsStatus<TS,L,D>,TC extends UtilsStatus<TC,L,D>,SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>, SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>,CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlQuestionFactory.class);
		
	private String lang;
	private Question q;
	
	//TODO tk: remove this constructor
	public XmlQuestionFactory(Question q){this(null,q);}
	
	public XmlQuestionFactory(String lang, Question q)
	{
		this.lang=lang;
		this.q=q;
	}
	
	public Question build(QUESTION ejb)
	{
		Question xml = new Question();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		if(q.isSetVisible()){xml.setVisible(ejb.isVisible());}
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetTopic()){xml.setTopic(ejb.getTopic());}
		
		if(q.isSetQuestion() && ejb.getQuestion()!=null){xml.setQuestion(net.sf.ahtutils.factory.xml.text.XmlQuestionFactory.build(ejb.getQuestion()));}
		if(q.isSetRemark() && ejb.getRemark()!=null){xml.setRemark(XmlRemarkFactory.build(ejb.getRemark()));}
		
		if(q.isSetUnit() && ejb.getUnit()!=null)
		{
			XmlUnitFactory f = new XmlUnitFactory(lang,q.getUnit());
			xml.setUnit(f.build(ejb.getUnit()));
		}
		
		if(q.isSetShowBoolean()){if(ejb.getShowBoolean()!=null){xml.setShowBoolean(ejb.getShowBoolean());}else{xml.setShowBoolean(false);}}
		if(q.isSetShowInteger()){if(ejb.getShowInteger()!=null){xml.setShowInteger(ejb.getShowInteger());}else{xml.setShowInteger(false);}}
		if(q.isSetShowDouble()){if(ejb.getShowDouble()!=null){xml.setShowDouble(ejb.getShowDouble());}else{xml.setShowDouble(false);}}
		if(q.isSetShowText()){if(ejb.getShowText()!=null){xml.setShowText(ejb.getShowText());}else{xml.setShowText(false);}}
		if(q.isSetShowScore()){if(ejb.getShowScore()!=null){xml.setShowScore(ejb.getShowScore());}else{xml.setShowScore(false);}}
		if(q.isSetShowRemark()){if(ejb.getShowRemark()!=null){xml.setShowRemark(ejb.getShowRemark());}else{xml.setShowRemark(false);}}

		return xml;
	}
	
	public static Question id()
	{
		Question xml = new Question();
		xml.setId(0);
		return xml;
	}
}