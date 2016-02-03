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

public class EjbRevisionViewFactory<L extends UtilsLang,D extends UtilsDescription,
									RV extends UtilsRevisionView<L,D,RV,RM,RS,RE,RA>,
									RM extends UtilsRevisionMapping<L,D,RV,RM,RS,RE,RA>,
									RS extends UtilsRevisionScope<L,D,RV,RM,RS,RE,RA>,
									RE extends UtilsRevisionEntity<L,D,RV,RM,RS,RE,RA>,
									RA extends UtilsRevisionAttribute<L,D,RV,RM,RS,RE,RA>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionViewFactory.class);
	
	final Class<RV> cView;
    
	public EjbRevisionViewFactory(final Class<RV> cView)
	{       
        this.cView = cView;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RV extends UtilsRevisionView<L,D,RV,RM,RS,RE,RA>,
					RM extends UtilsRevisionMapping<L,D,RV,RM,RS,RE,RA>,
					RS extends UtilsRevisionScope<L,D,RV,RM,RS,RE,RA>,
					RE extends UtilsRevisionEntity<L,D,RV,RM,RS,RE,RA>,
					RA extends UtilsRevisionAttribute<L,D,RV,RM,RS,RE,RA>>
	EjbRevisionViewFactory<L,D,RV,RM,RS,RE,RA> factory(final Class<RV> cView)
	{
		return new EjbRevisionViewFactory<L,D,RV,RM,RS,RE,RA>(cView);
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