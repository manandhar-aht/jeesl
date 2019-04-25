package org.jeesl.interfaces.model.module.ts.config;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;
import net.sf.ahtutils.interfaces.model.status.UtilsWithSymbol;

public interface JeeslTsDimension <S extends UtilsStatus<S,L,D>,
									L extends UtilsLang,
									D extends UtilsDescription,
									G extends JeeslGraphic<L,D,G,?,?,?>>
					extends Serializable,EjbPersistable,
								EjbWithCode,UtilsStatusFixedCode,UtilsWithSymbol,
								JeeslOptionRestDownload,EjbWithCodeGraphic<G>,
								UtilsStatus<S,L,D>
{	
	public enum Code{length,velocity,time,volume,temp}
}