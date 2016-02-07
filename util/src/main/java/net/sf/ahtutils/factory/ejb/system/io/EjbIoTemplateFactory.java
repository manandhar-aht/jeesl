package net.sf.ahtutils.factory.ejb.system.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.io.UtilsIoTemplate;

public class EjbIoTemplateFactory<L extends UtilsLang,D extends UtilsDescription,
									IOT extends UtilsIoTemplate<L,D,IOT,IOTT,IOTC>,
									IOTT extends UtilsStatus<IOTT,L,D>,
									IOTC extends UtilsStatus<IOTC,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoTemplateFactory.class);
	
	final Class<IOT> cIot;
    
	public EjbIoTemplateFactory(final Class<IOT> cIot)
	{       
        this.cIot = cIot;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					IOT extends UtilsIoTemplate<L,D,IOT,IOTT,IOTC>,
					IOTT extends UtilsStatus<IOTT,L,D>,
					IOTC extends UtilsStatus<IOTC,L,D>>
	EjbIoTemplateFactory<L,D,IOT,IOTT,IOTC> factory(final Class<IOT> cIot)
	{
		return new EjbIoTemplateFactory<L,D,IOT,IOTT,IOTC>(cIot);
	}
    
	public IOT build(IOTT type)
	{
		IOT ejb = null;
		try
		{
			ejb = cIot.newInstance();
			ejb.setPosition(1);
			ejb.setVisible(true);
			ejb.setType(type);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}