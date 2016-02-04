package net.sf.ahtutils.factory.ejb.system.revision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;

public class EjbRevisionScopeFactory<L extends UtilsLang,D extends UtilsDescription,
									RV extends UtilsRevisionView<L,D,RV,RVM,RS,RE,RA>,
									RVM extends UtilsRevisionViewMapping<L,D,RV,RVM,RS,RE,RA>,
									RS extends UtilsRevisionScope<L,D,RV,RVM,RS,RE,RA>,
									RE extends UtilsRevisionEntity<L,D,RV,RVM,RS,RE,RA>,
									RA extends UtilsRevisionAttribute<L,D,RV,RVM,RS,RE,RA>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionScopeFactory.class);
	
	final Class<RS> cScope;
    
	public EjbRevisionScopeFactory(final Class<RS> cScope)
	{       
        this.cScope = cScope;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RV extends UtilsRevisionView<L,D,RV,RVM,RS,RE,RA>,
					RVM extends UtilsRevisionViewMapping<L,D,RV,RVM,RS,RE,RA>,
					RS extends UtilsRevisionScope<L,D,RV,RVM,RS,RE,RA>,
					RE extends UtilsRevisionEntity<L,D,RV,RVM,RS,RE,RA>,
					RA extends UtilsRevisionAttribute<L,D,RV,RVM,RS,RE,RA>>
	EjbRevisionScopeFactory<L,D,RV,RVM,RS,RE,RA> factory(final Class<RS> cScope)
	{
		return new EjbRevisionScopeFactory<L,D,RV,RVM,RS,RE,RA>(cScope);
	}
    
	public RS build()
	{
		RS ejb = null;
		try
		{
			ejb = cScope.newInstance();
			ejb.setPosition(0);
			ejb.setVisible(true);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}