package org.jeesl.factory.xml.module.survey;

import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.factory.xml.system.lang.XmlDescriptionFactory;
import org.jeesl.factory.xml.system.status.XmlCategoryFactory;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
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
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.model.xml.jeesl.QuerySurvey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.text.XmlRemarkFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.survey.Template;

public class XmlTemplateFactory<L extends UtilsLang,D extends UtilsDescription,
				SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
				SS extends UtilsStatus<SS,L,D>,
				SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX>, TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS>,VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,TS extends UtilsStatus<TS,L,D>,TC extends UtilsStatus<TC,L,D>,SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>, QE extends UtilsStatus<QE,L,D>, SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE>,UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>, MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>, OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,OPTION extends JeeslSurveyOption<L,D>,CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>, DOMAIN extends JeeslSurveyDomain<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>, PATH extends JeeslSurveyDomainPath<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>, DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>, ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE>, AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>, AT extends JeeslSurveyAnalysisTool<L,D,QE,AQ,ATT>, ATT extends UtilsStatus<ATT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlTemplateFactory.class);
	
	private JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey;
	
	private final String localeCode;
	private final Template q;
	
	private XmlStatusFactory<TS,L,D> xfStatus;
	private XmlCategoryFactory<TC,L,D> xfCategory;
	private XmlSectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> xfSection;
	
	public XmlTemplateFactory(QuerySurvey q){this(q.getLocaleCode(),q.getTemplate());}
	public XmlTemplateFactory(String localeCode, Template q)
	{
		this.localeCode=localeCode;
		this.q=q;
		if(q.isSetStatus()) {xfStatus = new XmlStatusFactory<TS,L,D>(q.getStatus());}
		if(q.isSetCategory()) {xfCategory = new XmlCategoryFactory<TC,L,D>(q.getCategory());}
		if(q.isSetSection()) {xfSection  = new XmlSectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>(localeCode,q.getSection().get(0));}	
	}
	
	public void lazyLoad(JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey)
	{
		this.fSurvey=fSurvey;
		xfSection.lazyLoad(fSurvey);
	}
	
	public Template build(TEMPLATE ejb)
	{
		if(fSurvey!=null){ejb = fSurvey.load(ejb,false,false);}
		
		Template xml = new Template();
		if(q.isSetId()){xml.setId(ejb.getId());}
		
		if(q.isSetDescription()){xml.setDescription(XmlDescriptionFactory.build(ejb.getName()));}
		if(q.isSetRemark() && ejb.getRemark()!=null){xml.setRemark(XmlRemarkFactory.build(ejb.getRemark()));}
		
		if(q.isSetCategory()){xml.setCategory(xfCategory.build(ejb.getCategory()));}
		if(q.isSetStatus()){xml.setStatus(xfStatus.build(ejb.getStatus()));}
		
		if(q.isSetSection())
		{
			XmlSectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> f = new XmlSectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>(localeCode,q.getSection().get(0));
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