package org.jeesl.interfaces.model.system.io.revision;

import java.io.Serializable;

public interface JeeslRevisionEntityRelation extends Serializable
{	
	public enum Code{MtoO,OtoO,OtoM,MtoM}
}