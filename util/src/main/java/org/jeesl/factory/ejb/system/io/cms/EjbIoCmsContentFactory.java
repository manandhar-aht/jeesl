package org.jeesl.factory.ejb.system.io.cms;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbIoCmsContentFactory<L extends UtilsLang,D extends UtilsDescription,
									CAT extends UtilsStatus<CAT,L,D>,
									CMS extends JeeslIoCms<L,D,CAT,V,S,M,LOC>,
									V extends JeeslIoCmsVisiblity,
									S extends JeeslIoCmsSection<L,S>,
									E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,EC,ET,C,M,LOC>,
									EC extends UtilsStatus<EC,L,D>,
									ET extends UtilsStatus<ET,L,D>,
									C extends JeeslIoCmsContent<L,D,V,S,E,EC,ET,C,M,LOC>,
									M extends UtilsStatus<M,L,D>,
									LOC extends UtilsStatus<LOC,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoCmsContentFactory.class);
	
    private final Class<C> cC;
	
    public EjbIoCmsContentFactory(final Class<C> cC)
    {
        this.cC = cC;
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,
					CAT extends UtilsStatus<CAT,L,D>,
					CMS extends JeeslIoCms<L,D,CAT,V,S,M,LOC>,
					V extends JeeslIoCmsVisiblity,
					S extends JeeslIoCmsSection<L,S>,
					E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,EC,ET,C,M,LOC>,
					EC extends UtilsStatus<EC,L,D>,
					ET extends UtilsStatus<ET,L,D>,
					C extends JeeslIoCmsContent<L,D,V,S,E,EC,ET,C,M,LOC>,
					M extends UtilsStatus<M,L,D>,
					LOC extends UtilsStatus<LOC,L,D>>
    			EjbIoCmsContentFactory<L,D,CAT,CMS,V,S,E,EC,ET,C,M,LOC> factory(final Class<C> cC)
    {
        return new EjbIoCmsContentFactory<L,D,CAT,CMS,V,S,E,EC,ET,C,M,LOC>(cC);
    }
	
	public C build(E element, LOC locale, String value, M markup)
	{
		C c = null;
		try
		{
			c = cC.newInstance();
			c.setElement(element);
			c.setLkey(locale.getCode());
		 	c.setLang(value);
		 	c.setMarkup(markup);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
   
		return c;
	}
}