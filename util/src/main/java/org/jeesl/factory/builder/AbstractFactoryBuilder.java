package org.jeesl.factory.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class AbstractFactoryBuilder<L extends UtilsLang, D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractFactoryBuilder.class);
	
	private final Class<L> cL; public Class<L> getClassL() {return cL;}
	private final Class<D> cD; public Class<D> getClassD() {return cD;}
	
	public AbstractFactoryBuilder(final Class<L> cL, final Class<D> cD)
	{       
		this.cL=cL;
		this.cD=cD;
	}
}