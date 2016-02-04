package net.sf.ahtutils.factory.ejb.system.revision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;

public class EjbRevisionViewFactory<L extends UtilsLang,D extends UtilsDescription,
									RV extends UtilsRevisionView<L,D,RV,RVM,RS,RE,REM,RA>,
									RVM extends UtilsRevisionViewMapping<L,D,RV,RVM,RS,RE,REM,RA>,
									RS extends UtilsRevisionScope<L,D,RV,RVM,RS,RE,REM,RA>,
									RE extends UtilsRevisionEntity<L,D,RV,RVM,RS,RE,REM,RA>,
									REM extends UtilsRevisionEntityMapping<L,D,RV,RVM,RS,RE,REM,RA>,
									RA extends UtilsRevisionAttribute<L,D,RV,RVM,RS,RE,REM,RA>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionViewFactory.class);
	
	final Class<RV> cView;
    
	public EjbRevisionViewFactory(final Class<RV> cView)
	{       
        this.cView = cView;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RV extends UtilsRevisionView<L,D,RV,RVM,RS,RE,REM,RA>,
					RVM extends UtilsRevisionViewMapping<L,D,RV,RVM,RS,RE,REM,RA>,
					RS extends UtilsRevisionScope<L,D,RV,RVM,RS,RE,REM,RA>,
					RE extends UtilsRevisionEntity<L,D,RV,RVM,RS,RE,REM,RA>,
					REM extends UtilsRevisionEntityMapping<L,D,RV,RVM,RS,RE,REM,RA>,
					RA extends UtilsRevisionAttribute<L,D,RV,RVM,RS,RE,REM,RA>>
	EjbRevisionViewFactory<L,D,RV,RVM,RS,RE,REM,RA> factory(final Class<RV> cView)
	{
		return new EjbRevisionViewFactory<L,D,RV,RVM,RS,RE,REM,RA>(cView);
	}
    
	public RV build()
	{
		RV ejb = null;
		try
		{
			ejb = cView.newInstance();
			ejb.setPosition(0);
			ejb.setVisible(true);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}