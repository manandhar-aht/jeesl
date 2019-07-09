package org.jeesl.controller.handler.system.io.fr;

import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStatus;
import org.jeesl.util.db.cache.EjbCodeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public class JeeslFileStatusHandler<META extends JeeslFileMeta<?,?,?,STATUS>,
									STATUS extends JeeslFileStatus<?,?,STATUS,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslFileStatusHandler.class);

	private final JeeslIoFrFacade<?,?,?,?,?,META,?> fFr;
	private final EjbCodeCache<STATUS> cache;
	
	public JeeslFileStatusHandler(JeeslIoFrFacade<?,?,?,?,?,META,?> fFr,
									IoFileRepositoryFactoryBuilder<?,?,?,?,?,?,META,?,STATUS> fbFile
								)
	{
		this.fFr=fFr;
		cache = new EjbCodeCache<STATUS>(fFr,fbFile.getClassStatus());
	}
	
	public void updateStatus(META meta)
	{
		try
		{
			byte[] bytes = fFr.loadFromFileRepository(meta);
			meta.setStatus(cache.ejb(JeeslFileStatus.Code.exists));
		}
		catch (UtilsNotFoundException e)
		{
			
			logger.error(e.getMessage());
			meta.setStatus(cache.ejb(JeeslFileStatus.Code.missing));
		}
		
//		logger.info("Updated File TYPE for "+meta.getFileName()+": "+meta.getType().getCode());
	}
}