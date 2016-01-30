package net.sf.ahtutils.controller.facade;

import javax.persistence.EntityManager;

import net.sf.ahtutils.interfaces.facade.UtilsTsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsScope;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsData;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class UtilsTsFacadeBean<L extends UtilsLang,
							D extends UtilsDescription,
							CAT extends UtilsStatus<CAT,L,D>,
							SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>,
							ENTITY extends EjbWithId,
							INT extends UtilsStatus<INT,L,D>,
							DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>,
							WS extends UtilsStatus<WS,L,D>>
					extends UtilsFacadeBean
					implements UtilsTsFacade<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>
{	
	public UtilsTsFacadeBean(EntityManager em)
	{
		super(em);
	}

}