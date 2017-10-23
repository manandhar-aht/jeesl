package org.jeesl.factory.txt.module.survey;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class TxtOptionFactory <L extends UtilsLang, D extends UtilsDescription, OPTION extends JeeslSurveyOption<L,D>>
{
	private final String localeCode;
	
	public TxtOptionFactory(String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	public String labels (List<OPTION> list)
	{
		if(list==null || list.isEmpty()){return null;}
		List<String> result = new ArrayList<String>();
		for(OPTION ejb : list){result.add(ejb.getName().get(localeCode).getLang());}
		return StringUtils.join(result, ", ");
	}
}