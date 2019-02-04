package org.jeesl.controller.handler.module.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jeesl.api.bean.module.survey.JeeslSurveyCache;
import org.jeesl.controller.handler.module.survey.antlr.ConditionEvaluator;
import org.jeesl.factory.builder.module.survey.SurveyCoreFactoryBuilder;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnswerFactory;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyValidation;
import org.jeesl.util.comparator.ejb.module.survey.SurveyQuestionComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StringUtil;

public class SurveyValidationHandler<TEMPLATE extends JeeslSurveyTemplate<?,?,?,TEMPLATE,?,?,?,SECTION,?,?>,
							SECTION extends JeeslSurveySection<?,?,TEMPLATE,SECTION,QUESTION>,
							QUESTION extends JeeslSurveyQuestion<?,?,SECTION,?,VALIDATION,?,?,?,?,OPTION,?>,
							VALIDATION extends JeeslSurveyValidation<?,?,QUESTION,?>,
							ANSWER extends JeeslSurveyAnswer<?,?,QUESTION,?,?,OPTION>,
							OPTION extends JeeslSurveyOption<?,?>>
	implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(SurveyValidationHandler.class);
	private static final long serialVersionUID = 1L;
	private static final boolean debug = true;
	
	private JeeslSurveyCache<TEMPLATE,SECTION,QUESTION,?,VALIDATION> cache;
	private final Comparator<QUESTION> cpQuestion;
	
	private final List<QUESTION> questions;
	private final Map<QUESTION,ANSWER> answers;
	private final Map<QUESTION,Boolean> rendered; public Map<QUESTION, Boolean> getRendered() {return rendered;}
	private final Map<QUESTION,List<VALIDATION>> conditions; public Map<QUESTION,List<VALIDATION>> getConditions() {return conditions;}
	private final Map<QUESTION,Set<QUESTION>> triggers; public Map<QUESTION,Set<QUESTION>> getTriggers() {return triggers;}
	
	private EjbSurveyAnswerFactory<SECTION,QUESTION,ANSWER,?,?,OPTION> efAnswer;
	private final ConditionEvaluator evaluator;
	
	public SurveyValidationHandler(SurveyCoreFactoryBuilder<?,?,?,?,?,?,TEMPLATE,?,?,?,SECTION,QUESTION,?,VALIDATION,?,?,?,ANSWER,?,?,?,OPTION,?,?> fbCore,
									JeeslSurveyCache<TEMPLATE,SECTION,QUESTION,?,VALIDATION> cache
									)
	{
		this.cache=cache;
		questions = new ArrayList<QUESTION>();
		answers = new HashMap<QUESTION,ANSWER>();
		rendered = new HashMap<QUESTION,Boolean>();
		conditions  = new HashMap<QUESTION,List<VALIDATION>>();
		triggers = new HashMap<QUESTION,Set<QUESTION>>();
		
		evaluator = new ConditionEvaluator();
		
		efAnswer = fbCore.answer();
		cpQuestion = new SurveyQuestionComparator<QUESTION>().factory(SurveyQuestionComparator.Type.position);
	}
	
	public void clear()
	{
		answers.clear();
		questions.clear();
		rendered.clear();
		conditions.clear();
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
				addQuestion(question);
			}
		}
		Collections.sort(questions,cpQuestion);
	}
	
	public void addQuestion(QUESTION question)
	{
		questions.add(question);
		rendered.put(question,true);
		List<VALIDATION> list = null;//cache.getConditions(question);
		if(list==null) {logger.warn("THe condition List is null ...");}
		else
		{
			conditions.put(question,list);
			for(VALIDATION c : list)
			{
//				if(!triggers.containsKey(c.getTriggerQuestion())) {triggers.put(c.getTriggerQuestion(),new HashSet<QUESTION>());}
//				triggers.get(c.getTriggerQuestion()).add(question);
			}
		}
	}
	
	public void evaluateList(List<ANSWER> answers) 
	{
		this.evaluteMap(efAnswer.toMapQuestion(answers));
	}
	
	public void evaluteMap(Map<QUESTION,ANSWER> answers)
	{
		this.answers.clear();
		this.answers.putAll(answers);
		for(QUESTION q : questions)
		{
			if(q.getRenderCondition()==null || q.getRenderCondition().trim().isEmpty()) {rendered.put(q,true);}
			else
			{
				rendered.put(q,evaluate(q));
			}
		}
	}
	
	public void update(ANSWER answer)
	{
		if(debug) {logger.info("Update "+answer.toString()+" for Question:"+answer.getQuestion());}
		answers.put(answer.getQuestion(),answer);
		if(debug)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Updating "+answer.toString());
			sb.append(" triggering ");
			if(triggers.containsKey(answer.getQuestion())) {sb.append(triggers.get(answer.getQuestion()).size());}
			else {sb.append("0");}
			logger.info(sb.toString());
		}	
		
		if(triggers.containsKey(answer.getQuestion()))
		{
			for(QUESTION q : triggers.get(answer.getQuestion()))
			{
				rendered.put(q,evaluate(q));
			}
		}	
	}

	private boolean evaluate(QUESTION question)
	{
		if(debug) {logger.info("Evaluation Question: "+question.toString());}
		List<Boolean> booleans = new ArrayList<Boolean>();
		for(VALIDATION c : conditions.get(question))
		{
			
		}
		
		boolean result = evaluator.evaluate(question.getRenderCondition(), booleans);
		
		if(debug)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Evaluating: ");
			sb.append(question.toString());
			sb.append(" code:").append(question.getCode());
			sb.append(" expression: ["+question.getRenderCondition()).append("]");
			sb.append(" values: ").append(booleans);
			sb.append(" Result: ").append(result);
			logger.info("Evaluating: "+sb.toString());
		}
		
		return result;
	}
	
	public void debug()
	{
		logger.info(StringUtil.stars());
		logger.info("Debugging "+this.getClass().getSimpleName()+" Questions:"+questions.size());
		for(QUESTION q : questions)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Q: "+q.getCode()+" "+rendered.get(q));
			if(triggers.containsKey(q))
			{
				sb.append(" Triggers: "+triggers.size());
				List<String> l = new ArrayList<String>();
				
				for(QUESTION qt : triggers.get(q)){l.add(qt.getCode());}
				sb.append(" [ ").append(StringUtils.join(l, ",")).append(" ]");
			}
			logger.info(sb.toString());
		}
	}
}