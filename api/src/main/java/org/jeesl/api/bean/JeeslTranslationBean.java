package org.jeesl.api.bean;

import java.util.List;

public interface JeeslTranslationBean
{	
	List<String> getLangKeys();
	String get(String localeCode, String translationKey);
}