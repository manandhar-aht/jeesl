package org.jeesl.doc.ofx.dev.srs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.jeesl.doc.ofx.dev.srs.section.OfxSrsSectionFactory;
import org.jeesl.model.xml.dev.srs.Chapter;
import org.jeesl.model.xml.dev.srs.Srs;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.status.Translations;

public class OfxSrsFrFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxSrsFrFactory.class);
	
	public OfxSrsFrFactory(Configuration config, String[] langs, Translations translations)
	{
		super(config,langs,translations);
	}
	
	public Section build(Srs... srs) throws OfxAuthoringException, UtilsConfigurationException
	{
		Section section = XmlSectionFactory.build();
		section.getContent().add(XmlTitleFactory.build("Functional Requireemnts"));
		
		if(srs.length==1){section.getContent().addAll(OfxSrsFrFactory.buildModule(srs[0]));}
		else if(srs.length>1)
		{
			List<Srs> list = new ArrayList<Srs>();
			for(Srs sr : srs){list.add(sr);}
			section.getContent().addAll(OfxSrsFrFactory.buildModules(list));
		}
		return section;
	}
	
	public static List<Section> buildModules(List<Srs> list) throws OfxAuthoringException, UtilsConfigurationException 
	{
		List<Section> sections = new ArrayList<Section>();
		for(Srs srs : list)
		{
			Section section = XmlSectionFactory.build();
			section.getContent().add(XmlTitleFactory.build("Module"));
			
			section.getContent().addAll(buildModule(srs));
			sections.add(section);
		}
		
		return sections;
	}
	
	public static List<Section> buildModule(Srs srs) throws OfxAuthoringException, UtilsConfigurationException 
	{
		List<Section> sections = new ArrayList<Section>();
		for(Chapter chapter : srs.getChapter())
		{
			Section section = XmlSectionFactory.build();
			section.getContent().add(XmlTitleFactory.build(chapter.getTitle()));
			
			for(Serializable s : chapter.getContent())
			{
				if(s instanceof Chapter){section.getContent().add(OfxSrsSectionFactory.build((Chapter)s));}
			}
			sections.add(section);
		}
		return sections;
	}
}