package org.jeesl.controller.processor.survey;

import java.util.Iterator;

import net.sf.ahtutils.xml.survey.Question;
import net.sf.ahtutils.xml.survey.Section;
import net.sf.ahtutils.xml.survey.Template;

public class SurveyTemplateProcessor
{
	public static void removeInvisibleElements(Template template)
	{
		for(Iterator<Section> iSection = template.getSection().iterator(); iSection.hasNext();)
		{
		    Section s = iSection.next();
		    if(!s.isVisible()) {iSection.remove();}
		    else
		    {
		    	for(Iterator<Question> iQuestion = s.getQuestion().iterator(); iQuestion.hasNext();)
				{
				    Question q = iQuestion.next();
				    if(!q.isVisible()) {iQuestion.remove();}
				}
		    }
		}
	}
}