package org.jeesl.interfaces.model.system.io.cms;

import java.io.Serializable;
import java.util.Map;

import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslWithFileRepositoryContainer;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoCmsElement<
									V extends JeeslIoCmsVisiblity,
									S extends JeeslIoCmsSection<?,S>,
									EC extends UtilsStatus<EC,?,?>,
									ET extends UtilsStatus<ET,?,?>,
									C extends JeeslIoCmsContent<V,?,?>,
									FC extends JeeslFileContainer<?,?>>
		extends Serializable,EjbWithId,EjbPersistable,EjbSaveable,EjbRemoveable,EjbWithPositionParent,
				JeeslWithFileRepositoryContainer<FC>
{	
	public enum Attributes{section}
	
	public enum Type{paragraph,statusTable}
	
	S getSection();
	void setSection(S section);
	
	ET getType();
	void setType(ET type);
	
	public Map<String,C> getContent();
	public void setContent(Map<String,C> content);
	
	String getJson();
	void setJson(String json);
}