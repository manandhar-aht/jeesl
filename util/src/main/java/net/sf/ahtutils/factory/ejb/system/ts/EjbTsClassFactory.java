package net.sf.ahtutils.factory.ejb.system.ts;

import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbTsClassFactory<L extends UtilsLang, D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
								UNIT extends UtilsStatus<UNIT,L,D>,
								TS extends JeeslTimeSeries<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
								BRIDGE extends JeeslTsBridge<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
								EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
								INT extends UtilsStatus<INT,L,D>,
								DATA extends JeeslTsData<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
								WS extends UtilsStatus<WS,L,D>,
								QAF extends UtilsStatus<QAF,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTsClassFactory.class);
	
	final Class<EC> cEc;
    
	public EjbTsClassFactory(final Class<EC> cEc)
	{       
        this.cEc=cEc;
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					CAT extends UtilsStatus<CAT,L,D>,
					SCOPE extends JeeslTsScope<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					TS extends JeeslTimeSeries<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
					BRIDGE extends JeeslTsBridge<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
					EC extends JeeslTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
					INT extends UtilsStatus<INT,L,D>,
					DATA extends JeeslTsData<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
					WS extends UtilsStatus<WS,L,D>,
					QAF extends UtilsStatus<QAF,L,D>>
	EjbTsClassFactory<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF> factory(final Class<EC> cEc)
	{
		return new EjbTsClassFactory<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>(cEc);
	}
    
	public EC build(CAT category)
	{
		EC ejb = null;
		try
		{
			ejb = cEc.newInstance();
			ejb.setPosition(1);
			ejb.setVisible(true);
			ejb.setCategory(category);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}