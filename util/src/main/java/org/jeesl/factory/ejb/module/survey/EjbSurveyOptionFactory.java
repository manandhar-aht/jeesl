package org.jeesl.factory.ejb.module.survey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyOptionFactory<QUESTION extends JeeslSurveyQuestion<?,?,?,?,?,?,?,OPTION,?>,
				OPTION extends JeeslSurveyOption<?,?>
				>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyOptionFactory.class);
	
	final Class<OPTION> cOption;
    
	public EjbSurveyOptionFactory(final Class<OPTION> cOption)
	{       
        this.cOption = cOption;
	}
	    
	public OPTION build(QUESTION question, String code)
	{
		OPTION ejb = null;
		try
		{
			ejb = cOption.newInstance();
//			ejb.setQuestion(question);
			ejb.setCode(code);
			ejb.setPosition(1);

		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public List<OPTION> toRows(List<OPTION> options)
	{
		List<OPTION> rows = new ArrayList<OPTION>();
		for(OPTION option : options)
		{
			if(option.getRow()){rows.add(option);}
		}
		return rows;
	}
	public List<OPTION> toColumns(List<OPTION> options)
	{
		List<OPTION> columns = new ArrayList<OPTION>();
		for(OPTION option : options)
		{
			if(option.getCol()){columns.add(option);}
		}
		return columns;
	}
	public List<OPTION> toCells(List<OPTION> options)
	{
		List<OPTION> columns = new ArrayList<OPTION>();
		for(OPTION option : options)
		{
			if(option.getCell()){columns.add(option);}
		}
		return columns;
	}
	
	public Map<Long,OPTION> toIdMap(List<OPTION> options)
	{
		Map<Long,OPTION> map = new HashMap<Long,OPTION>();
		for(OPTION option : options)
		{
			map.put(option.getId(),option);
		}
		return map;
	}
}