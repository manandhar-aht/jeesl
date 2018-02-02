package org.jeesl.interfaces.model.system.io.cms;

import java.util.Map;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoCmsElement<L extends UtilsLang,D extends UtilsDescription,
									CAT extends UtilsStatus<CAT,L,D>,
									CMS extends JeeslIoCms<L,D,CAT,V,S,?,EC,ET,C,M,LOC>,
									V extends JeeslIoCmsVisiblity,
									S extends JeeslIoCmsSection<L,S>,
									
									EC extends UtilsStatus<EC,L,D>,
									ET extends UtilsStatus<ET,L,D>,
									C extends JeeslIoCmsContent<L,D,V,S,?,EC,ET,C,M,LOC>,
									M extends UtilsStatus<M,L,D>,
									LOC extends UtilsStatus<LOC,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithPositionParent
{	
	public enum Attributes{section}
	public enum Type{paragraph}
	
	S getSection();
	void setSection(S section);
	
	ET getType();
	void setType(ET type);
	
	public Map<String,C> getContent();
	public void setContent(Map<String,C> content);
	
	String getJson();
	void setJson(String json);
}