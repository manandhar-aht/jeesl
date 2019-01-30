package org.jeesl.factory.ejb.module.survey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.model.pojo.map.generic.Nested2Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyMatrixFactory<ANSWER extends JeeslSurveyAnswer<?,?,?,MATRIX,?,OPTION>,
									MATRIX extends JeeslSurveyMatrix<?,?,ANSWER,OPTION>,
									OPTION extends JeeslSurveyOption<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyMatrixFactory.class);
	
	private final Class<MATRIX> cMatrix;
    
	public EjbSurveyMatrixFactory(final Class<MATRIX> cMatrix)
	{       
        this.cMatrix = cMatrix;
	}
		
	public MATRIX build(ANSWER answer, OPTION row, OPTION column)
	{
		MATRIX ejb = null;
		try
		{
			ejb = cMatrix.newInstance();
			ejb.setAnswer(answer);
			ejb.setRow(row);
			ejb.setColumn(column);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public Nested2Map<OPTION,OPTION,MATRIX> build(List<MATRIX> matrix)
	{
		Nested2Map<OPTION,OPTION,MATRIX> map = new Nested2Map<OPTION,OPTION,MATRIX>();
		for (MATRIX m : matrix)
		{
			map.put(m.getRow(), m.getColumn(), m);
		}
		return map;
	}
	
	public Map<ANSWER,List<MATRIX>> toMapAnswer(List<MATRIX> cells)
	{
		 Map<ANSWER,List<MATRIX>> map = new HashMap<ANSWER,List<MATRIX>>();
		 
		 for(MATRIX cell : cells)
		 {
			 if(!map.containsKey(cell.getAnswer())) {map.put(cell.getAnswer(),new ArrayList<MATRIX>());}
			 map.get(cell.getAnswer()).add(cell);
		 }
		 
		 return map;
	}
}