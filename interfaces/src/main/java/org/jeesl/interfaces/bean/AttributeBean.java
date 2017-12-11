package org.jeesl.interfaces.bean;

import org.jeesl.interfaces.controller.handler.JeeslAttributeHandler;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;

public interface AttributeBean<CONTAINER extends JeeslAttributeContainer<?,?>>
{
	void save(JeeslAttributeHandler<CONTAINER> handler) throws UtilsConstraintViolationException, UtilsLockingException;
}