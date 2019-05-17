package org.jeesl.api.bean;

import java.util.List;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

public interface JeeslLabelBean<RE extends JeeslRevisionEntity<?,?,?,?,?>>
{	
	void reload(RE re);
	List<RE> allEntities();
}