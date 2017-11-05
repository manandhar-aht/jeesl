package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.log.EjbLogFactory;
import org.jeesl.factory.ejb.module.log.EjbLogItemFactory;
import org.jeesl.interfaces.model.module.log.JeeslLog;
import org.jeesl.interfaces.model.module.log.JeeslLogItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class LogFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
								LOG extends JeeslLog<L,D,LOG,ITEM,IMPACT,SCOPE,USER>,
								ITEM extends JeeslLogItem<L,D,LOG,ITEM,IMPACT,SCOPE,USER>,
								IMPACT extends UtilsStatus<IMPACT,L,D>,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								USER extends EjbWithId
								>
	extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(LogFactoryBuilder.class);
	
	private final Class<LOG> cLog;
	private final Class<ITEM> cItem;

	public LogFactoryBuilder(final Class<L> cL,final Class<D> cD,final Class<LOG> cLog, final Class<ITEM> cItem)
	{       
		super(cL,cD);
        this.cLog = cLog;
        this.cItem = cItem;
	}
	
	public EjbLogFactory<L,D,LOG,ITEM,IMPACT,SCOPE,USER> log(){return new EjbLogFactory<L,D,LOG,ITEM,IMPACT,SCOPE,USER>(cLog);}
	public EjbLogItemFactory<L,D,LOG,ITEM,IMPACT,SCOPE,USER> item(){return new EjbLogItemFactory<L,D,LOG,ITEM,IMPACT,SCOPE,USER>(cItem);}
}