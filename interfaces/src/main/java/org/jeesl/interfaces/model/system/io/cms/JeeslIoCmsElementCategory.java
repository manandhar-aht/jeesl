package org.jeesl.interfaces.model.system.io.cms;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;

public interface JeeslIoCmsElementCategory <L extends UtilsLang, D extends UtilsDescription,
										S extends UtilsStatus<S,L,D>,
										G extends JeeslGraphic<L,D,?,?,?>>
					extends Serializable,EjbPersistable,UtilsStatusFixedCode,
							UtilsStatus<S,L,D>,EjbWithCodeGraphic<G>
{	

}