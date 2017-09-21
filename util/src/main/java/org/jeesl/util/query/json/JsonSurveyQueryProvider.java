package org.jeesl.util.query.json;

import java.util.Date;

import org.jeesl.factory.json.system.survey.JsonOptionFactory;
import org.jeesl.factory.json.system.survey.JsonQuestionFactory;
import org.jeesl.factory.json.system.survey.JsonSectionFactory;
import org.jeesl.model.json.survey.Option;
import org.jeesl.model.json.survey.Question;
import org.jeesl.model.json.survey.Section;
import org.jeesl.model.json.survey.Survey;
import org.jeesl.model.json.survey.Template;

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
		Option option = JsonOptionFactory.build();
		option.setId(0);
		option.setPosition(0);
		option.setCode("");
		option.setLabel("");
		option.setDescription("");
		option.setColumn(true);
		option.setRow(true);
		option.setCell(true);
		
		Question question = JsonQuestionFactory.build();
		question.setId(0);
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
		question.setShowText(true);
		question.setShowScore(true);
		question.setShowRemark(true);
		question.setShowSelectOne(true);
		question.setShowSelectMulti(true);
		question.setShowMatrix(true);
		question.getOptions().add(option);
		
		Section section = JsonSectionFactory.build();
		section.setId(0);
		section.setCode("");
		section.setName("");
		section.getQuestions().add(question);
		
		Template xml = new Template();
		xml.setId(Long.valueOf(0));
		xml.getSections().add(section);
		
		return xml;
	}
}