package org.jeesl.interfaces.model.module.bb;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.interfaces.model.with.EjbWithRefId;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslBb<L extends UtilsLang, D extends UtilsDescription,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								BB extends JeeslBb<L,D,SCOPE,BB,USER>,
								USER extends EjbWithEmail>
						extends Serializable,
								EjbWithId,EjbWithRefId,
								EjbSaveable,
								EjbWithRecord
{	
	BB getParent();
	void setParent(BB bb);
	
	

}