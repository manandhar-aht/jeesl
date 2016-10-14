package net.sf.ahtutils.util.query.xml;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.jeesl.factory.xml.survey.XmlAnswerFactory;
import org.jeesl.factory.xml.survey.XmlCorrelationFactory;
import org.jeesl.factory.xml.survey.XmlDataFactory;
import org.jeesl.factory.xml.survey.XmlSurveyFactory;
import org.jeesl.factory.xml.survey.XmlTemplateFactory;

import net.sf.ahtutils.factory.xml.status.XmlCategoryFactory;
import net.sf.ahtutils.factory.xml.status.XmlDescriptionFactory;
import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.factory.xml.status.XmlUnitFactory;
import net.sf.ahtutils.factory.xml.text.XmlRemarkFactory;
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.survey.Answer;
import net.sf.ahtutils.xml.survey.Data;
import net.sf.ahtutils.xml.survey.Question;
import net.sf.ahtutils.xml.survey.Section;
import net.sf.ahtutils.xml.survey.Survey;
import net.sf.ahtutils.xml.survey.Surveys;
import net.sf.ahtutils.xml.survey.Template;
import net.sf.exlp.util.DateUtil;

public class SurveyQuery
{
	public static enum Key {exTemplate,exSurveys,exSurvey,surveyAnswers}
	
	private static Map<Key,Query> mQueries;
	
	public static Query get(Key key){return get(key,null);}
	public static Query get(Key key,String lang)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{
				case exTemplate: q.setTemplate(exTemplate());break;
				case exSurvey: q.setSurvey(exSurvey());break;
				case exSurveys: q.setSurveys(exSurveys());break;
				case surveyAnswers: q.setAnswer(surveyAnswers());
			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	public static Template exTemplate()
	{		
		Template xml = new Template();
		xml.setId(0);
		xml.setCode("");
		xml.setDescription(XmlDescriptionFactory.build(""));
		xml.setRemark(XmlRemarkFactory.build(""));
		xml.setCategory(XmlCategoryFactory.create(""));
		xml.setStatus(XmlStatusFactory.create(""));
		xml.getSection().add(exSection());
		xml.getSection().get(0).getSection().add(exSection());
		return xml;
	}
	
	public static Section exSection()
	{		
		Section xml = new Section();
		xml.setId(0);
		xml.setCode("");
		xml.setPosition(0);
		xml.setDescription(XmlDescriptionFactory.build(""));
		xml.setRemark(XmlRemarkFactory.build(""));
		xml.getQuestion().add(exQuestion());
		return xml;
	}
	
	public static Question exQuestion()
	{		
		Question xml = new Question();
		xml.setId(0);
		xml.setPosition(0);
		xml.setVisible(true);
		xml.setCode("");
		xml.setTopic("");
		xml.setUnit(XmlUnitFactory.build(""));
		xml.setQuestion(net.sf.ahtutils.factory.xml.text.XmlQuestionFactory.build(""));
		xml.setRemark(XmlRemarkFactory.build(""));
		
		return xml;
	}
	
	public static Surveys exSurveys()
	{				
		Surveys xml = new Surveys();
		xml.getSurvey().add(XmlSurveyFactory.id());
		
		return xml;
	}
	
	public static Survey exSurvey()
	{				
		Survey xml = new Survey();
		xml.setId(0);
		xml.setName("");
		xml.setValidFrom(DateUtil.toXmlGc(new Date()));
		xml.setValidTo(DateUtil.toXmlGc(new Date()));
		xml.setStatus(XmlStatusFactory.create(""));
		xml.setTemplate(XmlTemplateFactory.id());
		xml.getData().add(exData());
		return xml;
	}
	
	private static Data exData()
	{		
		Data xml = XmlDataFactory.id();
		xml.setCorrelation(XmlCorrelationFactory.id());
		xml.getAnswer().add(exAnswer());
		return xml;
	}
	
	private static Answer exAnswer()
	{
		Answer xml = XmlAnswerFactory.id();
		xml.setQuestion(org.jeesl.factory.xml.survey.XmlQuestionFactory.id());
		xml.setValueBoolean(true);
		xml.setValueNumber(0);
		return xml;
	}
	
	private static Answer surveyAnswers()
	{
		Data data = XmlDataFactory.id();
		data.setCorrelation(XmlCorrelationFactory.id());
		
		Answer xml = XmlAnswerFactory.id();
		xml.setQuestion(org.jeesl.factory.xml.survey.XmlQuestionFactory.id());
		xml.setValueBoolean(true);
		xml.setValueNumber(0);
		xml.setData(data);
		return xml;
	}
}