package org.jeesl.api.facade;

import javax.naming.NamingException;

public interface JeeslFacadeLookup 
{	
	public <F extends Object> F lookup(Class<F> facade) throws NamingException;
}