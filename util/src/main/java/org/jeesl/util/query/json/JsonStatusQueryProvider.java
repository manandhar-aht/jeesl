package org.jeesl.util.query.json;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.factory.json.system.survey.JsonQuestionFactory;
import org.jeesl.factory.json.system.survey.JsonSectionFactory;
import org.jeesl.model.json.survey.Question;
import org.jeesl.model.json.survey.Section;
import org.jeesl.model.json.survey.Template;
import org.jeesl.model.json.system.status.JsonStatus;

import net.sf.ahtutils.xml.aht.Query;

public class JsonStatusQueryProvider
{
	public static enum Key {StatusExport,Langs,extractType,statusLabel,typeLabel,categoryLabel}
	
	private static Map<Key,Query> mQueries;
	
	public static Query get(Key key){return get(key,null);}
	public static Query get(Key key,String lang)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{

			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	public static JsonStatus statusExport()
	{				
		JsonStatus xml = new JsonStatus();
		xml.setId(Long.valueOf(0));
		xml.setCode("");
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