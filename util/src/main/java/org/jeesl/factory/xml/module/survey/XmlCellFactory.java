package org.jeesl.factory.xml.module.survey;

import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.util.comparator.pojo.BooleanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.survey.Cell;

public class XmlCellFactory<MATRIX extends JeeslSurveyMatrix<?,?,?,?>>
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