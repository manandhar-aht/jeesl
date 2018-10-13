package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.cms.EjbIoCmsContentFactory;
import org.jeesl.factory.ejb.system.io.cms.EjbIoCmsElementFactory;
import org.jeesl.factory.ejb.system.io.cms.EjbIoCmsFactory;
import org.jeesl.factory.ejb.system.io.cms.EjbIoCmsSectionFactory;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
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
								E extends JeeslIoCmsElement<V,S,EC,ET,C,FC>,
								EC extends UtilsStatus<EC,L,D>,
								ET extends UtilsStatus<ET,L,D>,
								C extends JeeslIoCmsContent<V,E,MT>,
								MT extends UtilsStatus<MT,L,D>,
								FC extends JeeslFileContainer<?,?>
								>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoCmsFactoryBuilder.class);

	private final Class<LOC> cLoc; public Class<LOC> getClassLocale() {return cLoc;}
	private final Class<CAT> cCategory; public Class<CAT> getClassCategory() {return cCategory;}
	private final Class<CMS> cCms; public Class<CMS> getClassCms() {return cCms;}
	private final Class<S> cSection; public Class<S> getClassSection() {return cSection;}
	private final Class<E> cElement; public Class<E> getClassElement() {return cElement;}
	private final Class<EC> cElementCategory; public Class<EC> getClassElementCategory() {return cElementCategory;}
	private final Class<ET> cElementType; public Class<ET> getClassElementType() {return cElementType;}
	
	private final Class<C> cContent;
	
	private final Class<MT> cMarkupType; public Class<MT> getClassMarkupType() {return cMarkupType;}
    
	public IoCmsFactoryBuilder(final Class<L> cL, final Class<D> cD,final Class<LOC> cLoc,
				final Class<CAT> cCategory, final Class<CMS> cCms,
				final Class<S> cSection,
				final Class<E> cElement,final Class<EC> cElementCategory,final Class<ET> cElementType,
				final Class<C> cContent,
				final Class<MT> cMarkupType)
	{
		super(cL,cD);
		this.cLoc=cLoc;
		this.cCategory=cCategory;
		this.cCms=cCms;
		this.cSection=cSection;
		this.cElement=cElement;
		this.cElementCategory=cElementCategory;
		this.cElementType=cElementType;
		this.cContent=cContent;
		
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
	
	public EjbIoCmsElementFactory<L,S,E> ejbElement()
	{
		return new EjbIoCmsElementFactory<L,S,E>(cElement);
	}
	
	public EjbIoCmsContentFactory<LOC,E,C,MT> ejbContent()
	{
		return new EjbIoCmsContentFactory<LOC,E,C,MT>(cContent);
	}
}