package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.cms.EjbIoCmsFactory;
import org.jeesl.factory.ejb.system.io.cms.EjbIoCmsSectionFactory;
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

public class IoCmsFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
								CAT extends UtilsStatus<CAT,L,D>,
								CMS extends JeeslIoCms<L,D,CAT,S,LOC>,
								V extends JeeslIoCmsVisiblity,
								S extends JeeslIoCmsSection<L,S>,
								E extends JeeslIoCmsElement<V,S,EC,ET,C,MT,LOC>,
								EC extends UtilsStatus<EC,L,D>,
								ET extends UtilsStatus<ET,L,D>,
								C extends JeeslIoCmsContent<L,D,V,S,E,EC,ET,C,MT,LOC>,
								MT extends UtilsStatus<MT,L,D>
								>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoCmsFactoryBuilder.class);

	private final Class<LOC> cLoc; public Class<LOC> getClassLocale() {return cLoc;}
	private final Class<CAT> cCategory; public Class<CAT> getClassCategory() {return cCategory;}
	private final Class<CMS> cCms; public Class<CMS> getClassCms() {return cCms;}
	private final Class<S> cSection; public Class<S> getClassSection() {return cSection;}
	
	private final Class<MT> cMarkupType; public Class<MT> getClassMarkupType() {return cMarkupType;}
    
	public IoCmsFactoryBuilder(final Class<L> cL, final Class<D> cD,final Class<LOC> cLoc,
				final Class<CAT> cCategory, final Class<CMS> cCms,
				final Class<S> cSection,
				final Class<MT> cMarkupType)
	{
		super(cL,cD);
		this.cLoc=cLoc;
		this.cCategory=cCategory;
		this.cCms=cCms;
		this.cSection=cSection;
		
		this.cMarkupType=cMarkupType;
	}
	
	public EjbIoCmsFactory<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,LOC> ejbCms()
	{
		return new EjbIoCmsFactory<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,LOC>(cCms);
	}
	
	public EjbIoCmsSectionFactory<L,S> ejbSection()
	{
		return new EjbIoCmsSectionFactory<L,S>(cSection);
	}
}