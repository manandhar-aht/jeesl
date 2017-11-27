package org.jeesl.interfaces.controller.handler;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;

public interface JeeslFileRepositoryStore <META extends JeeslFileMeta<?,?>>
		extends Serializable
{
	public META saveToFileRepository(META meta, byte[] bytes) throws UtilsConstraintViolationException, UtilsLockingException;
}