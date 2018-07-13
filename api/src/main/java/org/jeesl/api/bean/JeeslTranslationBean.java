package org.jeesl.api.bean;

import java.util.List;

public interface JeeslTranslationBean //extends TranslationProvider
{
	void ping();
	List<String> getLangKeys();
	String get(String localeCode, String translationKey);
}