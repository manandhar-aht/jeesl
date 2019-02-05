package org.jeesl.factory.xls.module.survey;

import org.apache.commons.lang.mutable.MutableInt;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.jeesl.factory.xls.system.io.report.XlsCellFactory;
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
import org.jeesl.util.comparator.pojo.BooleanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XlsSurveyAnswerFactory <L extends UtilsLang, D extends UtilsDescription,
							SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
							SS extends UtilsStatus<SS,L,D>,
							SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
							TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,?>,
							VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
							TS extends UtilsStatus<TS,L,D>,
							TC extends UtilsStatus<TC,L,D>,
							SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
							QUESTION extends JeeslSurveyQuestion<L,D,SECTION,?,?,QE,SCORE,UNIT,OPTIONS,OPTION,?>,
							QE extends UtilsStatus<QE,L,D>,
							SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
							MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
							DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>,
							OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
							OPTION extends JeeslSurveyOption<L,D>,
							CORRELATION extends JeeslSurveyCorrelation<DATA>>
{
	final static Logger logger = LoggerFactory.getLogger(XlsSurveyAnswerFactory.class);

	private final CellStyle style;
	
	public XlsSurveyAnswerFactory(CellStyle style)
	{
		this.style=style;
	}
	
	public void build(Row row, MutableInt colNr, ANSWER answer)
	{
		if(BooleanComparator.active(answer.getQuestion().getShowBoolean())){XlsCellFactory.build(row, colNr, style, answer.getValueBoolean(), 1);}
		else if(BooleanComparator.active(answer.getQuestion().getShowDouble())){XlsCellFactory.build(row, colNr, style, answer.getValueDouble(), 1);}
		else if(BooleanComparator.active(answer.getQuestion().getShowInteger())){XlsCellFactory.build(row, colNr, style, answer.getValueNumber(), 1);}
		else if(BooleanComparator.active(answer.getQuestion().getShowText())){XlsCellFactory.build(row, colNr, style, answer.getValueText(), 1);}
		else if(BooleanComparator.active(answer.getQuestion().getShowSelectOne()))
		{
			if(answer.getOption()!=null){XlsCellFactory.build(row, colNr, style, answer.getOption().getCode(), 1);}
			else{XlsCellFactory.build(row, colNr, style, "", 1);}
		}
		else if(BooleanComparator.active(answer.getQuestion().getShowDate())){XlsCellFactory.build(row, colNr, style, answer.getValueDate(), 1);}
		else
		{
			XlsCellFactory.build(row, colNr, style, "XXXXX", 1);
		}
		
	}
	
	public void build(Row row, MutableInt colNr, QUESTION question, MATRIX matrix)
	{
		if(BooleanComparator.active(question.getShowBoolean())){XlsCellFactory.build(row, colNr, style, matrix.getValueBoolean(), 1);}
		else if(BooleanComparator.active(question.getShowDouble())){XlsCellFactory.build(row, colNr, style, matrix.getValueDouble(), 1);}
		else if(BooleanComparator.active(question.getShowInteger())){XlsCellFactory.build(row, colNr, style, matrix.getValueNumber(), 1);}
		else if(BooleanComparator.active(question.getShowText())){XlsCellFactory.build(row, colNr, style, matrix.getValueText(), 1);}
		else if(BooleanComparator.active(question.getShowSelectOne()))
		{
			if(matrix.getOption()!=null){XlsCellFactory.build(row, colNr, style, matrix.getOption().getCode(), 1);}
			else{XlsCellFactory.build(row, colNr, style, "", 1);}
		}
//		else if(BooleanComparator.active(question.getShowDate())){XlsCellFactory.build(row, colNr, style, matrix.getValueDate(), 1);}
		else
		{
			XlsCellFactory.build(row, colNr, style, "XXXXX", 1);
		}
		
	}
}