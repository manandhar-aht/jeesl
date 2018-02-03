package org.jeesl.doc.ofx.cms;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Sections;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionsFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public abstract class AbstractOfxCmsFactory <L extends UtilsLang,D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								CMS extends JeeslIoCms<L,D,CAT,S,LOC>,
								V extends JeeslIoCmsVisiblity,
								S extends JeeslIoCmsSection<L,S>,
								E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,EC,ET,C,M,LOC>,
								EC extends UtilsStatus<EC,L,D>,
								ET extends UtilsStatus<ET,L,D>,
								C extends JeeslIoCmsContent<L,D,V,S,E,EC,ET,C,M,LOC>,
								M extends UtilsStatus<M,L,D>,
								LOC extends UtilsStatus<LOC,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxCmsFactory.class);
	
	protected final String localeCode;
	protected final JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,EC,ET,C,M,LOC> fCms;
	
	private final OfxParagraphFactory<L,D,CAT,CMS,V,S,E,EC,ET,C,M,LOC> ofParagraph;
	
	public AbstractOfxCmsFactory(String localeCode, JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,EC,ET,C,M,LOC> fCms)
	{
		this.localeCode=localeCode;
		this.fCms = fCms;
		
		ofParagraph = new OfxParagraphFactory<L,D,CAT,CMS,V,S,E,EC,ET,C,M,LOC>(localeCode);
	}
	
	public Sections build(CMS cms) throws OfxAuthoringException
	{
		logger.info("Rendering "+cms.toString());
		S root = fCms.load(cms.getRoot(),true);
		
		Sections xml = XmlSectionsFactory.build();
		for(S section : root.getSections())
		{
			if(section.isVisible())
			{
				xml.getContent().add(build(section));
			}
		}
		return xml;
	}
 
	private Section build(S section) throws OfxAuthoringException
	{
		Section xml = XmlSectionFactory.build();
		xml.getContent().add(XmlTitleFactory.build(section.getName().get(localeCode).getLang()));
		
		List<E> elements = fCms.fCmsElements(section);
		for(E e : elements)
		{
			build(xml.getContent(),e);
//			xml.getContent().add(build(e));
		}
		
		for(S child : section.getSections())
		{
			if(child.isVisible())
			{
				xml.getContent().add(this.build(child));
			}
		}
		
		return xml;
	}
	
	protected abstract void build(List<Serializable> list, E element) throws OfxAuthoringException;
	
	protected void buildJeesl(List<Serializable> list, E element) throws OfxAuthoringException
	{
		if(element.getType().getCode().equals(JeeslIoCmsElement.Type.paragraph.toString())) {list.add(ofParagraph.build(element));}
		else {logger.warn("Unhandled "+element.getType().getCode());}
	}
}