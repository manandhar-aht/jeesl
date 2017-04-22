package org.jeesl.util.query.json;

import org.jeesl.factory.json.system.survey.JsonQuestionFactory;
import org.jeesl.factory.json.system.survey.JsonSectionFactory;
import org.jeesl.model.json.survey.Question;
import org.jeesl.model.json.survey.Section;
import org.jeesl.model.json.survey.Template;
import org.jeesl.model.json.system.status.JsonStatus;

public class JsonStatusQueryProvider
{
	
	public static JsonStatus statusExport()
	{				
		JsonStatus xml = new JsonStatus();
		xml.setId(Long.valueOf(0));
		xml.setCode("");
		return xml;
	}
	
	public static JsonStatus statusLabel()
	{				
		JsonStatus xml = new JsonStatus();
//		xml.setId(Long.valueOf(0));
		xml.setCode("");
		xml.setLabel("");
		return xml;
	}
	
	public static Template templateExport()
	{
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