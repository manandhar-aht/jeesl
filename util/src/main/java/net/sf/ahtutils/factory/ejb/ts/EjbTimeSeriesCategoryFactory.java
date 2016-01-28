package net.sf.ahtutils.factory.ejb.ts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsCategory;
import net.sf.ahtutils.interfaces.model.ts.UtilsTsData;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbTimeSeriesCategoryFactory<L extends UtilsLang,
											D extends UtilsDescription,
											CAT extends UtilsTsCategory<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											TS extends UtilsTimeSeries<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>,
											ENTITY extends EjbWithId,
											INT extends UtilsStatus<INT,L,D>,
											DATA extends UtilsTsData<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTimeSeriesCategoryFactory.class);
	
	final Class<CAT> cCat;
    
	public EjbTimeSeriesCategoryFactory(final Class<CAT> cCat)
	{       
        this.cCat = cCat;
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					CAT extends UtilsTsCategory<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					TS extends UtilsTimeSeries<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>,
					ENTITY extends EjbWithId,
					INT extends UtilsStatus<INT,L,D>,
					DATA extends UtilsTsData<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>>
	EjbTimeSeriesCategoryFactory<L,D,CAT,UNIT,TS,ENTITY,INT,DATA> factory(final Class<CAT> cCat)
	{
		return new EjbTimeSeriesCategoryFactory<L,D,CAT,UNIT,TS,ENTITY,INT,DATA>(cCat);
	}
    
	public CAT build(UNIT unit)
	{
		CAT ejb = null;
		try
		{
			ejb = cCat.newInstance();
			ejb.setPosition(0);
			ejb.setVisible(true);
			ejb.setUnit(unit);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}