package org.jeesl.interfaces.controller.report;

import java.util.List;

import org.jeesl.model.json.JsonFlatFigure;

public interface JeeslFlatReport extends JeeslReport
{		
	List<JsonFlatFigure> getFlatList();
	void buildFlat();
}