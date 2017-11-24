package org.jeesl.factory.builder.system;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class StatusFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
								LOC extends UtilsStatus<LOC,L,D>>
	extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(StatusFactoryBuilder.class);
	
	private final Class<LOC> cLoc; public Class<LOC> getClassLocale(){return cLoc;}
	private final Class<?> cStatus; public Class<?> getClassStatus(){return cStatus;}
	
	public StatusFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<LOC> cLoc, final Class<?> cStatus)
	{       
		super(cL,cD);
		this.cLoc = cLoc;
		this.cStatus=cStatus;
	}
}