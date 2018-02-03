package org.jeesl.factory.ejb.system.io.cms;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbIoCmsSectionFactory <L extends UtilsLang,S extends JeeslIoCmsSection<L,S>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoCmsSectionFactory.class);
	
	private final Class<S> cS;

	public EjbIoCmsSectionFactory(final Class<S> cS)
	{
        this.cS = cS;
	}
 
	public S build() {return build(null);}
	public S build(S parent)
	{
		S ejb = null;
		try
		{
			ejb = cS.newInstance();
			ejb.setSection(parent);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public void update(S src, S dst)
	{
		dst.setSection(src.getSection());
		dst.setPosition(src.getPosition());
		dst.setName(src.getName());
	}
}