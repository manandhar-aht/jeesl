package net.sf.ahtutils.controller.factory.ejb.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.system.mail.UtilsMailAddress;

public class EjbMailFactory<M extends UtilsMailAddress>
{
	final static Logger logger = LoggerFactory.getLogger(EjbMailFactory.class);
	
    final Class<M> mailClass;
	
    public EjbMailFactory(final Class<M> mailClass)
    {
        this.mailClass = mailClass;
    } 
    
    public static <M extends UtilsMailAddress> EjbMailFactory<M> factory(final Class<M> mailClass)
    {
        return new EjbMailFactory<M>(mailClass);
    }
    
    public M build(String email){return build(email,null);}
    public M build(String email, String name)
	{
		try
		{
			M l = mailClass.newInstance();
			l.setEmail(email);
			l.setName(name);
			return l;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		logger.error("Something went terribly wrong, see stacktrace. Unfortunately null is returned here!");
		return null;
	}
}