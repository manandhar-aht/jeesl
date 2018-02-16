package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;

public interface JeeslSecurityPasswordStrength <S extends UtilsStatus<S,L,D>,
									L extends UtilsLang, D extends UtilsDescription,G extends JeeslGraphic<L,D,G,?,?,?>>
		extends Serializable,EjbPersistable,JeeslOptionRestDownload,UtilsStatusFixedCode,EjbWithCodeGraphic<G>
{
	public enum Code{s0,s1,s2,s3,s4}
}