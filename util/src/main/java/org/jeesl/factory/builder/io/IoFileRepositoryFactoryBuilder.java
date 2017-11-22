package org.jeesl.factory.builder.io;

import org.jeesl.controller.handler.FileRepositoryHandler;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.fr.EjbIoFrStorageFactory;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class IoFileRepositoryFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
											STORAGE extends JeeslFileStorage<L,D,ENGINE>,
											ENGINE extends UtilsStatus<ENGINE,L,D>,
											CONTAINER extends JeeslFileContainer<STORAGE,META>,
											META extends JeeslFileMeta<CONTAINER,TYPE>,
											TYPE extends UtilsStatus<TYPE,L,D>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoFileRepositoryFactoryBuilder.class);

	private final Class<STORAGE> cStorage; public Class<STORAGE> getClassStorage() {return cStorage;}
	private final Class<ENGINE> cEngine; public Class<ENGINE> getClassEngine() {return cEngine;}
	
	public IoFileRepositoryFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<STORAGE> cStorage, final Class<ENGINE> cEngine)
	{
		super(cL,cD);
		this.cStorage=cStorage;
		this.cEngine=cEngine;
	}
	
	public EjbIoFrStorageFactory<STORAGE> ejbStorage()
	{
		return new EjbIoFrStorageFactory<STORAGE>(cStorage);
	}
	
	public FileRepositoryHandler<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE> handler()
	{
		return new FileRepositoryHandler<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE>();
	}
}