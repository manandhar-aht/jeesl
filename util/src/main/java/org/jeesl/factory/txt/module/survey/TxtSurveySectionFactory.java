package org.jeesl.factory.txt.module.survey;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class TxtSurveySectionFactory <L extends UtilsLang, D extends UtilsDescription, SECTION extends JeeslSurveySection<L,D,?,SECTION,?>>
{
	final static Logger logger = LoggerFactory.getLogger(TxtSurveySectionFactory.class);
		
	public String codes(List<SECTION> sections)
	{
		List<String> list = new ArrayList<String>();
		if(sections!=null)
		{
			for(SECTION section : sections)
			{
				list.add(section.getCode());
			}
		}
		return StringUtil.join(list,", ");
	}
}