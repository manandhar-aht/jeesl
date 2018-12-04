package org.jeesl.util.query.json;

import java.util.ArrayList;
import java.util.Date;

import org.jeesl.factory.json.module.survey.JsonSurveyConditionFactory;
import org.jeesl.factory.json.module.survey.JsonSurveyOptionFactory;
import org.jeesl.factory.json.module.survey.JsonSurveyQuestionFactory;
import org.jeesl.factory.json.module.survey.JsonSurveySectionFactory;
import org.jeesl.model.json.survey.Answer;
import org.jeesl.model.json.survey.Condition;
import org.jeesl.model.json.survey.Option;
import org.jeesl.model.json.survey.Question;
import org.jeesl.model.json.survey.Section;
import org.jeesl.model.json.survey.Survey;
import org.jeesl.model.json.survey.Template;
import org.jeesl.model.json.system.status.JsonType;

public class JsonSurveyQueryProvider
{
	public static Survey survey()
	{				
		Survey json = new Survey();
		json.setId(Long.valueOf(1));
		json.setLabel("");
		json.setDateStart(new Date());
		json.setDateEnd(new Date());
		json.setStatus(JsonStatusQueryProvider.statusLabel());
		return json;
	}
	
	public static Template templateExport()
	{
		Question trigger = JsonSurveyQuestionFactory.build();
		trigger.setId(0l);
		
		Option triggerOption = JsonSurveyOptionFactory.build();
		triggerOption.setId(0l);
		
		JsonType type = new JsonType();
		
		Condition condition = JsonSurveyConditionFactory.build();
		condition.setId(0l);
		condition.setTrigger(trigger);
		condition.setOption(triggerOption);
		condition.setType(type);
		
		Option option = JsonSurveyOptionFactory.build();
		option.setId(0l);
		option.setPosition(0);
		option.setCode("");
		option.setLabel("");
		option.setDescription("");
		option.setColumn(true);
		option.setRow(true);
		option.setCell(true);
		
		Question question = JsonSurveyQuestionFactory.build();
		question.setId(0l);
		question.setVisible(true);
		question.setPosition(0);
		question.setCode("");
		question.setTopic("");
		question.setQuestion("");
		question.setRemark("");
		question.setCalculateScore(true);
		question.setMinScore(0d);
		question.setMaxScore(0d);
		question.setShowBoolean(true);
		question.setShowInteger(true);
		question.setShowDouble(true);
		question.setShowDate(true);
		question.setShowText(true);
		question.setShowScore(true);
		question.setShowRemark(true);
		question.setShowSelectOne(true);
		question.setShowSelectMulti(true);
		question.setShowMatrix(true);
		question.setOptions(new ArrayList<Option>());question.getOptions().add(option);
		
		question.setCondition("");
		question.setConditions(new ArrayList<Condition>());question.getConditions().add(condition);
		
		Section section = JsonSurveySectionFactory.build();
		section.setId(0l);
		section.setCode("");
		section.setName("");
		section.getQuestions().add(question);
		
		Template xml = new Template();
		xml.setId(Long.valueOf(0));
		xml.getSections().add(section);
		
		return xml;
	}
	
	public static Answer answers()
	{		
		Answer json = new Answer();
		json.setId(Long.valueOf(1));
		json.setQuestion(JsonSurveyQuestionFactory.id(1));
		json.setValueBoolean(true);
		json.setValueDouble(2d);
		json.setValueNumber(1);
		json.setValueText("");
		
		return json;
	}
}