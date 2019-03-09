package org.jeesl.interfaces.model.module.bb;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.interfaces.model.with.EjbWithRefId;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslBb<L extends UtilsLang, D extends UtilsDescription,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								BB extends JeeslBb<L,D,SCOPE,BB,USER>,
								USER extends EjbWithEmail>
						extends Serializable,
								EjbWithId,EjbWithRefId,EjbWithPosition,
								EjbSaveable
{	
	public enum Attributes{scope,refId}
	
	BB getParent();
	void setParent(BB bb);
	
	SCOPE getScope();
	void setScope(SCOPE scope);
	
	
}