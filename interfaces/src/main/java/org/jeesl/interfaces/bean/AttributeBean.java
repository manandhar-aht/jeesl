package org.jeesl.interfaces.bean;

import org.jeesl.interfaces.controller.handler.JeeslAttributeHandler;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;

public interface AttributeBean
{
	void save(JeeslAttributeHandler handler) throws UtilsConstraintViolationException, UtilsLockingException;
}