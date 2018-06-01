package org.jeesl.factory.ejb.module.survey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyCorrelationFactory<ANSWER extends JeeslSurveyAnswer<?,?,?,?,DATA,?>,
											DATA extends JeeslSurveyData<?,?,?,ANSWER,CORRELATION>,
											CORRELATION extends JeeslSurveyCorrelation<?,?,DATA>
											>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyCorrelationFactory.class);

	public Map<CORRELATION,List<ANSWER>> toMapCorrelation(Map<DATA,List<ANSWER>> mapData)
	{
		Map<CORRELATION,List<ANSWER>> map = new HashMap<CORRELATION,List<ANSWER>>();
		
		for(DATA d : mapData.keySet())
		{
			map.put(d.getCorrelation(), mapData.get(d));
		}
		
		return map;
	}
}