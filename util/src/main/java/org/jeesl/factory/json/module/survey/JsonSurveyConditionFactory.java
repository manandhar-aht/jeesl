package org.jeesl.factory.json.module.survey;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.model.json.survey.Condition;
import org.jeesl.model.json.survey.Option;
import org.jeesl.model.json.survey.Question;
import org.jeesl.model.json.system.status.JsonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JsonSurveyConditionFactory<CONDITION extends JeeslSurveyCondition<?,QE,OPTION>,
										QE extends UtilsStatus<QE,?,?>,
										OPTION extends JeeslSurveyOption<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JsonSurveyConditionFactory.class);
	
	@SuppressWarnings("unused")
	private final String localeCode;
	private final Condition q;
	
	public JsonSurveyConditionFactory(String localeCode, Condition q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
	
	public static Condition build() {return new Condition();}
	
	public Condition build(CONDITION ejb)
	{
		Condition json = build();
		
		if(q.getId()!=null) {json.setId(ejb.getId());}
		json.setNegate(ejb.isNegate());
		json.setPosition(ejb.getPosition());
		
		if(q.getTrigger()!=null)
		{
			Question trigger = new Question();
			trigger.setId(ejb.getTriggerQuestion().getId());
			json.setTrigger(trigger);
		}
		if(q.getOption()!=null)
		{
			Option option = new Option();
			option.setId(ejb.getOption().getId());
			json.setOption(option);
		}
		if(q.getType()!=null)
		{
			JsonType type = new JsonType();
			type.setCode(ejb.getElement().getCode());
			json.setType(type);
		}
		
		return json;
	}
	
}