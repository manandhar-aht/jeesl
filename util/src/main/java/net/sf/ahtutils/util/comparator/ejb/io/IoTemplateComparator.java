package net.sf.ahtutils.util.comparator.ejb.io;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.io.UtilsIoTemplate;

public class IoTemplateComparator<L extends UtilsLang,D extends UtilsDescription,
									IOT extends UtilsIoTemplate<L,D,IOT,IOTT,IOTC>,
									IOTT extends UtilsStatus<IOTT,L,D>,
									IOTC extends UtilsStatus<IOTC,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(IoTemplateComparator.class);

    public enum Type {position};

    public IoTemplateComparator()
    {
    	
    }
    
    public Comparator<IOT> factory(Type type)
    {
        Comparator<IOT> c = null;
        IoTemplateComparator<L,D,IOT,IOTT,IOTC> factory = new IoTemplateComparator<L,D,IOT,IOTT,IOTC>();
        switch (type)
        {
            case position: c = factory.new PositionCodeComparator();break;
        }

        return c;
    }

    private class PositionCodeComparator implements Comparator<IOT>
    {
        public int compare(IOT a, IOT b)
        {
			CompareToBuilder ctb = new CompareToBuilder();
			ctb.append(a.getCategory().getPosition(), b.getCategory().getPosition());
			ctb.append(a.getPosition(), b.getPosition());
			return ctb.toComparison();
        }
    }
}