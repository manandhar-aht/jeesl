package org.jeesl.controller.handler.module.survey;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jeesl.api.bean.module.survey.JeeslSurveyCache;
import org.jeesl.controller.handler.module.survey.antlr.ConditionEvaluator;
import org.jeesl.factory.builder.module.survey.SurveyCoreFactoryBuilder;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnswerFactory;
import org.jeesl.interfaces.controller.processor.SurveyValidator;
import org.jeesl.interfaces.model.json.module.survey.SurveyValidatorConfiguration;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyValidation;
import org.jeesl.util.comparator.ejb.module.survey.SurveyQuestionComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.JsonUtil;
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
	private final Map<VALIDATION,SurveyValidator<ANSWER>> validators; public Map<VALIDATION,SurveyValidator<ANSWER>> getValidators() {return validators;}
	private final Map<QUESTION,List<VALIDATION>> validations; public Map<QUESTION,List<VALIDATION>> getValidations() {return validations;}
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
		validators = new HashMap<VALIDATION,SurveyValidator<ANSWER>>();
		validations  = new HashMap<QUESTION,List<VALIDATION>>();
		triggers = new HashMap<QUESTION,Set<QUESTION>>();
		
		evaluator = new ConditionEvaluator();
		
		efAnswer = fbCore.answer();
		cpQuestion = new SurveyQuestionComparator<QUESTION>().factory(SurveyQuestionComparator.Type.position);
	}
	
	public void clear()
	{
		answers.clear();
		questions.clear();
		validations.clear();
		validations.clear();
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
				initQuestion(question);
			}
		}
		Collections.sort(questions,cpQuestion);
	}
	
	public void initQuestion(QUESTION question)
	{
		questions.add(question);
		List<VALIDATION> list = cache.getValidations(question);
		if(list==null) {logger.warn("THe condition List is null ...");}
		else
		{
			validations.put(question,list);
			for(VALIDATION v : list)
			{
				try
				{
					Class<?> cAlgorithm = Class.forName(v.getAlgorithm().getCode()).asSubclass(SurveyValidator.class);
					Class<?> cConfig = Class.forName(v.getAlgorithm().getConfig()).asSubclass(SurveyValidatorConfiguration.class);
					
					if(debug) {logger.info("Configuration of "+cAlgorithm.getSimpleName()+" "+cConfig.getSimpleName());}
					if(debug) {logger.info("Config: "+v.getConfig());}
					
					SurveyValidator<ANSWER> validator = (SurveyValidator<ANSWER>) cAlgorithm.newInstance();
					SurveyValidatorConfiguration config = (SurveyValidatorConfiguration)JsonUtil.read(v.getConfig(),cConfig);
					JsonUtil.info(config);
					validator.init(config);
					validators.put(v,validator);
					
				}
				catch (ClassNotFoundException | IOException | InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
//				logger.info(c.toString());
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
			if(validations.containsKey(q) && !validations.get(q).isEmpty())
			{
				validate(q);
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
				
			}
		}	
	}

	private boolean validate(QUESTION question)
	{
		if(debug) {logger.info("Validating Question: "+question.toString());}
		List<Boolean> booleans = new ArrayList<Boolean>();
		for(VALIDATION v : validations.get(question))
		{
			SurveyValidator<ANSWER> validator = validators.get(v);
			boolean validationResult = validator.validate(answers.get(question));
			logger.info(v.getPosition()+" Valid Entry: "+validationResult);
			;
		}
		
		return false;
	}
	
	public void debug()
	{
		logger.info(StringUtil.stars());
		logger.info("Debugging "+this.getClass().getSimpleName()+" Questions:"+questions.size());
		for(QUESTION q : questions)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Q: "+q.getCode());
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