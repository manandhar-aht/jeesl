package org.jeesl.interfaces.model.system.io.cms;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslWithCms<CMS extends JeeslIoCms<?,?,?,?,?,?,?,?,?,?,?,?>>
						extends EjbWithId
{
	public static String attributeCategory = "cms";
	
	CMS getCms();
	void setCms(CMS cms);
}