package org.jeesl.api.bean;

import net.sf.ahtutils.xml.system.ConstraintScope;

public interface JeeslConstraintsBean
{
	String getMessage(String category, String scope, String code, String lang);
	ConstraintScope getScope(String category, String scope, String lang);
}