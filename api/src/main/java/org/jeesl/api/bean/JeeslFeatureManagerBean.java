package org.jeesl.api.bean;

import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.system.JeeslFeature;

public interface JeeslFeatureManagerBean <F extends JeeslFeature>
{
	List<F> getFeatures();
	Map<String,Boolean> getMap();
	void realodFeatures();
}