package net.sf.ahtutils.interfaces.model.system.revision;

import java.util.List;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsRevisionEntity<L extends UtilsLang,D extends UtilsDescription,
									RV extends UtilsRevisionView<L,D,RV,RM,RS,RE,RA>,
									RM extends UtilsRevisionMapping<L,D,RV,RM,RS,RE,RA>,
									RS extends UtilsRevisionScope<L,D,RV,RM,RS,RE,RA>,
									RE extends UtilsRevisionEntity<L,D,RV,RM,RS,RE,RA>,
									RA extends UtilsRevisionAttribute<L,D,RV,RM,RS,RE,RA>>
		extends EjbWithId//,EjbWithPositionVisible,
		//EjbWithLang<L>,EjbWithDescription<D>
{	
	RS getScope();
	void setScope(RS scope);
	
	String getFqcn();
	void setFqcn(String fqcn);
	
	List<RA> getAttributes();
	void setAttributes(List<RA> attributes);
}