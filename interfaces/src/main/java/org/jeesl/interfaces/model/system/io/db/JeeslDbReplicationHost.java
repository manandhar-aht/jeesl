package org.jeesl.interfaces.model.system.io.db;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDescription;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslDbReplicationHost <L extends UtilsLang, D extends UtilsDescription,
									S extends UtilsStatus<S,L,D>,
									G extends JeeslGraphic<L,D,?,?,?>>
							extends Serializable,EjbPersistable,
									JeeslOptionRestDescription,
									EjbWithCodeGraphic<G>,
									UtilsStatus<S,L,D>
{	

}