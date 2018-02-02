package org.jeesl.interfaces.model.system.io.cms;

import java.util.Map;

public interface JeeslWithMarkup <M extends JeeslIoCmsMarkup<?>>
{
	Map<String,M> getTranslation();
	void setTranslation(Map<String,M> translation);
}