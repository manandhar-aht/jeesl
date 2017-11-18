package org.jeesl.controller.handler.ui.helper;

import java.io.Serializable;
import java.util.List;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UiHelperSurvey <VERSION extends JeeslSurveyTemplateVersion<?,?,?>, SECTION extends JeeslSurveySection<?,?,?,SECTION,?>>
			implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(UiHelperSurvey.class);
		
	private boolean versionRemoveable; public boolean isVersionRemoveable() {return versionRemoveable;}

	public UiHelperSurvey()
	{
		versionRemoveable = false;
	}
	
	public void check(VERSION version, List<SECTION> sections)
	{
		if(EjbIdFactory.isSaved(version) && (sections==null || sections.isEmpty())){versionRemoveable=true;}
		else{versionRemoveable = false;}
		logger.info("versionRemoveable:"+versionRemoveable);
	}
}