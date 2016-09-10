package net.sf.ahtutils.factory.ejb.util;

import org.jeesl.interfaces.model.system.util.JeeslProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.utils.Property;

public class EjbPropertyFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								PROPERTY extends JeeslProperty<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbPropertyFactory.class);
	
	final Class<PROPERTY> cProperty;
    
	public EjbPropertyFactory(final Class<PROPERTY> cProperty)
	{       
        this.cProperty = cProperty;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					PROPERTY extends JeeslProperty<L,D>>
			EjbPropertyFactory<L,D,CATEGORY,PROPERTY> factory(final Class<PROPERTY> cProperty)
	{
		return new EjbPropertyFactory<L,D,CATEGORY,PROPERTY>(cProperty);
	}
    
	public PROPERTY build(Property property)
	{
		return build(property.getKey(),property.getValue(),property.isFrozen());
    }
	
	public PROPERTY build(String code, String value){return build(code,value,false);}
	public PROPERTY build(String code, String value, boolean frozen)
	{
		PROPERTY ejb = null;
		try
		{
			ejb = cProperty.newInstance();
			ejb.setKey(code);
			ejb.setValue(value);
			ejb.setFrozen(frozen);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}