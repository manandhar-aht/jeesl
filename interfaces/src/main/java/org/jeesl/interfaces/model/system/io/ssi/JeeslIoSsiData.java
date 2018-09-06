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
	
	public MAPPING getMapping();
	public void setMapping(MAPPING mapping);
	
	public LINK getLink();
	void setLink(LINK link);
	
	public String getJson();
	public void setJson(String json);
}