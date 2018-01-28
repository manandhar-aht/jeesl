package org.jeesl.interfaces.model.system.constraint;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;

public interface JeeslConstraintType <S extends UtilsStatus<S,L,D>,L extends UtilsLang,D extends UtilsDescription,G extends JeeslGraphic<L,D,G,?,?,?>>
									extends Serializable,EjbPersistable,UtilsStatusFixedCode,JeeslOptionRestDownload,EjbWithCodeGraphic<G>
{
	public static String xmlResourceContainer = "jeesl/db/system/constraint/type.xml";
	
	public static enum Code{unique,precondition};
}