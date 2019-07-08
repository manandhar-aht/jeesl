package org.jeesl.interfaces.model.system.io.fr;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.status.JeeslWithStatus;
import org.jeesl.interfaces.model.with.status.JeeslWithType;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.with.EjbWithSize;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslFileMeta<D extends UtilsDescription,
								CONTAINER extends JeeslFileContainer<?,?>,
								TYPE extends JeeslFileType<?,D,TYPE,?>,
								STATUS extends JeeslFileStatus<?,D,STATUS,?>
>
			extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
					EjbWithParentAttributeResolver,
					EjbWithPosition,
					EjbWithCode,
//					EjbWithName,
					JeeslWithType<TYPE>,JeeslWithStatus<STATUS>,
					EjbWithSize,EjbWithRecord,
					EjbWithDescription<D>
{
	public enum Attributes{container,type}
	
	CONTAINER getContainer();
	void setContainer(CONTAINER container);
	
	String getMd5Hash();
	void setMd5Hash(String md5Hash);
	
	String getFileName();
	void setFileName(String fileName);
	
	String getCategory();
	void setCategory(String category);
}