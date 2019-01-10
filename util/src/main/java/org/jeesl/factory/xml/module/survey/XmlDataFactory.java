package org.jeesl.factory.xml.module.survey;

import org.jeesl.api.facade.module.survey.JeeslSurveyCoreFacade;
import org.jeesl.api.facade.module.survey.JeeslSurveyTemplateFacade;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.model.xml.jeesl.QuerySurvey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.survey.Data;

public class XmlDataFactory<L extends UtilsLang,D extends UtilsDescription,
							SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
							SS extends UtilsStatus<SS,L,D>,
							SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
							TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,?>,
							VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,TS extends UtilsStatus<TS,L,D>,
							TC extends UtilsStatus<TC,L,D>,SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
							QUESTION extends JeeslSurveyQuestion<L,D,SECTION,?,?,QE,SCORE,UNIT,OPTIONS,OPTION,?>,
							QE extends UtilsStatus<QE,L,D>, SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
							UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
							MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
							DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>,
							OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,OPTION extends JeeslSurveyOption<L,D>,
							CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlDataFactory.class);
		
	private JeeslSurveyCoreFacade<L,D,?,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fSurvey;
	private JeeslSurveyTemplateFacade<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION> fTemplate;
	
	private Class<DATA> cData;
	
	private XmlSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> xfSurvey;
	private XmlCorrelationFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> xfCorrelation;
	private XmlAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> xfAnswer;
	private XmlSectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> xfSection;
	
	private Data q;
	
	public XmlDataFactory(QuerySurvey query){this(query.getLocaleCode(),query.getData());}
	public XmlDataFactory(String localeCode, Data q)
	{
		this.q=q;
		
		if(q.isSetSurvey()) {xfSurvey = new XmlSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode,q.getSurvey());}
		if(q.isSetCorrelation()){xfCorrelation = new XmlCorrelationFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(q.getCorrelation());}
		if(q.isSetAnswer()){xfAnswer = new XmlAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode,q.getAnswer().get(0));}
		if(q.isSetSection()){xfSection = new XmlSectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode,q.getSection().get(0));}
	}
	
	public void lazyLoad(JeeslSurveyCoreFacade<L,D,?,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fSurvey,
						JeeslSurveyTemplateFacade<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION> fTemplate,
						Class<DATA> cData)
	{
		this.fTemplate=fTemplate;
		this.fSurvey=fSurvey;
		this.cData=cData;
		if(q.isSetSection()){xfSection.lazyLoad(fSurvey);}
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
			if(fTemplate!=null){template = fTemplate.load(template,false,false);}
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