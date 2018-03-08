package org.jeesl.interfaces.model.system.io.cms;

import java.util.Map;

public interface JeeslWithMarkup <M extends JeeslIoCmsMarkup<?>>
{
	Map<String,M> getMarkup();
	void setMarkup(Map<String,M> translation);
}