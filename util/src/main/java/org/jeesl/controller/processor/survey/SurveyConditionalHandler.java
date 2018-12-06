package org.jeesl.controller.processor.survey;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jeesl.api.bean.module.survey.JeeslSurveyCache;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SurveyConditionalHandler<SECTION extends JeeslSurveySection<?,?,?,SECTION,QUESTION>,
							QUESTION extends JeeslSurveyQuestion<?,?,SECTION,?,?,?,?,OPTION,?>,
							CONDITION extends JeeslSurveyCondition<QUESTION,?,OPTION>,
							ANSWER extends JeeslSurveyAnswer<?,?,QUESTION,?,?,OPTION>,
							OPTION extends JeeslSurveyOption<?,?>>
	implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(SurveyConditionalHandler.class);
	private static final long serialVersionUID = 1L;
	
	private JeeslSurveyCache<QUESTION> cache;
	
	private final Map<QUESTION,Boolean> rendered; public Map<QUESTION, Boolean> getRendered() {return rendered;}
	private final Map<QUESTION,Boolean> triggers; public Map<QUESTION, Boolean> getTriggers() {return triggers;}
		
	public SurveyConditionalHandler(JeeslSurveyCache<QUESTION> cache)
	{
		this.cache=cache;
		rendered = new HashMap<QUESTION,Boolean>();
		triggers = new HashMap<QUESTION,Boolean>();
	}
	
	public void clear()
	{
		rendered.clear();
		triggers.clear();
	}
	
	public void addQuestion(QUESTION question)
	{
		if(question.getRenderCondition()==null || question.getRenderCondition().trim().isEmpty()) {rendered.put(question,true);}
	}
	
	public void debug()
	{
		for(QUESTION q : rendered.keySet())
		{
			logger.info("Q: "+q.getCode()+" "+rendered.get(q));
		
		}
	}
	
}