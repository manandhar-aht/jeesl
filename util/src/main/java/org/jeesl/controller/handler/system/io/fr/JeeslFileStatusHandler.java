package org.jeesl.controller.handler.system.io.fr;

import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStatus;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileType;
import org.jeesl.util.db.cache.EjbCodeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslFileStatusHandler<META extends JeeslFileMeta<?,?,?,STATUS>,
									STATUS extends JeeslFileStatus<?,?,STATUS,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslFileStatusHandler.class);

	private final EjbCodeCache<STATUS> cache;
	
	public JeeslFileStatusHandler(IoFileRepositoryFactoryBuilder<?,?,?,?,?,?,META,?,STATUS> fbFile,
								JeeslIoFrFacade<?,?,?,?,?,META,?> fFr)
	{
		cache = new EjbCodeCache<STATUS>(fFr,fbFile.getClassStatus());
	}
	
	public void updateStatus(META meta)
	{
//		JeeslFileType.Code code = buildType(meta.getFileName().trim().toLowerCase());
//		meta.setType(cache.ejb(code));
		logger.info("Updated File TYPE for "+meta.getFileName()+": "+meta.getType().getCode());
	}
}