package org.jeesl.factory.xml.module.survey;

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
import org.jeesl.util.comparator.pojo.BooleanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.survey.Cell;

public class XmlCellFactory<L extends UtilsLang,D extends UtilsDescription,
				UNIT extends UtilsStatus<UNIT,L,D>,
				ANSWER extends JeeslSurveyAnswer<L,D,?,MATRIX,DATA,OPTION>,
				MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
				DATA extends JeeslSurveyData<L,D,?,ANSWER,?>,
				OPTIONS extends JeeslSurveyOptionSet<L,D,?,OPTION>,
				OPTION extends JeeslSurveyOption<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlCellFactory.class);
	
	
	private String localeCode;
			
	public XmlCellFactory(String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	public Cell build(MATRIX matrix)
	{
		Cell xml = new Cell();
		
		if(BooleanComparator.active(matrix.getAnswer().getQuestion().getShowBoolean()) && matrix.getValueBoolean()!=null){xml.setLabel(matrix.getValueBoolean().toString());}
		if(BooleanComparator.active(matrix.getAnswer().getQuestion().getShowInteger()) && matrix.getValueNumber()!=null){xml.setLabel(matrix.getValueNumber().toString());}
		if(BooleanComparator.active(matrix.getAnswer().getQuestion().getShowDouble()) && matrix.getValueDouble()!=null){xml.setLabel(matrix.getValueDouble().toString());}
		if(BooleanComparator.active(matrix.getAnswer().getQuestion().getShowText()) && matrix.getValueText()!=null){xml.setLabel(matrix.getValueText());}
	
		if(BooleanComparator.active(matrix.getAnswer().getQuestion().getShowSelectOne())){xml.setLabel(matrix.getOption().getName().get(localeCode).getLang());}
		
		return xml;
		
	}
}