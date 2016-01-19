package net.sf.ahtutils.jsf.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;

public class PositionListReorderer 
{
	final static Logger logger = LoggerFactory.getLogger(PositionListReorderer.class);
	
	public static <T extends EjbWithPosition> void reorder(UtilsFacade facade, List<T> list) throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("updateOrder "+list.size());
		int i=1;
		for(T t : list)
		{
			t.setPosition(i);
			facade.update(t);
			i++;
		}
	}
}
