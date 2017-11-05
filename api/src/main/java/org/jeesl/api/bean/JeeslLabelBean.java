package org.jeesl.api.bean;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

public interface JeeslLabelBean<RE extends JeeslRevisionEntity<?,?,?,?,?>>
{	
	void reload(RE re);
}