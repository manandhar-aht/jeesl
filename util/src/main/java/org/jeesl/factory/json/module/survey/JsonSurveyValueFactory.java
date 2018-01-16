package org.jeesl.factory.json.module.survey;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.model.json.module.survey.JsonSurveyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JsonSurveyValueFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonSurveyValueFactory.class);
	
	public static JsonSurveyValue build(){return new JsonSurveyValue();}
	
	public static JsonSurveyValue build(long id)
	{
		JsonSurveyValue json = build();
		json.setId(id);
		return json;
	}
	
	public static JsonSurveyValue build(long id, long count)
	{
		JsonSurveyValue json = build();
		json.setId(id);
		json.setCount(count);
		return json;
	}
	
	public static List<JsonSurveyValue> build(Set<Long> ids)
	{
		List<JsonSurveyValue> list = new ArrayList<JsonSurveyValue>();
		for(Long id : ids) {list.add(build(id));}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static <PATH extends JeeslSurveyDomainPath<?,?,?,DENTITY,DATTRIBUTE>,
					DENTITY extends JeeslRevisionEntity<?,?,?,?,DATTRIBUTE>,
					DATTRIBUTE extends JeeslRevisionAttribute<?,?,DENTITY,?,?>>
		List<JsonSurveyValue> build(UtilsFacade fUtils, Set<Long> ids, PATH path) throws ClassNotFoundException
	{
		List<JsonSurveyValue> list = new ArrayList<JsonSurveyValue>();
		Class<EjbWithId> c = (Class<EjbWithId>)Class.forName(path.getAttribute().getEntity().getCode()).asSubclass(EjbWithId.class);
		for(EjbWithId o : fUtils.find(c, ids))
		{
			JsonSurveyValue v = build(o.getId());
			v.setEjb(o);
			list.add(v);
		}
		return list;
	}
}