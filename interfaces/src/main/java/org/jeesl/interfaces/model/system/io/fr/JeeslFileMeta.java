package org.jeesl.interfaces.model.system.io.fr;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithSize;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslFileMeta<CONTAINER extends JeeslFileContainer<?,?>,
								TYPE extends UtilsStatus<TYPE,?,?>>
			extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
					EjbWithParentAttributeResolver,
//					EjbWithPosition,
					EjbWithCode,
//					EjbWithName,
					EjbWithSize,EjbWithRecord
{
	public enum Attributes{container}
	
	public Integer getPosition();
	public void setPosition(Integer position);
	
	CONTAINER getContainer();
	void setContainer(CONTAINER container);
	
	TYPE getType();
	void setType(TYPE type);
	
	String getMd5Hash();
	void setMd5Hash(String md5Hash);
	
	String getFileName();
	void setFileName(String fileName);
	
	String getCategory();
	void setCategory(String category);
}