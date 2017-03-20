package org.jeesl.doc.ofx.dev.srs.section;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.jeesl.model.xml.dev.srs.Chapter;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.latex.builder.UtilsLatexQaDocumentationBuilder;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.doc.ofx.qa.table.OfxQaDurationFrCategoryTable;
import net.sf.ahtutils.doc.ofx.qa.table.OfxQaDurationFrSummaryTable;
import net.sf.ahtutils.doc.ofx.qa.table.OfxQaDurationGroupTable;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Groups;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.xml.JaxbUtil;

public class OfxSrsSectionFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxSrsSectionFactory.class);

	private OfxQaDurationFrSummaryTable ofSummary;
	private OfxQaDurationFrCategoryTable ofDuration;
	private OfxQaDurationGroupTable ofGroup;
	
	public OfxSrsSectionFactory(Configuration config, String[] langs, Translations translations)
	{
		super(config,langs,translations);
		ofSummary = new OfxQaDurationFrSummaryTable(config,langs,translations);
		ofDuration = new OfxQaDurationFrCategoryTable(config,langs,translations);
		ofGroup = new OfxQaDurationGroupTable(config,langs,translations);
	}
	
	public static Section build(Chapter chapter) throws OfxAuthoringException, UtilsConfigurationException
	{
		Section section = XmlSectionFactory.build();
		section.setId("section.srs.durations");
		section.setContainer(false);
		section.getContent().add(XmlTitleFactory.build(chapter.getTitle()));
		
		
		return section;
	}
}