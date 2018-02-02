package org.jeesl.factory.ejb.system.io.cms;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.jeesl.interfaces.model.system.io.cms.JeeslWithCms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbIoCmsFactory <L extends UtilsLang,D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								CMS extends JeeslIoCms<L,D,CAT,V,S,E,EC,ET,C,M,LOC>,
								V extends JeeslIoCmsVisiblity,
								S extends JeeslIoCmsSection<L,S>,
								E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,EC,ET,C,M,LOC>,
								EC extends UtilsStatus<EC,L,D>,
								ET extends UtilsStatus<ET,L,D>,
								C extends JeeslIoCmsContent<L,D,CAT,CMS,V,S,E,EC,ET,C,M,LOC>,
								M extends UtilsStatus<M,L,D>,
								LOC extends UtilsStatus<LOC,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoCmsFactory.class);
	
	private final Class<CMS> cCms;

	public EjbIoCmsFactory(final Class<CMS> cCms)
	{
        this.cCms = cCms;
	}
 
	public CMS build(CAT category, S root)
	{
		CMS ejb = null;
		try
		{
			ejb = cCms.newInstance();
			ejb.setCategory(category);
			ejb.setRoot(root);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public <W extends JeeslWithCms<CMS>> List<CMS> toCms(List<W> list)
	{
		List<CMS> result = new ArrayList<CMS>();
		for(W w : list) {result.add(w.getCms());}
		return result;
	}
}