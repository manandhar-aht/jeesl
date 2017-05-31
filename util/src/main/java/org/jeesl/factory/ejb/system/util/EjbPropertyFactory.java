package org.jeesl.factory.ejb.system.util;

import org.jeesl.interfaces.model.system.util.JeeslProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.utils.Property;

public class EjbPropertyFactory<L extends UtilsLang, D extends UtilsDescription,
								C extends UtilsStatus<C,L,D>,
								P extends JeeslProperty<L,D,C,P>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbPropertyFactory.class);
	
	final Class<P> cProperty;
    
	public EjbPropertyFactory(final Class<P> cProperty)
	{       
        this.cProperty = cProperty;
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					C extends UtilsStatus<C,L,D>,
					P extends JeeslProperty<L,D,C,P>>
			EjbPropertyFactory<L,D,C,P> factory(final Class<P> cProperty)
	{
		return new EjbPropertyFactory<L,D,C,P>(cProperty);
	}
    
	public P build(Property property)
	{
		return build(property.getKey(),property.getValue(),property.isFrozen());
    }
	
	public P build(String code, String value){return build(code,value,false);}
	public P build(String code, String value, boolean frozen)
	{
		P ejb = null;
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