package org.jeesl.api.bean;

import java.util.List;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslTranslationBean //<L extends UtilsLang, D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>>
{
	void ping();
	List<String> getLangKeys();
	String get(String localeCode, String translationKey);
}