package org.jeesl.interfaces.model.system.property;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;

public interface JeeslPropertyCategory <L extends UtilsLang, D extends UtilsDescription,
								S extends UtilsStatus<S,L,D>,
								G extends JeeslGraphic<L,D,G,?,?,?>>
						extends Serializable,EjbSaveable,UtilsStatusFixedCode,
								EjbWithCodeGraphic<G>,
								UtilsStatus<S,L,D>
{

}