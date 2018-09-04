package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.ssi.EjbIoSsiDataFactory;
import org.jeesl.factory.ejb.system.io.ssi.EjbIoSsiSystemFactory;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class IoSsiFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								SYSTEM extends JeeslIoSsiSystem,
								MAPPING extends JeeslIoSsiMapping<SYSTEM,?>,
								DATA extends JeeslIoSsiData<MAPPING>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoSsiFactoryBuilder.class);
	
	private final Class<SYSTEM> cSystem; public Class<SYSTEM> getClassSystem(){return cSystem;}
	private final Class<MAPPING> cMapping; public Class<MAPPING> getClassMapping(){return cMapping;}
	private final Class<DATA> cData; public Class<DATA> getClassData(){return cData;}
	
	public IoSsiFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<SYSTEM> cSystem, final Class<MAPPING> cMapping, final Class<DATA> cData)
	{
		super(cL,cD);
		this.cSystem=cSystem;
		this.cMapping=cMapping;
		this.cData=cData;
	}
	
	public EjbIoSsiSystemFactory<SYSTEM> ejbSystem() {return new EjbIoSsiSystemFactory<SYSTEM>(cSystem);}
	public EjbIoSsiDataFactory<MAPPING,DATA> ejbData() {return new EjbIoSsiDataFactory<MAPPING,DATA>(cData);}
}