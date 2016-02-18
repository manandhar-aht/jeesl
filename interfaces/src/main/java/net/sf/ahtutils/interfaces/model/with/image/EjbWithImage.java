package net.sf.ahtutils.interfaces.model.with.image;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithImage extends EjbWithId
{
	String getImage();
	void setImage(String image);
}
