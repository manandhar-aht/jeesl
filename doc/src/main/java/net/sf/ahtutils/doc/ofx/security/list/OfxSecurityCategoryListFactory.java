package net.sf.ahtutils.doc.ofx.security.list;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.list.Item;
import org.openfuxml.content.list.List;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.list.OfxListFactory;
import org.openfuxml.factory.xml.list.OfxListItemFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.latex.content.list.LatexListRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.doc.ofx.util.OfxMultiLangFactory;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class OfxSecurityCategoryListFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxSecurityCategoryListFactory.class);
	
	private CrossMediaManager cmm;
	private DefaultSettingsManager dsm;
	private ConfigurationProvider cp;
	
	public OfxSecurityCategoryListFactory(Configuration config, String lang, Translations translations,CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		this(config,new String[] {lang},translations,cmm,dsm);
	}
	public OfxSecurityCategoryListFactory(Configuration config,String[] langs, Translations translations,CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(config,langs,translations);
		this.cmm=cmm;
		this.dsm=dsm;
	}

	public OfxSecurityCategoryListFactory(Configuration config,String[] langs, Translations translations,ConfigurationProvider cp)
	{
		super(config,langs,translations);
		this.cp=cp;
	}
	
	@Deprecated public String saveDescription(java.util.List<Category> categories) throws OfxAuthoringException
	{
		try
		{
			LatexListRenderer renderer = new LatexListRenderer(cp,false);
			renderer.render(create(categories),new LatexSectionRenderer(cp,0,null));
			StringWriter sw = new StringWriter();
			renderer.write(sw);
			return sw.toString();
		}
		catch (IOException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	
	public String saveDescriptionSec(java.util.List<net.sf.ahtutils.xml.security.Category> categories) throws OfxAuthoringException
	{
		try
		{
			LatexListRenderer renderer = new LatexListRenderer(cp,false);
			renderer.render(list(categories),new LatexSectionRenderer(cp,0,null));
			StringWriter sw = new StringWriter();
			renderer.write(sw);
			return sw.toString();
		}
		catch (IOException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	
	@Deprecated public List create(java.util.List<Category> lRc)
	{
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		
		List list = OfxListFactory.unordered();
		list.setComment(comment);
		
		for(Category category : lRc)
		{
			try {list.getItem().add(createItem(category));}
			catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
			catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		}
		
		return list;
	}
	
	public List list(java.util.List<net.sf.ahtutils.xml.security.Category> categories)
	{
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		
		List list = OfxListFactory.unordered();
//		list.setComment(comment);
		
		for(net.sf.ahtutils.xml.security.Category category : categories)
		{
			list.getItem().add(OfxListItemFactory.build(OfxMultiLangFactory.paragraph(langs, category.getLangs())));
		}
		
		return list;
	}
	
	@Deprecated private Item createItem(Category category) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		String description,text;
		
		try
		{
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			Lang l = StatusXpath.getLang(category.getLangs(), langs[0]);
			description = l.getTranslation();
		}
		catch (ExlpXpathNotFoundException e){description = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){description = e.getMessage();}
		
		try
		{
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			Description d = StatusXpath.getDescription(category.getDescriptions(), langs[0]);
			text = d.getValue();
		}
		catch (ExlpXpathNotFoundException e){text = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){text = e.getMessage();}		
		
		Paragraph p = new Paragraph();
		p.getContent().add(text);
		
		Item item = new Item();
		item.setName(description);
		item.getContent().add(p);
		
		return item;
	}
}