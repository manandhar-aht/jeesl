package org.jeesl.interfaces.model.system.io.ssi;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;

public interface JeeslIoSsiLink <S extends UtilsStatus<S,L,D>,
						L extends UtilsLang, D extends UtilsDescription,
						G extends JeeslGraphic<L,D,?,?,?>>
		extends Serializable,EjbPersistable,
				JeeslOptionRestDownload,UtilsStatusFixedCode,
				EjbWithCodeGraphic<G>,UtilsStatus<S,L,D>
{
	public static enum Code{unlinked,precondition,possible,linked,ignore};
}