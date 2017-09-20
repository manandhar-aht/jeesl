package org.jeesl.factory.xml.module.survey;

import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.survey.Survey;
import net.sf.exlp.util.DateUtil;

public class XmlSurveyFactory<L extends UtilsLang,D extends UtilsDescription,SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,SS extends UtilsStatus<SS,L,D>,SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>, TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,TS extends UtilsStatus<TS,L,D>,TC extends UtilsStatus<TC,L,D>,SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>, SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>, MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlSurveyFactory.class);
	
	private JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> fSurvey;
	private Class<SURVEY> cSurvey;
	
	private final String localeCode;
	private final Survey q;
	
	private XmlStatusFactory<SS,L,D> xfStatus;
	private XmlDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> xfData;
	
	public XmlSurveyFactory(String localeCode, Survey q)
	{
		this.localeCode=localeCode;
		this.q=q;
		if(q.isSetData()){xfData = new XmlDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>(localeCode,q.getData().get(0));}
		if(q.isSetStatus()){xfStatus = new XmlStatusFactory<SS,L,D>(q.getStatus());}
	}
	
	public void lazyLoad(JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> fSurvey,Class<SURVEY> cSurvey,Class<SECTION> cSection,Class<DATA> cData)
	{
		this.fSurvey=fSurvey;
		this.cSurvey=cSurvey;
		
		if(q.isSetData()){xfData.lazyLoad(fSurvey,cData);}
	}
	
	public Survey build(SURVEY ejb)
	{
		if(fSurvey!=null){ejb = fSurvey.load(cSurvey,ejb);}
		
		Survey xml = new Survey();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetName()){xml.setName(ejb.getLbl());}
		if(q.isSetValidFrom()){xml.setValidFrom(DateUtil.toXmlGc(ejb.getStartDate()));}
		if(q.isSetValidTo()){xml.setValidTo(DateUtil.toXmlGc(ejb.getEndDate()));}
		
		if(q.isSetStatus()) {xml.setStatus(xfStatus.build(ejb.getStatus()));}
		
		if(q.isSetTemplate())
		{
			XmlTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> f = new XmlTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>(localeCode,q.getTemplate());
//			if(fSurvey!=null){f.lazyLoad(fSurvey,cSection);}
			xml.setTemplate(f.build(ejb.getTemplate()));
		}
		
		if(q.isSetData())
		{
			
			for(DATA data : ejb.getSurveyData())
			{
				xml.getData().add(xfData.build(data));
			}
		}
		return xml;
	}
	
	public static Survey id()
	{
		Survey xml = new Survey();
		xml.setId(0);
		return xml;
	}
}