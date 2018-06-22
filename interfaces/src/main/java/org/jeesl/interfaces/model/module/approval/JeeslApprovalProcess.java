package org.jeesl.interfaces.model.module.approval;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisibleParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslApprovalProcess <L extends UtilsLang, D extends UtilsDescription,
									CAT extends UtilsStatus<CAT,L,D>
									
									>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,EjbWithCode,
				EjbWithPositionVisibleParent,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{category}
	
	CAT getCategory();
	void setCategory(CAT category);
	
	String getCode();
	void setCode(String code);
	
}