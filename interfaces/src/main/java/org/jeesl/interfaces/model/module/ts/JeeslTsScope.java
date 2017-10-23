package org.jeesl.interfaces.model.module.ts;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisibleParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslTsScope <L extends UtilsLang, D extends UtilsDescription,
									CAT extends UtilsStatus<CAT,L,D>,
									UNIT extends UtilsStatus<UNIT,L,D>,
									EC extends JeeslTsEntityClass<L,D,CAT>,
									INT extends UtilsStatus<INT,L,D>
									>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,EjbWithCode,
				EjbWithPositionVisibleParent,EjbWithParentAttributeResolver,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Statistic{none}
	
	CAT getCategory();
	void setCategory(CAT category);
	
	UNIT getUnit();
	void setUnit(UNIT unit);
	
	String getCode();
	void setCode(String code);
	
	List<INT> getIntervals();
	void setIntervals(List<INT> intervals);
	
	List<EC> getClasses();
	void setClasses(List<EC> classes);
}