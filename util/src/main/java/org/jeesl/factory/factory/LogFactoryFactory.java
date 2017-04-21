package org.jeesl.factory.factory;

import org.jeesl.factory.ejb.module.log.EjbLogFactory;
import org.jeesl.interfaces.model.module.log.JeeslLog;
import org.jeesl.interfaces.model.module.log.JeeslLogItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class LogFactoryFactory<L extends UtilsLang, D extends UtilsDescription,
								LOG extends JeeslLog<L,D,LOG,ITEM,IMPACT,SCOPE>,
								ITEM extends JeeslLogItem<L,D,LOG,ITEM,IMPACT,SCOPE>,
								IMPACT extends UtilsStatus<IMPACT,L,D>,
								SCOPE extends UtilsStatus<SCOPE,L,D>
								>
{
	final static Logger logger = LoggerFactory.getLogger(LogFactoryFactory.class);
	
//	private final Class<L> cL;
//	private final Class<D> cD;
	private final Class<LOG> cLog;

    
	private LogFactoryFactory(final Class<L> cL,final Class<D> cD,final Class<LOG> cLog)
	{       
//		this.cL = cL;
//       this.cD = cD;
        this.cLog = cLog;
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					LOG extends JeeslLog<L,D,LOG,ITEM,IMPACT,SCOPE>,
					ITEM extends JeeslLogItem<L,D,LOG,ITEM,IMPACT,SCOPE>,
					IMPACT extends UtilsStatus<IMPACT,L,D>,
					SCOPE extends UtilsStatus<SCOPE,L,D>
					>
		LogFactoryFactory<L,D,LOG,ITEM,IMPACT,SCOPE> factory(final Class<L> cL,final Class<D> cD,final Class<LOG> cLog)
	{
		return new LogFactoryFactory<L,D,LOG,ITEM,IMPACT,SCOPE>(cL,cD,cLog);
	}
	
	public EjbLogFactory<L,D,LOG,ITEM,IMPACT,SCOPE> log(){return new EjbLogFactory<L,D,LOG,ITEM,IMPACT,SCOPE>(cLog);}
}