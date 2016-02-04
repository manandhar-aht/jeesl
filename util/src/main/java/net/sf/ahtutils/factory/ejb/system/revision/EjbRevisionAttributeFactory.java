package net.sf.ahtutils.factory.ejb.system.revision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;

public class EjbRevisionAttributeFactory<L extends UtilsLang,D extends UtilsDescription,
									RV extends UtilsRevisionView<L,D,RV,RM,RS,RE,RA>,
									RM extends UtilsRevisionMapping<L,D,RV,RM,RS,RE,RA>,
									RS extends UtilsRevisionScope<L,D,RV,RM,RS,RE,RA>,
									RE extends UtilsRevisionEntity<L,D,RV,RM,RS,RE,RA>,
									RA extends UtilsRevisionAttribute<L,D,RV,RM,RS,RE,RA>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionAttributeFactory.class);
	
	final Class<RA> cAttribute;
    
	public EjbRevisionAttributeFactory(final Class<RA> cAttribute)
	{       
        this.cAttribute = cAttribute;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RV extends UtilsRevisionView<L,D,RV,RM,RS,RE,RA>,
					RM extends UtilsRevisionMapping<L,D,RV,RM,RS,RE,RA>,
					RS extends UtilsRevisionScope<L,D,RV,RM,RS,RE,RA>,
					RE extends UtilsRevisionEntity<L,D,RV,RM,RS,RE,RA>,
					RA extends UtilsRevisionAttribute<L,D,RV,RM,RS,RE,RA>>
	EjbRevisionAttributeFactory<L,D,RV,RM,RS,RE,RA> factory(final Class<RA> cAttribute)
	{
		return new EjbRevisionAttributeFactory<L,D,RV,RM,RS,RE,RA>(cAttribute);
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