package org.jeesl.factory.xml.module.survey;

import org.jeesl.api.facade.module.survey.JeeslSurveyCoreFacade;
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
								SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
								SS extends UtilsStatus<SS,L,D>,SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
								TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,?>,
								VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
								TS extends UtilsStatus<TS,L,D>,
								TC extends UtilsStatus<TC,L,D>,
								SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
								QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,?>,
								QE extends UtilsStatus<QE,L,D>,
								SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
								UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
								MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
								DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>,
								OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
								OPTION extends JeeslSurveyOption<L,D>,
								CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlAnswerFactory.class);
		
	private Answer q;
	
	private XmlQuestionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> xfQuestion;
	private XmlDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> xfData;
	private XmlOptionFactory<L,D,OPTION> xfOption;
	private XmlMatrixFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION> xfMatrix;
	
	public XmlAnswerFactory(Query q){this(q.getLang(),q.getAnswer());}
	public XmlAnswerFactory(QuerySurvey q){this(q.getLocaleCode(),q.getAnswer());}
	public XmlAnswerFactory(String localeCode, Answer q)
	{
		this.q=q;
		
		if(q.isSetData()) {xfQuestion = new XmlQuestionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode,q.getQuestion());}
		if(q.isSetData()) {xfData = new XmlDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode,q.getData());}
		if(q.isSetOption()) {xfOption = new XmlOptionFactory<L,D,OPTION>(localeCode,q.getOption());}
		if(q.isSetMatrix()) {xfMatrix = new XmlMatrixFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION>(localeCode,q.getMatrix());}
	}
	
	public void lazyLoad(JeeslSurveyCoreFacade<L,D,?,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fSurvey)
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
	
		if(q.isSetOption() && BooleanComparator.active(ejb.getQuestion().getShowSelectOne())){if(ejb.getOption()!=null) {xml.setOption(xfOption.build(ejb.getOption()));}}
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