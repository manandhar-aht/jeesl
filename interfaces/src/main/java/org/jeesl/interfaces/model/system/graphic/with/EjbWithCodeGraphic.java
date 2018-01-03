package org.jeesl.interfaces.model.system.graphic.with;

import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.with.EjbWithGraphic;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

public interface EjbWithCodeGraphic<G extends JeeslGraphic<?,?,G,?,?,?>> extends EjbWithGraphic<G>,EjbWithCode
{
}