package org.jeesl.doc.ofx.cms;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.doc.ofx.cms.jeesl.JeeslCmsImageFactory;
import org.jeesl.doc.ofx.cms.jeesl.JeeslCmsParagraphFactory;
import org.jeesl.doc.ofx.cms.jeesl.JeeslCmsStatusTableFactory;
import org.jeesl.interfaces.controller.handler.JeeslFileRepositoryHandler;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
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

public abstract class AbstractCmsRenderer <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
								CAT extends UtilsStatus<CAT,L,D>,
								CMS extends JeeslIoCms<L,D,CAT,S,LOC>,
								V extends JeeslIoCmsVisiblity,
								S extends JeeslIoCmsSection<L,S>,
								E extends JeeslIoCmsElement<V,S,EC,ET,C,FC>,
								EC extends UtilsStatus<EC,L,D>,
								ET extends UtilsStatus<ET,L,D>,
								C extends JeeslIoCmsContent<V,E,MT>,
								MT extends UtilsStatus<MT,L,D>,
								FS extends JeeslFileStorage<L,D,?>,
								FC extends JeeslFileContainer<FS,?>,
								FM extends JeeslFileMeta<D,FC,?>
								>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractCmsRenderer.class);
	
	protected final JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,FC,LOC> fCms;
	
	private final JeeslCmsParagraphFactory<E,C> ofParagraph;
	private final JeeslCmsStatusTableFactory<E,C> ofTableStatus;
	private final JeeslCmsImageFactory<E,C,FS,FC,FM> ofImage;
	
	public AbstractCmsRenderer(JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,EC,ET,C,MT,FC,LOC> fCms, JeeslFileRepositoryHandler<FS,FC,FM> frh)
	{
		this.fCms = fCms;
		
		ofParagraph = new JeeslCmsParagraphFactory<E,C>();
		ofTableStatus = new JeeslCmsStatusTableFactory<E,C>();
		ofImage = new JeeslCmsImageFactory<E,C,FS,FC,FM>(frh);
	}
	
	public Sections build(String localeCode, CMS cms) throws OfxAuthoringException
	{
		logger.info("Rendering "+cms.toString());
		S root = fCms.load(cms.getRoot(),true);
		
		Sections xml = XmlSectionsFactory.build();
		for(S section : root.getSections())
		{
			if(section.isVisible())
			{
				xml.getContent().add(buildSection(localeCode, section));
			}
		}
		return xml;
	}
	
	public Section build(String localeCode, S section) throws OfxAuthoringException
	{
		S root = fCms.load(section,true);
		return buildSection(localeCode, root);
	}
 
	private Section buildSection(String localeCode, S section) throws OfxAuthoringException
	{
		Section xml = XmlSectionFactory.build();
		xml.getContent().add(XmlTitleFactory.build(section.getName().get(localeCode).getLang()));
		
		List<E> elements = fCms.fCmsElements(section);
		for(E e : elements)
		{
			build(localeCode,xml.getContent(),e);
//			xml.getContent().add(build(e));
		}
		
		for(S child : section.getSections())
		{
			if(child.isVisible())
			{
				xml.getContent().add(this.build(localeCode,child));
			}
		}
		
		return xml;
	}
	
	protected abstract void build(String localeCode, List<Serializable> list, E element) throws OfxAuthoringException;
	
	//Here we are handling all types which are available as generic renderer in JEESL 
	protected void buildJeesl(String localeCode, List<Serializable> list, E element) throws OfxAuthoringException
	{
		if(element.getType().getCode().equals(JeeslIoCmsElement.Type.paragraph.toString())) {list.addAll(ofParagraph.build(localeCode,element).getContent());}
		else if(element.getType().getCode().equals(JeeslIoCmsElement.Type.image.toString())) {list.add(ofImage.build(localeCode,element));}
		else if(element.getType().getCode().equals(JeeslIoCmsElement.Type.statusTable.toString())) {list.add(ofTableStatus.build(localeCode,element));}
		
		else {logger.warn("Unhandled "+element.getType().getCode());}
	}
}