package org.jeesl.interfaces.model.system.symbol;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;

public interface JeeslGraphicType extends Serializable,EjbPersistable,EjbWithCode,JeeslOptionRestDownload
{
	public static enum Code{svg,symbol}
}