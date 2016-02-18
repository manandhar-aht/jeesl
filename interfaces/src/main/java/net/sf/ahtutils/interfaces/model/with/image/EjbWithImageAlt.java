package net.sf.ahtutils.interfaces.model.with.image;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithImageAlt extends EjbWithId
{
	String getImageAlt();
	void setImageAlt(String image);
}