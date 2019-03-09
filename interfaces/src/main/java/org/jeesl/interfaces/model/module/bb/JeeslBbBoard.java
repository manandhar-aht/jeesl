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
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslBbBoard<L extends UtilsLang, D extends UtilsDescription,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								BB extends JeeslBbBoard<L,D,SCOPE,BB,PUB,POST,USER>,
								PUB extends UtilsStatus<PUB,L,D>,
								POST extends JeeslBbPost<BB,USER>,
								USER extends EjbWithEmail>
						extends Serializable,
								EjbWithId,EjbWithRefId,EjbWithPosition,EjbWithName,
								EjbSaveable
{	
	public enum Attributes{scope,refId}
	
	BB getParent();
	void setParent(BB bb);
	
	SCOPE getScope();
	void setScope(SCOPE scope);
	
	String getDescription();
	void setDescription(String description);
	
	PUB getPublishing();
	void setPublishing(PUB publishing);
}