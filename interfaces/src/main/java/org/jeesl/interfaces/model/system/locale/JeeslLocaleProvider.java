package org.jeesl.interfaces.model.system.locale;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslLocaleProvider<LOC extends UtilsStatus<LOC,?,?>> extends Serializable
{	
	String getPrimaryLocaleCode();
	List<String> getLocaleCodes();
	boolean hasLocale(String localeCode);
}