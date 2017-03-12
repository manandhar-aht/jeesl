package net.sf.ahtutils.util.comparator.ejb.ts;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
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

public class TsClassComparator<L extends UtilsLang, D extends UtilsDescription,
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
	final static Logger logger = LoggerFactory.getLogger(TsClassComparator.class);

    public enum Type {position};

    public TsClassComparator()
    {
    	
    }
    
    public Comparator<EC> factory(Type type)
    {
        Comparator<EC> c = null;
        TsClassComparator<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF> factory = new TsClassComparator<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>();
        switch (type)
        {
            case position: c = factory.new PositionCodeComparator();break;
        }

        return c;
    }

    private class PositionCodeComparator implements Comparator<EC>
    {
        public int compare(EC a, EC b)
        {
			CompareToBuilder ctb = new CompareToBuilder();
			ctb.append(a.getCategory().getPosition(), b.getCategory().getPosition());
			ctb.append(a.getPosition(), b.getPosition());
			return ctb.toComparison();
        }
    }
}