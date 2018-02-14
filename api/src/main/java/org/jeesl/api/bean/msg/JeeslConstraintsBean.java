package org.jeesl.api.bean.msg;

import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.xml.system.ConstraintScope;

public interface JeeslConstraintsBean <CONSTRAINT extends JeeslConstraint<?,?,?,?,CONSTRAINT,?,?,?>>
{
	String getMessage(String category, String scope, String code, String lang);
	ConstraintScope getScope(String category, String scope, String lang);
	
	<SID extends Enum<SID>, CID extends Enum<CID>> CONSTRAINT get(SID sId, CID cId) throws UtilsNotFoundException;
	void update(CONSTRAINT constraint);
}