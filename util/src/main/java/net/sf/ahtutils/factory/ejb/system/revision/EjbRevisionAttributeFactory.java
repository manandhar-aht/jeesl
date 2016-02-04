package net.sf.ahtutils.factory.ejb.system.revision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;

public class EjbRevisionAttributeFactory<L extends UtilsLang,D extends UtilsDescription,
									RV extends UtilsRevisionView<L,D,RV,RVM,RS,RE,REM,RA>,
									RVM extends UtilsRevisionViewMapping<L,D,RV,RVM,RS,RE,REM,RA>,
									RS extends UtilsRevisionScope<L,D,RV,RVM,RS,RE,REM,RA>,
									RE extends UtilsRevisionEntity<L,D,RV,RVM,RS,RE,REM,RA>,
									REM extends UtilsRevisionEntityMapping<L,D,RV,RVM,RS,RE,REM,RA>,
									RA extends UtilsRevisionAttribute<L,D,RV,RVM,RS,RE,REM,RA>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionAttributeFactory.class);
	
	final Class<RA> cAttribute;
    
	public EjbRevisionAttributeFactory(final Class<RA> cAttribute)
	{       
        this.cAttribute = cAttribute;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RV extends UtilsRevisionView<L,D,RV,RVM,RS,RE,REM,RA>,
					RVM extends UtilsRevisionViewMapping<L,D,RV,RVM,RS,RE,REM,RA>,
					RS extends UtilsRevisionScope<L,D,RV,RVM,RS,RE,REM,RA>,
					RE extends UtilsRevisionEntity<L,D,RV,RVM,RS,RE,REM,RA>,
					REM extends UtilsRevisionEntityMapping<L,D,RV,RVM,RS,RE,REM,RA>,
					RA extends UtilsRevisionAttribute<L,D,RV,RVM,RS,RE,REM,RA>>
	EjbRevisionAttributeFactory<L,D,RV,RVM,RS,RE,REM,RA> factory(final Class<RA> cAttribute)
	{
		return new EjbRevisionAttributeFactory<L,D,RV,RVM,RS,RE,REM,RA>(cAttribute);
	}
    
	public RA build(RE entity)
	{
		RA ejb = null;
		try
		{
			ejb = cAttribute.newInstance();
			ejb.setPosition(0);
			ejb.setVisible(true);
			ejb.setEntity(entity);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}