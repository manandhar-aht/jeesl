package org.jeesl.interfaces.model.module.attribute;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslAttributeCriteria<L extends UtilsLang, D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										TYPE extends UtilsStatus<TYPE,L,D>>
			extends Serializable,EjbWithId,
					EjbSaveable,EjbRemoveable,
					EjbWithNonUniqueCode,EjbWithPositionVisible,EjbWithPositionParent,
					EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{category,refId,position,type}
	public enum Types{text,bool,intNumber,doubleNumber,date,selectOne,selectMany,remark}
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	Long getRefId();
	void setRefId(Long refId);
	
	TYPE getType();
	void setType(TYPE type);
	
	Boolean getAllowEmpty();
	void setAllowEmpty(Boolean allowEmpty);
}