package org.jeesl.interfaces.model.system.io.revision;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;
import net.sf.ahtutils.interfaces.model.status.UtilsWithSymbol;

public interface JeeslRevisionEntityRelation <S extends UtilsStatus<S,L,D>,
												L extends UtilsLang, D extends UtilsDescription,
												G extends JeeslGraphic<L,D,G,?,?,?>>
		extends Serializable,EjbPersistable,UtilsWithSymbol,JeeslOptionRestDownload,EjbWithCodeGraphic<G>,UtilsStatusFixedCode
{	
	public enum Code{MtoO,OtoO,OtoM,MtoM}
}