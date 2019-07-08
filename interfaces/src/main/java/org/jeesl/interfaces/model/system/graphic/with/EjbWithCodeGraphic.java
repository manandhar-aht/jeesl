package org.jeesl.interfaces.model.system.graphic.with;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.with.EjbWithGraphic;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

public interface EjbWithCodeGraphic<G extends JeeslGraphic<?,?,?,?,?>> extends EjbWithGraphic<G>,EjbWithCode
{
}