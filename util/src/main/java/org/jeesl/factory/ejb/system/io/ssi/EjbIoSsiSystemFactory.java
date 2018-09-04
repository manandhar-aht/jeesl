package org.jeesl.factory.ejb.system.io.ssi;

import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;

public class EjbIoSsiSystemFactory <SYSTEM extends JeeslIoSsiSystem>
{
	private final Class<SYSTEM> cSystem;

	public EjbIoSsiSystemFactory(final Class<SYSTEM> cSystem)
	{
        this.cSystem = cSystem;
	}
	
	public SYSTEM build(org.jeesl.model.xml.system.io.ssi.System system)
    {
        return create(system.getCode(),system.getName());
    }

	public SYSTEM build()
	{
		return create(null,null);
	}
	
	public SYSTEM create(String code, String name)
	{
		SYSTEM ejb = null;
		try
		{
			ejb = cSystem.newInstance();
	        ejb.setCode(code);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}    
}