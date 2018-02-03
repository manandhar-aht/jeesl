package org.jeesl.interfaces.model.system.io.cms;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoCmsContent<L extends UtilsLang,D extends UtilsDescription,
								
								V extends JeeslIoCmsVisiblity,
								S extends JeeslIoCmsSection<L,S>,
								E extends JeeslIoCmsElement<V,S,EC,ET,C,MT,LOC>,
								EC extends UtilsStatus<EC,L,D>,
								ET extends UtilsStatus<ET,L,D>,
								C extends JeeslIoCmsContent<L,D,V,S,E,EC,ET,C,MT,LOC>,
								MT extends UtilsStatus<MT,L,D>,
								LOC extends UtilsStatus<LOC,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithParentAttributeResolver
{	
	public enum Attributes{element}
	public enum Markup{html}
	
	E getElement();
	void setElement(E element);
	
	String getLkey();
	void setLkey(String lkey);
	
	String getLang();
	void setLang(String name);
	
	MT getMarkup();
	void setMarkup(MT markup);
	
	boolean isFallback();
	void setFallback(boolean fallback);
}