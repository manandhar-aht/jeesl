package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.ssi.EjbIoSsiSystemFactory;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class IoSsiFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								SYSTEM extends JeeslIoSsiSystem>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoSsiFactoryBuilder.class);
	
	private final Class<SYSTEM> cSystem; public Class<SYSTEM> getClassSystem(){return cSystem;}
	
	public IoSsiFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<SYSTEM> cSystem)
	{
		super(cL,cD);
		this.cSystem=cSystem;
	}
	
	public EjbIoSsiSystemFactory<SYSTEM> ejbSystem()
	{
		return new EjbIoSsiSystemFactory<SYSTEM>(cSystem);
	}
}