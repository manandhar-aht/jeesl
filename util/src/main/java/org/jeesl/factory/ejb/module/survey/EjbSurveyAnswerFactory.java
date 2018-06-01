package org.jeesl.factory.ejb.module.survey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.survey.Answer;

public class EjbSurveyAnswerFactory<SECTION extends JeeslSurveySection<?,?,?,SECTION,QUESTION>,
									QUESTION extends JeeslSurveyQuestion<?,?,SECTION,?,?,?,?,OPTION,?>,
									ANSWER extends JeeslSurveyAnswer<?,?,QUESTION,MATRIX,DATA,OPTION>,
									MATRIX extends JeeslSurveyMatrix<?,?,ANSWER,OPTION>,
									DATA extends JeeslSurveyData<?,?,?,ANSWER,?>,
									OPTION extends JeeslSurveyOption<?,?>
			>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyAnswerFactory.class);
	
	final Class<ANSWER> cAnswer;
    
	public EjbSurveyAnswerFactory(final Class<ANSWER> cAnswer)
	{       
        this.cAnswer = cAnswer;
	}
   	
	public ANSWER build(QUESTION question, DATA data, Answer answer)
	{
		Boolean valueBoolean = null;if(answer.isSetValueBoolean()){valueBoolean=answer.isValueBoolean();}
		Integer valueNumber = null;if(answer.isSetValueNumber()){valueNumber=answer.getValueNumber();}
		return build(question, data,valueBoolean,valueNumber);
	}
	public ANSWER build(QUESTION question, DATA data)
	{
		return build(question, data,null,null);
	}
	
	public ANSWER build(QUESTION question, DATA data,Boolean valueBoolean,Integer valueNumber)
	{
		ANSWER ejb = null;
		try
		{
			ejb = cAnswer.newInstance();
			ejb.setQuestion(question);
			ejb.setData(data);
			ejb.setValueBoolean(valueBoolean);
			ejb.setValueNumber(valueNumber);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public List<ANSWER> toSectionAnswers(SECTION section, Map<QUESTION,ANSWER> map)
	{
		List<ANSWER> list = new ArrayList<ANSWER>();
		
		for(QUESTION q : map.keySet())
		{
			if(q.getSection().equals(section))
			{
				list.add(map.get(q));
			}
		}
		return list;
	}
	
	public static <QUESTION extends JeeslSurveyQuestion<?,?,?,?,?,?,?,OPTION,?>,
					ANSWER extends JeeslSurveyAnswer<?,?,QUESTION,MATRIX,DATA,OPTION>,
					MATRIX extends JeeslSurveyMatrix<?,?,ANSWER,OPTION>,
					DATA extends JeeslSurveyData<?,?,?,ANSWER,?>,
					OPTION extends JeeslSurveyOption<?,?>
					>
			Map<QUESTION,ANSWER> toQuestionMap(List<ANSWER> list)
	{
		Map<QUESTION,ANSWER> map = new HashMap<QUESTION,ANSWER>();
		for(ANSWER a : list){map.put(a.getQuestion(),a);}
		return map;
	}
	public static <L extends UtilsLang, D extends UtilsDescription,
					QUESTION extends JeeslSurveyQuestion<L,D,?,?,?,?,?,OPTION,?>,
					ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
					MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
					DATA extends JeeslSurveyData<L,D,?,ANSWER,?>,
					OPTION extends JeeslSurveyOption<L,D>
					>
		Map<Long,ANSWER> toQuestionIdMap(List<ANSWER> list)
	{
		Map<Long,ANSWER> map = new HashMap<Long,ANSWER>();
		for(ANSWER a : list){map.put(a.getQuestion().getId(),a);}
		return map;
	}
	
	public Map<DATA,List<ANSWER>> toDataMap(List<ANSWER> answers)
	{
		Map<DATA,List<ANSWER>> map = new HashMap<DATA,List<ANSWER>>();
		for(ANSWER a : answers)
		{
			if(!map.containsKey(a.getData())) {map.put(a.getData(), new ArrayList<ANSWER>());}
			map.get(a.getData()).add(a);
		}
		return map;
	}
	
	public boolean belongsToSection(ANSWER answer, SECTION section, boolean defaultResult)
	{
		if(section==null){return defaultResult;}
		{
			return answer.getQuestion().getSection().equals(section);
		}
	}
}