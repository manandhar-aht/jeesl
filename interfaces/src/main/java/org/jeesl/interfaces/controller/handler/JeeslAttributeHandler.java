package org.jeesl.interfaces.controller.handler;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;

public interface JeeslAttributeHandler<CONTAINER extends JeeslAttributeContainer<?,?>>
{
	CONTAINER saveContainer() throws UtilsConstraintViolationException, UtilsLockingException;
}