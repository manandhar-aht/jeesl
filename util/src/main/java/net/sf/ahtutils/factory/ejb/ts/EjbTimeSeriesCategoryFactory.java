package net.sf.ahtutils.factory.ejb.ts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsScope;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsData;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbTimeSeriesCategoryFactory<L extends UtilsLang,
											D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>,
											ENTITY extends EjbWithId,
											INT extends UtilsStatus<INT,L,D>,
											DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>,
											WS extends UtilsStatus<WS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTimeSeriesCategoryFactory.class);
	
	final Class<SCOPE> cScope;
    
	public EjbTimeSeriesCategoryFactory(final Class<SCOPE> cScope)
	{       
        this.cScope = cScope;
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					CAT extends UtilsStatus<CAT,L,D>,
					SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>,
					ENTITY extends EjbWithId,
					INT extends UtilsStatus<INT,L,D>,
					DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>,
					WS extends UtilsStatus<WS,L,D>>
	EjbTimeSeriesCategoryFactory<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS> factory(final Class<SCOPE> cScope)
	{
		return new EjbTimeSeriesCategoryFactory<L,D,CAT,SCOPE,UNIT,TS,ENTITY,INT,DATA,WS>(cScope);
	}
    
	public SCOPE build(UNIT unit)
	{
		SCOPE ejb = null;
		try
		{
			ejb = cScope.newInstance();
			ejb.setPosition(0);
			ejb.setVisible(true);
			ejb.setUnit(unit);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}