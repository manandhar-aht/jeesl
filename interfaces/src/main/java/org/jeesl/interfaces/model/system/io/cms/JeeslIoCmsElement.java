package org.jeesl.interfaces.model.system.io.cms;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoCmsElement<L extends UtilsLang,D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								CMS extends JeeslIoCms<L,D,CAT,CMS,V,S,E,T,C,M>,
								V extends JeeslIoCmsVisiblity<L,D,CAT,CMS,V,S,E,T,C,M>,
								S extends JeeslIoCmsSection<L,D,CAT,CMS,V,S,E,T,C,M>,
								E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,E,T,C,M>,
								T extends UtilsStatus<T,L,D>,
								C extends JeeslIoCmsContent<L,D,CAT,CMS,V,S,E,T,C,M>,
								M extends UtilsStatus<M,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithPositionParent
{	
	public enum Attributes{section}
	public enum Type{paragraph}
	
	S getSection();
	void setSection(S section);
	
	T getType();
	void setType(T type);
}