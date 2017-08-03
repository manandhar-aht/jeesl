package org.jeesl.interfaces.model.system.io.cms;

import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoCmsSection<L extends UtilsLang,D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								CMS extends JeeslIoCms<L,D,CAT,CMS,V,S,E,T,C,M>,
								V extends JeeslIoCmsVisiblity<L,D,CAT,CMS,V,S,E,T,C,M>,
								S extends JeeslIoCmsSection<L,D,CAT,CMS,V,S,E,T,C,M>,
								E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,E,T,C,M>,
								T extends UtilsStatus<T,L,D>,
								C extends JeeslIoCmsContent<L,D,CAT,CMS,V,S,E,T,C,M>,
								M extends UtilsStatus<M,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithPositionParent,EjbWithLang<L>
{	
	public enum Attributes{section}
	
	S getSection();
	void setSection(S section);
	
	List<S> getSections();
	void setSections(List<S> columns);
}