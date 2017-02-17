package org.jeesl.factory.xml.survey;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.survey.Data;

import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.interfaces.model.survey.JeeslSurvey;
import org.jeesl.interfaces.model.survey.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.survey.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.survey.JeeslSurveyData;
import org.jeesl.interfaces.model.survey.JeeslSurveyOption;
import org.jeesl.interfaces.model.survey.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.survey.JeeslSurveySection;
import org.jeesl.interfaces.model.survey.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.survey.JeeslSurveyTemplateVersion;
import org.jeesl.model.xml.jeesl.QuerySurvey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDataFactory<L extends UtilsLang,D extends UtilsDescription,SURVEY extends JeeslSurvey<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,SS extends UtilsStatus<SS,L,D>,TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,TS extends UtilsStatus<TS,L,D>,TC extends UtilsStatus<TC,L,D>,SECTION extends JeeslSurveySection<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,DATA extends JeeslSurveyData<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlDataFactory.class);
		
	private JeeslSurveyFacade<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey;
	private Class<DATA> cData;
	
	private XmlSurveyFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> xfSurvey;
	private XmlCorrelationFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> xfCorrelation;
	private XmlAnswerFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> xfAnswer;
	private XmlSectionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> xfSection;
	
	private Data q;
	
	public XmlDataFactory(QuerySurvey query){this(query.getData());}
	public XmlDataFactory(Data q)
	{
		this.q=q;
		
		if(q.isSetSurvey()) {xfSurvey = new XmlSurveyFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(q.getSurvey());}
		if(q.isSetCorrelation()){xfCorrelation = new XmlCorrelationFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(q.getCorrelation());}
		if(q.isSetAnswer()){xfAnswer = new XmlAnswerFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(q.getAnswer().get(0));}
		if(q.isSetSection()){xfSection = new XmlSectionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(q.getSection().get(0));}
	}
	
	public void lazyLoad(JeeslSurveyFacade<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey, Class<SECTION> cSection,Class<DATA> cData)
	{
		this.fSurvey=fSurvey;
		this.cData=cData;
		if(q.isSetSection()){xfSection.lazyLoad(fSurvey, cSection);}
	}
	
	public Data build(DATA ejb)
	{		
		if(fSurvey!=null){ejb = fSurvey.load(cData,ejb);}
		
		Data xml = build();
		if(q.isSetId()){xml.setId(ejb.getId());}
		
		if(q.isSetSurvey()) {xml.setSurvey(xfSurvey.build(ejb.getSurvey()));}
		if(q.isSetCorrelation()){xml.setCorrelation(xfCorrelation.build(ejb.getCorrelation()));}
		
		if(q.isSetAnswer())
		{
			for(ANSWER answer : ejb.getAnswers())
			{
				xml.getAnswer().add(xfAnswer.build(answer));
			}
		}
		
		if(q.isSetSection())
		{
			TEMPLATE template = ejb.getSurvey().getTemplate();
			if(fSurvey!=null){template = fSurvey.load(template);}
			for(SECTION section : template.getSections())
			{
				xml.getSection().add(xfSection.build(section));
			}
		}
		
		return xml;
	}
	
	public static Data id()
	{
		Data xml = build();
		xml.setId(0);
		return xml;
	}
	
	public static Data build()
	{
		Data xml = new Data();
		return xml;
	}
}