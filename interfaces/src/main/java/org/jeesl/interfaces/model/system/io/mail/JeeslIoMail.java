package org.jeesl.interfaces.model.system.io.mail;

import java.util.Date;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoMail<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL>
								>
		extends EjbWithId,EjbSaveable,EjbRemoveable
{	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	Date getRecordSpool();
	void setRecordSpool(Date recordSpool);
	
	Date getRecordSent();
	void setRecordSent(Date recordSent);
	
	String getXml();
	void setXml(String xml);
}