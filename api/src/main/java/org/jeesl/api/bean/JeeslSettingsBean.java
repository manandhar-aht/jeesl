package org.jeesl.api.bean;

import java.io.Serializable;

public interface JeeslSettingsBean extends Serializable
{
	String getPaginatorTemplate();
	String getRowsPerPageTemplate();
	String getPaginatorPosition();
	
	String getDatePattern();
	String getDateTimePattern();
}