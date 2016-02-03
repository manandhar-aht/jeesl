package net.sf.ahtutils.factory.ejb.ts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsData;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsEntity;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsEntityClass;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsScope;

public class EjbTimeSeriesClassFactory<L extends UtilsLang,
											D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											ENTITY extends UtilsTsEntity<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											EC extends UtilsTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											INT extends UtilsStatus<INT,L,D>,
											DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
											WS extends UtilsStatus<WS,L,D>,
											QAF extends UtilsStatus<QAF,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTimeSeriesClassFactory.class);
	
	final Class<EC> cEc;
    
	public EjbTimeSeriesClassFactory(final Class<EC> cEc)
	{       
        this.cEc=cEc;
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					CAT extends UtilsStatus<CAT,L,D>,
					SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
					ENTITY extends UtilsTsEntity<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
					EC extends UtilsTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
					INT extends UtilsStatus<INT,L,D>,
					DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>,
					WS extends UtilsStatus<WS,L,D>,
					QAF extends UtilsStatus<QAF,L,D>>
	EjbTimeSeriesClassFactory<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF> factory(final Class<EC> cEc)
	{
		return new EjbTimeSeriesClassFactory<L,D,CAT,SCOPE,UNIT,TS,ENTITY,EC,INT,DATA,WS,QAF>(cEc);
	}
    
	public EC build()
	{
		EC ejb = null;
		try
		{
			ejb = cEc.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}