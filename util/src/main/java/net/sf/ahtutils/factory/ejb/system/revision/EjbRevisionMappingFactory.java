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

public class EjbRevisionMappingFactory<L extends UtilsLang,D extends UtilsDescription,
									RV extends UtilsRevisionView<L,D,RV,RVM,RS,RE,RA>,
									RVM extends UtilsRevisionViewMapping<L,D,RV,RVM,RS,RE,RA>,
									RS extends UtilsRevisionScope<L,D,RV,RVM,RS,RE,RA>,
									RE extends UtilsRevisionEntity<L,D,RV,RVM,RS,RE,RA>,
									RA extends UtilsRevisionAttribute<L,D,RV,RVM,RS,RE,RA>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionMappingFactory.class);
	
	final Class<RVM> cMapping;
    
	public EjbRevisionMappingFactory(final Class<RVM> cMapping)
	{       
        this.cMapping = cMapping;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RV extends UtilsRevisionView<L,D,RV,RVM,RS,RE,RA>,
					RVM extends UtilsRevisionViewMapping<L,D,RV,RVM,RS,RE,RA>,
					RS extends UtilsRevisionScope<L,D,RV,RVM,RS,RE,RA>,
					RE extends UtilsRevisionEntity<L,D,RV,RVM,RS,RE,RA>,
					RA extends UtilsRevisionAttribute<L,D,RV,RVM,RS,RE,RA>>
	EjbRevisionMappingFactory<L,D,RV,RVM,RS,RE,RA> factory(final Class<RVM> cMapping)
	{
		return new EjbRevisionMappingFactory<L,D,RV,RVM,RS,RE,RA>(cMapping);
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