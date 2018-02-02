package org.jeesl.interfaces.model.system.io.cms;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.with.status.JeeslWithCategory;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoCms<L extends UtilsLang,D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								
								V extends JeeslIoCmsVisiblity,
								S extends JeeslIoCmsSection<L,S>,
								E extends JeeslIoCmsElement<L,D,CAT,?,V,S,EC,ET,C,M,LOC>,
								EC extends UtilsStatus<EC,L,D>,
								ET extends UtilsStatus<ET,L,D>,
								C extends JeeslIoCmsContent<L,D,V,S,E,EC,ET,C,M,LOC>,
								M extends UtilsStatus<M,L,D>,
								LOC extends UtilsStatus<LOC,L,D>>
		extends Serializable,EjbWithId,
				EjbSaveable,EjbRemoveable,EjbWithPosition,EjbWithLang<L>,JeeslWithCategory<L,D,CAT>
{	
	public enum Attributes{category,position}
	
	CAT getCategory();
	void setCategory(CAT category);
	
	S getRoot();
	void setRoot(S section);
	
	List<LOC> getLocales();
	void setLocales(List<LOC> locales);
	
	boolean getToc();
	void setToc(boolean toc);
}