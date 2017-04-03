package org.jeesl.api.bean;

import java.util.Map;

public interface JeeslFeatureManagerBean
{
	Map<String,Boolean> getMap();
	void realodFeatures();
}