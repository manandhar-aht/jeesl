package org.jeesl.interfaces.model.system.io.revision;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.with.EjbWithGraphic;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslRevisionAttributeType <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription,G extends JeeslGraphic<L,D,G,?,?,?>>
		extends Serializable,EjbPersistable,EjbWithCode,JeeslOptionRestDownload,EjbWithGraphic<G>
{

}