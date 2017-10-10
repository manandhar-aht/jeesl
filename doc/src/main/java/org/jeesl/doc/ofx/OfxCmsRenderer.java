package org.jeesl.doc.ofx;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.jeesl.model.xml.jeesl.Container;
import org.openfuxml.content.layout.Font;
import org.openfuxml.content.list.Item;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Sections;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.table.Cell;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionsFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.ofx.list.XmlListItemFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.text.OfxTextFactory;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.preamble.LatexSrcBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class OfxCmsRenderer<L extends UtilsLang,D extends UtilsDescription,
							CAT extends UtilsStatus<CAT,L,D>,
							CMS extends JeeslIoCms<L,D,CAT,CMS,V,S,E,EC,ET,C,M,LOC>,
							V extends JeeslIoCmsVisiblity<L,D,CAT,CMS,V,S,E,EC,ET,C,M,LOC>,
							S extends JeeslIoCmsSection<L,D,CAT,CMS,V,S,E,EC,ET,C,M,LOC>,
							E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,E,EC,ET,C,M,LOC>,
							EC extends UtilsStatus<EC,L,D>,
							ET extends UtilsStatus<ET,L,D>,
							C extends JeeslIoCmsContent<L,D,CAT,CMS,V,S,E,EC,ET,C,M,LOC>,
							M extends UtilsStatus<M,L,D>,
							LOC extends UtilsStatus<LOC,L,D>>
{	
	final static Logger logger = LoggerFactory.getLogger(OfxCmsRenderer.class);
	
	private final File latexBaseDir;
	private ConfigurationProvider cp;
	
	public OfxCmsRenderer(ConfigurationProvider cp, File latexBaseDir)
	{
		this.latexBaseDir=latexBaseDir;
		this.cp=cp;
	}
	
	public void render(CMS cms, Sections sections) throws OfxConfigurationException, IOException, OfxAuthoringException, UtilsConfigurationException
	{
		Writer w = new PrintWriter(new File(latexBaseDir,"srs.tex"));
		
		LatexSrcBook srcBook = new LatexSrcBook(cp);
		srcBook.reportPackages();
		srcBook.graphicsPath("pdf","png");
		srcBook.chapterSectionMarks();
		logger.info("CMS: "+cms.getName().get("en").getLang());
		srcBook.fancyHeader("AHT", cms.getName().get("en").getLang());
		srcBook.fancyFooter("");
//		srcBook.draft(false);
		srcBook.hyphenation();
		
		srcBook.beginDocument();
		if(cms.getToc()) {srcBook.toc();}
		
		for(Section section : XmlSectionsFactory.toList(sections))
		{
			LatexSectionRenderer rSection = new LatexSectionRenderer(cp,0,srcBook);
			rSection.render(section);
			srcBook.addSectionRenderer(rSection);
		}
		
		srcBook.endDocument();
		srcBook.write(w);
//		jlr.copyPackages();
	}
}