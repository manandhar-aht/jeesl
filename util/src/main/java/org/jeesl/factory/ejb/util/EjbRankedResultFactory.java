package org.jeesl.factory.ejb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.ranking.UtilsRankedResult;
import net.sf.ahtutils.model.DefaultRankedResult;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbRankedResultFactory <T extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRankedResultFactory.class);
	
	final Class<T> c;
	
    public EjbRankedResultFactory(final Class<T> c)
    {
        this.c = c;
    } 
    
    public static <T extends EjbWithId> EjbRankedResultFactory<T> factory(final Class<T> c)
    {
        return new EjbRankedResultFactory<T>(c);
    }
    	    
	public UtilsRankedResult<T> build(double ranking, T entity)
	{
		UtilsRankedResult<T> urr = new DefaultRankedResult<T>();
		urr.setRanking(ranking);
		urr.setEntity(entity);
		return urr;
    }
}