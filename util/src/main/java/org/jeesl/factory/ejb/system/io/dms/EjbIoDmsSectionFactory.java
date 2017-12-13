package org.jeesl.factory.ejb.system.io.dms;

import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbIoDmsSectionFactory <S extends JeeslIoDmsSection<?,?,S>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoDmsSectionFactory.class);
	
	private final Class<S> cSection;

	public EjbIoDmsSectionFactory(final Class<S> cSection)
	{
        this.cSection = cSection;
	}
 
	public S build(S parent)
	{
		S ejb = null;
		try
		{
			ejb = cSection.newInstance();
			ejb.setSection(parent);
			ejb.setPosition(1);
			ejb.setVisible(true);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public void update(S src, S dst)
	{
		dst.setSection(src.getSection());
		dst.setPosition(src.getPosition());
//		dst.setName(src.getName());
	}
}