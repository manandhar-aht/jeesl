package org.jeesl.interfaces.model.module.attribute;

import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;

public interface JeeslAttributeType	extends UtilsStatusFixedCode
{
	public static enum Code{text,number,bool};
}