package org.jeesl.factory.ejb.system.constraint.algorithm;

import java.util.List;

import org.jeesl.interfaces.model.system.constraint.algorithm.JeeslConstraintAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbConstraintAlgorithmFactory <L extends UtilsLang, D extends UtilsDescription,
											ALGCAT extends UtilsStatus<ALGCAT,L,D>,
											ALGO extends JeeslConstraintAlgorithm<L,D,ALGCAT>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbConstraintAlgorithmFactory.class);
	
	private final Class<ALGO> cAlgorithm;
	
	public EjbConstraintAlgorithmFactory(final Class<ALGO> cAlgorithm)
	{
        this.cAlgorithm = cAlgorithm;
	}
	
	public ALGO build(ALGCAT category, List<ALGO> list)
	{
		ALGO ejb = null;
		try
		{
			ejb = cAlgorithm.newInstance();
			ejb.setPosition(0);
			ejb.setCategory(category);
			if(list==null) {ejb.setPosition(1);}else {ejb.setPosition(list.size()+1);}
			
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}