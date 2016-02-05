package net.sf.ahtutils.factory.ejb.system.revision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;

public class EjbRevisionMappingViewFactory<L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,
									RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA>,
									RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
									RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA>,
									RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA>,
									REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
									RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionMappingViewFactory.class);
	
	final Class<RVM> cMapping;
    
	public EjbRevisionMappingViewFactory(final Class<RVM> cMapping)
	{       
        this.cMapping = cMapping;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RC extends UtilsStatus<RC,L,D>,
					RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA>,
					RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
					RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA>,
					RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA>,
					REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA>,
					RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA>>
	EjbRevisionMappingViewFactory<L,D,RC,RV,RVM,RS,RE,REM,RA> factory(final Class<RVM> cMapping)
	{
		return new EjbRevisionMappingViewFactory<L,D,RC,RV,RVM,RS,RE,REM,RA>(cMapping);
	}
    
	public RVM build(RV view, RE entity, RS scope)
	{
		RVM ejb = null;
		try
		{
			ejb = cMapping.newInstance();
			ejb.setPosition(0);
			ejb.setVisible(true);
			ejb.setView(view);
			ejb.setEntity(entity);
			ejb.setScope(scope);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}