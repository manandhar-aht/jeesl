package net.sf.ahtutils.factory.ejb.system.revision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;

public class EjbRevisionEntityFactory<L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,
									RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA>,
									RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
									RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA>,
									RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA>,
									REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
									RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionEntityFactory.class);
	
	final Class<RE> cEntity;
    
	public EjbRevisionEntityFactory(final Class<RE> cEntity)
	{       
        this.cEntity = cEntity;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RC extends UtilsStatus<RC,L,D>,
					RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA>,
					RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
					RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA>,
					RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA>,
					REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
					RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA>>
	EjbRevisionEntityFactory<L,D,RC,RV,RVM,RS,RE,REM,RA> factory(final Class<RE> cEntity)
	{
		return new EjbRevisionEntityFactory<L,D,RC,RV,RVM,RS,RE,REM,RA>(cEntity);
	}
    
	public RE build()
	{
		RE ejb = null;
		try
		{
			ejb = cEntity.newInstance();
			ejb.setPosition(0);
			ejb.setVisible(true);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}