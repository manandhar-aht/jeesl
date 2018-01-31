package org.jeesl.factory.ejb.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;

public class EjbPositionFactory
{
	final static Logger logger = LoggerFactory.getLogger(EjbPositionFactory.class);
    
	public static <T extends EjbWithPosition> void next(T ejb, List<T> list)
	{
		if(list==null) {ejb.setPosition(1);}
		else {ejb.setPosition(list.size()+1);}
	}
}