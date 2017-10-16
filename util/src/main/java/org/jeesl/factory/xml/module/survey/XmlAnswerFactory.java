package org.jeesl.factory.xml.module.survey;

import org.jeesl.api.facade.module.JeeslSurveyFacade;
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
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionEntity;
import org.jeesl.model.xml.jeesl.QuerySurvey;
import org.jeesl.util.comparator.pojo.BooleanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.text.XmlRemarkFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.survey.Answer;

public class XmlAnswerFactory<L extends UtilsLang,D extends UtilsDescription,
								SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>,
								SS extends UtilsStatus<SS,L,D>,SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>,
								TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>,
								VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>,
								TS extends UtilsStatus<TS,L,D>,
								TC extends UtilsStatus<TC,L,D>,
								SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>,
								QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>, QE extends UtilsStatus<QE,L,D>,
								SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>,
								UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>,
								MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>,
								DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>,
								OPTIONS extends JeeslSurveyOptionSet<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>,
								OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>,
								CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>, DOMAIN extends JeeslSurveyDomain<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>, DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>, ANALYSIS extends JeeslSurveyAnalysis<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>, AQ extends JeeslSurveyAnalysisQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>, AT extends JeeslSurveyAnalysisTool<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>, ATT extends UtilsStatus<ATT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlAnswerFactory.class);
		
	private Answer q;
	
	private XmlQuestionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT> xfQuestion;
	private XmlDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT> xfData;
	private XmlOptionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT> xfOption;
	private XmlMatrixFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT> xfMatrix;
	
	public XmlAnswerFactory(Query q){this(q.getLang(),q.getAnswer());}
	public XmlAnswerFactory(QuerySurvey q){this(q.getLocaleCode(),q.getAnswer());}
	public XmlAnswerFactory(String localeCode, Answer q)
	{
		this.q=q;
		
		if(q.isSetData()){xfQuestion = new XmlQuestionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>(q.getQuestion());}
		if(q.isSetData()){xfData = new XmlDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>(localeCode,q.getData());}
		if(q.isSetOption()) {xfOption = new XmlOptionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>(localeCode,q.getOption());}
		if(q.isSetMatrix()) {xfMatrix = new XmlMatrixFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT>(localeCode,q.getMatrix());}
	}
	
	public void lazyLoad(JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey)
	{
		if(q.isSetMatrix()){xfMatrix.lazyLoad(fSurvey);}
	}
	
	public Answer build(ANSWER ejb)
	{		
		Answer xml = new Answer();
		if(q.isSetId()){xml.setId(ejb.getId());}
		
		if(q.isSetQuestion()){xml.setQuestion(xfQuestion.build(ejb.getQuestion()));}
		if(q.isSetData()){xml.setData(xfData.build(ejb.getData()));}
		
		if(q.isSetValueBoolean() && ejb.getQuestion().getShowBoolean() && ejb.getValueBoolean()!=null){xml.setValueBoolean(ejb.getValueBoolean());}
		if(q.isSetValueNumber() && ejb.getQuestion().getShowInteger() && ejb.getValueNumber()!=null){xml.setValueNumber(ejb.getValueNumber());}
		if(q.isSetValueDouble() && ejb.getQuestion().getShowDouble() && ejb.getValueDouble()!=null){xml.setValueDouble(ejb.getValueDouble());}
		if(q.isSetScore() && ejb.getQuestion().getShowScore() && ejb.getScore()!=null){xml.setScore(ejb.getScore());}
		if(q.isSetAnswer() && ejb.getQuestion().getShowText() && ejb.getValueText()!=null){xml.setAnswer(net.sf.ahtutils.factory.xml.text.XmlAnswerFactory.build(ejb.getValueText()));}
		if(q.isSetRemark() && ejb.getQuestion().getShowRemark() && ejb.getRemark()!=null){xml.setRemark(XmlRemarkFactory.build(ejb.getRemark()));}
	
		if(q.isSetOption() && BooleanComparator.active(ejb.getQuestion().getShowSelectOne())){xml.setOption(xfOption.build(ejb.getOption()));}
		if(q.isSetMatrix() && BooleanComparator.active(ejb.getQuestion().getShowMatrix())){xml.setMatrix(xfMatrix.build(ejb));}
		
		return xml;
	}
	
	public static Answer id()
	{
		Answer xml = new Answer();
		xml.setId(0);
		return xml;
	}
}