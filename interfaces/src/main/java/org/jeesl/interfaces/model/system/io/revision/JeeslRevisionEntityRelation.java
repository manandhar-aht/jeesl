package org.jeesl.interfaces.model.system.io.revision;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsWithSymbol;

public interface JeeslRevisionEntityRelation extends Serializable,EjbPersistable,EjbWithCode,UtilsWithSymbol,JeeslOptionRestDownload
{	
	public enum Code{MtoO,OtoO,OtoM,MtoM}
}