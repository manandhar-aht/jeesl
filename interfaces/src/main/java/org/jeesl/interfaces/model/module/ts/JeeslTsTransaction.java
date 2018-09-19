package org.jeesl.interfaces.model.module.ts;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslTsTransaction <SOURCE extends EjbWithLangDescription<?,?>, 
								DATA extends JeeslTsData<?,?,?,?>,
								USER extends EjbWithId>
		extends Serializable,EjbWithId,EjbSaveable,EjbWithRecord,EjbRemoveable,EjbPersistable
{
	enum Attributes{user,record}
	
	USER getUser();
	void setUser(USER user);

	String getRemark();
	void setRemark(String remark);
	
	SOURCE getSource();
	void setSource(SOURCE source);
	
	List<DATA> getDatas();
	void setDatas(List<DATA> datas);
	
	String getReference();
	void setReference(String reference);
}