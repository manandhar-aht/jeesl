package org.jeesl.interfaces.model.module.feedback;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslFeedback<L extends UtilsLang, D extends UtilsDescription,
								FEEDBACK extends JeeslFeedback<L,D,FEEDBACK,STYLE,TYPE,USER>,
								STYLE extends UtilsStatus<STYLE,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								USER extends EjbWithEmail>
						extends EjbWithId,EjbWithRecord
{	
	STYLE getStyle();
	void setStyle(STYLE style);
	
	TYPE getType();
	void setType(TYPE type);
	
	USER getUser();
	void setUser(USER user);
	
	String getTxt();
	void setTxt(String txt);
}