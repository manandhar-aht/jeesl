package org.jeesl.interfaces.model.system.io.fr;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslFileStorage<L extends UtilsLang,D extends UtilsDescription,
									SYSTEM extends JeeslIoSsiSystem,
									ENGINE extends UtilsStatus<ENGINE,L,D>>
		extends Serializable,EjbWithId,
			EjbSaveable,EjbRemoveable,
			EjbWithCode,EjbWithPosition,EjbWithLang<L>,EjbWithDescription<D>
				
{	
	ENGINE getEngine();
	void setEngine(ENGINE engines);
	
	String getJson();
	void setJson(String json);
	
	Boolean getKeepRemoved();
	void setKeepRemoved(Boolean keepRemoved);
	
	Long getFileSizeLimit();
	void setFileSizeLimit(Long fileSizeLimit);
}