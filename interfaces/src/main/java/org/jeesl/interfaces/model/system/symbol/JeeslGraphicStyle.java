package org.jeesl.interfaces.model.system.symbol;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;

public interface JeeslGraphicStyle extends Serializable,EjbPersistable,EjbWithCode,JeeslOptionRestDownload
{
	public static enum Code{circle,square,triangle}
	public static enum Group{outer,inner}
	public static enum Color{outer,inner}
	public static enum Size{outer}
}