package org.jeesl.controller.processor.survey;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jeesl.api.bean.module.survey.JeeslSurveyCache;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SurveyConditionalHandler<TEMPLATE extends JeeslSurveyTemplate<?,?,?,TEMPLATE,?,?,?,SECTION,?,?>,
							SECTION extends JeeslSurveySection<?,?,TEMPLATE,SECTION,QUESTION>,
							QUESTION extends JeeslSurveyQuestion<?,?,SECTION,?,?,?,?,OPTION,?>,
							CONDITION extends JeeslSurveyCondition<QUESTION,?,OPTION>,
							ANSWER extends JeeslSurveyAnswer<?,?,QUESTION,?,?,OPTION>,
							OPTION extends JeeslSurveyOption<?,?>>
	implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(SurveyConditionalHandler.class);
	private static final long serialVersionUID = 1L;
	
	private JeeslSurveyCache<TEMPLATE,SECTION,QUESTION,CONDITION> cache;
	
	private final Set<QUESTION> questions;
	private final Map<QUESTION,Boolean> rendered; public Map<QUESTION, Boolean> getRendered() {return rendered;}
	private final Map<QUESTION,Boolean> triggers; public Map<QUESTION, Boolean> getTriggers() {return triggers;}
		
	public SurveyConditionalHandler(JeeslSurveyCache<TEMPLATE,SECTION,QUESTION,CONDITION> cache)
	{
		this.cache=cache;
		questions = new HashSet<QUESTION>();
		rendered = new HashMap<QUESTION,Boolean>();
		triggers = new HashMap<QUESTION,Boolean>();
	}
	
	public void clear()
	{
		questions.clear();
		rendered.clear();
		triggers.clear();
	}
	
	public void init(TEMPLATE template)
	{
		for(SECTION section : cache.getSections(template))
		{
			logger.info(section.toString());
			for(QUESTION question : cache.getQuestions(section))
			{
				logger.info("\t"+question.toString());
			}
		}
	}
	
	public void addQuestion(QUESTION question)
	{
		questions.add(question);
		rendered.put(question,false);
		
		if(question.getRenderCondition()==null || question.getRenderCondition().trim().isEmpty()) {rendered.put(question,true);}
	}
	
	public void debug()
	{
		logger.info("Debugging "+this.getClass().getSimpleName());
		for(QUESTION q : questions)
		{
			logger.info("Q: "+q.getCode()+" "+rendered.get(q));
			for(CONDITION c : cache.getConditions(q))
			{
				logger.info("\tC: "+c.toString());
			}
		}
	}
}