package org.jeesl.factory.builder.io;

import org.jeesl.api.bean.callback.JeeslFileRepositoryCallback;
import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.controller.handler.system.io.fr.DefaultFileRepositoryHandler;
import org.jeesl.controller.handler.system.io.fr.JeeslFileStatusHandler;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.fr.EjbIoFrContainerFactory;
import org.jeesl.factory.ejb.system.io.fr.EjbIoFrMetaFactory;
import org.jeesl.factory.ejb.system.io.fr.EjbIoFrStorageFactory;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStatus;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileType;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class IoFileRepositoryFactoryBuilder<L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
											SYSTEM extends JeeslIoSsiSystem,
											STORAGE extends JeeslFileStorage<L,D,SYSTEM,ENGINE>,
											ENGINE extends UtilsStatus<ENGINE,L,D>,
											CONTAINER extends JeeslFileContainer<STORAGE,META>,
											META extends JeeslFileMeta<D,CONTAINER,TYPE,STATUS>,
											TYPE extends JeeslFileType<L,D,TYPE,?>,
											STATUS extends JeeslFileStatus<L,D,STATUS,?>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoFileRepositoryFactoryBuilder.class);

	private final Class<STORAGE> cStorage; public Class<STORAGE> getClassStorage() {return cStorage;}
	private final Class<ENGINE> cEngine; public Class<ENGINE> getClassEngine() {return cEngine;}
	private final Class<CONTAINER> cContainer; public Class<CONTAINER> getClassContainer() {return cContainer;}
	private final Class<META> cMeta; public Class<META> getClassMeta() {return cMeta;}
	private final Class<TYPE> cType; public Class<TYPE> getClassType() {return cType;}
	private final Class<STATUS> cStatus; public Class<STATUS> getClassStatus() {return cStatus;}
	
	public IoFileRepositoryFactoryBuilder(final Class<L> cL, final Class<D> cD,
								final Class<STORAGE> cStorage, final Class<ENGINE> cEngine,
								final Class<CONTAINER> cContainer, final Class<META> cMeta,
								final Class<TYPE> cType, final Class<STATUS> cStatus)
	{
		super(cL,cD);
		this.cStorage=cStorage;
		this.cEngine=cEngine;
		this.cContainer=cContainer;
		this.cMeta=cMeta;
		this.cType=cType;
		this.cStatus=cStatus;
	}
	
	public EjbIoFrStorageFactory<SYSTEM,STORAGE> ejbStorage() {return new EjbIoFrStorageFactory<SYSTEM,STORAGE>(cStorage);}
	public EjbIoFrContainerFactory<STORAGE,CONTAINER> ejbContainer() {return new EjbIoFrContainerFactory<>(cContainer);}
	public EjbIoFrMetaFactory<CONTAINER,META,TYPE> ejbMeta() {return new EjbIoFrMetaFactory<>(cMeta);}
	
	public DefaultFileRepositoryHandler<L,D,LOC,SYSTEM,STORAGE,ENGINE,CONTAINER,META,TYPE,STATUS> handler(JeeslIoFrFacade<L,D,SYSTEM,STORAGE,ENGINE,CONTAINER,META,TYPE> fFr, JeeslFileRepositoryCallback callback)
	{
		return new DefaultFileRepositoryHandler<>(fFr,this,callback);
	}
	
	public JeeslFileStatusHandler<META,STATUS> handlerStatus(JeeslIoFrFacade<L,D,SYSTEM,STORAGE,ENGINE,CONTAINER,META,TYPE> fFr){return new JeeslFileStatusHandler<META,STATUS>(fFr,this);}
}