package org.jeesl.controller.handler.system.io.fr;

import org.jeesl.api.bean.callback.JeeslFileRepositoryCallback;
import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStatus;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class DefaultFileRepositoryHandler<L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
									STORAGE extends JeeslFileStorage<L,D,ENGINE>,
									ENGINE extends UtilsStatus<ENGINE,L,D>,
									CONTAINER extends JeeslFileContainer<STORAGE,META>,
									META extends JeeslFileMeta<D,CONTAINER,TYPE,STATUS>,
									TYPE extends JeeslFileType<L,D,TYPE,?>,
									STATUS extends JeeslFileStatus<L,D,STATUS,?>>
	extends AbstractFileRepositoryHandler<L,D,LOC,STORAGE,ENGINE,CONTAINER,META,TYPE,STATUS>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(DefaultFileRepositoryHandler.class);

	public DefaultFileRepositoryHandler(JeeslIoFrFacade<L,D,STORAGE,ENGINE,CONTAINER,META,TYPE> fFr,
								IoFileRepositoryFactoryBuilder<L,D,LOC,STORAGE,ENGINE,CONTAINER,META,TYPE,STATUS> fbFile,
								JeeslFileRepositoryCallback callback)
	{
		super(fFr,fbFile,callback);
	}
}