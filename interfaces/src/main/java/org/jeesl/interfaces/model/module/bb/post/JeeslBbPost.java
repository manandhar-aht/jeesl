package org.jeesl.interfaces.model.module.bb.post;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.bb.JeeslBbBoard;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslBbPost<BB extends JeeslBbBoard<?,?,?,BB,?,?,USER>,
								USER extends EjbWithEmail>
						extends Serializable,
								EjbWithId,
								EjbSaveable
{	
	public enum Attributes{scope,refId}
	
	
	
	
}