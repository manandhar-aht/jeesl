package org.jeesl.factory.xml.module.survey;

import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.factory.xml.system.status.XmlCategoryFactory;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.jeesl.interfaces.model.module.survey.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.JeeslSurveySection;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyTemplateVersion;
import org.jeesl.model.xml.jeesl.QuerySurvey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionFactory;
import net.sf.ahtutils.factory.xml.text.XmlRemarkFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.survey.Template;

public class XmlTemplateFactory<L extends UtilsLang,D extends UtilsDescription,SURVEY extends JeeslSurvey<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,SS extends UtilsStatus<SS,L,D>,TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,TS extends UtilsStatus<TS,L,D>,TC extends UtilsStatus<TC,L,D>,SECTION extends JeeslSurveySection<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,DATA extends JeeslSurveyData<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlTemplateFactory.class);
	
	private JeeslSurveyFacade<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey;
	
	private final Template q;
	
	private XmlStatusFactory<TS,L,D> xfStatus;
	private XmlCategoryFactory<TC,L,D> xfCategory;
	private XmlSectionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> xfSection;
	
	public XmlTemplateFactory(QuerySurvey q){this(q.getTemplate());}
	public XmlTemplateFactory(Template q)
	{
		this.q=q;
		if(q.isSetStatus()) {xfStatus = new XmlStatusFactory<TS,L,D>(q.getStatus());}
		if(q.isSetCategory()) {xfCategory = new XmlCategoryFactory<TC,L,D>(q.getCategory());}
		if(q.isSetSection()) {xfSection  = new XmlSectionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(q.getSection().get(0));}	
	}
	
	public void lazyLoad(JeeslSurveyFacade<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey)
	{
		this.fSurvey=fSurvey;
		xfSection.lazyLoad(fSurvey);
	}
	
	public Template build(TEMPLATE ejb)
	{
		if(fSurvey!=null){ejb = fSurvey.load(ejb);}
		
		Template xml = new Template();
		if(q.isSetId()){xml.setId(ejb.getId());}
		
		if(q.isSetDescription()){xml.setDescription(XmlDescriptionFactory.build(ejb.getName()));}
		if(q.isSetRemark() && ejb.getRemark()!=null){xml.setRemark(XmlRemarkFactory.build(ejb.getRemark()));}
		
		if(q.isSetCategory()){xml.setCategory(xfCategory.build(ejb.getCategory()));}
		if(q.isSetStatus()){xml.setStatus(xfStatus.build(ejb.getStatus()));}
		
		if(q.isSetSection())
		{
			XmlSectionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> f = new XmlSectionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(q.getSection().get(0));
			if(fSurvey!=null){f.lazyLoad(fSurvey);}
			
			for(SECTION section : ejb.getSections())
			{
				xml.getSection().add(f.build(section));
			}
		}

		return xml;
	}
	
	public static Template id(){return id(0);}
	public static Template id(long id)
	{
		Template xml = new Template();
		xml.setId(id);
		return xml;
	}
}