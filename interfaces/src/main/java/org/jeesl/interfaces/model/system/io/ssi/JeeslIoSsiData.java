package org.jeesl.interfaces.model.system.io.ssi;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoSsiData <MAPPING extends JeeslIoSsiMapping<?,?>,
									LINK extends UtilsStatus<LINK,?,?>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithCode
{	
	public enum Attributes{mapping,link}
	
	public MAPPING getMapping();
	public void setMapping(MAPPING mapping);
	
	public LINK getLink();
	void setLink(LINK link);
	
	String getJson();
	void setJson(String json);
	
	Long getLocalId();
	void setLocalId(Long meisId);
}