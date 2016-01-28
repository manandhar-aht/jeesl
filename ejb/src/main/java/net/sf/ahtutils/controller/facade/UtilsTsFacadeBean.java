package net.sf.ahtutils.controller.facade;

import javax.persistence.EntityManager;

import net.sf.ahtutils.interfaces.facade.UtilsTsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsCategory;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsData;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class UtilsTsFacadeBean<L extends UtilsLang,
							D extends UtilsDescription,
							CAT extends UtilsTsCategory<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							TS extends UtilsTimeSeries<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>,
							ENTITY extends EjbWithId,
							INT extends UtilsStatus<INT,L,D>,
							DATA extends UtilsTsData<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>>
					extends UtilsFacadeBean
					implements UtilsTsFacade<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>
{	
	public UtilsTsFacadeBean(EntityManager em)
	{
		super(em);
	}

}