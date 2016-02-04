package net.sf.ahtutils.interfaces.model.system.revision;

import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsRevisionEntity<L extends UtilsLang,D extends UtilsDescription,
									RV extends UtilsRevisionView<L,D,RV,RVM,RS,RE,RA>,
									RVM extends UtilsRevisionViewMapping<L,D,RV,RVM,RS,RE,RA>,
									RS extends UtilsRevisionScope<L,D,RV,RVM,RS,RE,RA>,
									RE extends UtilsRevisionEntity<L,D,RV,RVM,RS,RE,RA>,
									RA extends UtilsRevisionAttribute<L,D,RV,RVM,RS,RE,RA>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithPositionVisible,
		EjbWithLang<L>,EjbWithDescription<D>
{	
	RS getScope();
	void setScope(RS scope);
	
	List<RA> getAttributes();
	void setAttributes(List<RA> attributes);
}