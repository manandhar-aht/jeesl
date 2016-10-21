package net.sf.ahtutils.doc.ofx.security;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import net.sf.ahtutils.doc.ofx.security.list.OfxSecurityCategoryListFactory;
import net.sf.ahtutils.test.AhtUtilsDocBootstrap;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.TestXmlCategory;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.jeesl.model.xml.system.status.TestXmlDescription;
import org.jeesl.model.xml.system.status.TestXmlLang;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.list.List;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.renderer.OfxLatexRenderer;
import org.openfuxml.renderer.latex.content.list.LatexListRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOfxCategoryListFactory extends AbstractOfxSecurityFactoryTest
{
	final static Logger logger = LoggerFactory.getLogger(TestOfxCategoryListFactory.class);
	
	private static Configuration config;
	private static Translations translations;
	
	private OfxSecurityCategoryListFactory factory;
	
	private final String lang ="de";
	private Category rc1;
//	private RoleCategory rc2;
	private java.util.List<Category> list;
	
	
	private OfxLatexRenderer parentSection;
	
	@BeforeClass
	public static void initFiles() throws ConfigurationException, FileNotFoundException
	{
		fXml = new File(rootDir,"listRoleCategory.xml");
		fTxt = new File(rootDir,"listRoleCategory.tex");
		
		DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
		config = builder.getConfiguration(false);
		translations = JaxbUtil.loadJAXB("src/test/resources/data/xml/dummyTranslations.xml", Translations.class);
	}
	
	@Before
	public void init()
	{	
		super.initOfx();
		
		parentSection = new LatexSectionRenderer(cp,0,null);
		factory = new OfxSecurityCategoryListFactory(config,lang,translations,cp);
		list = new ArrayList<Category>();
		rc1 = createCategory(1);list.add(rc1);
	}
	
	private Category createCategory(int id)
	{
		Category rc = TestXmlCategory.create(true);
		rc.getLangs().getLang().add(TestXmlLang.create(false,lang,"Category "+id));
		rc.getDescriptions().getDescription().add(TestXmlDescription.create(false,lang,id+" This category is for testing purposes only."));
		return rc;
	}
	
	@Test
	public void testOfx() throws FileNotFoundException
	{
		List actual = factory.create(list);
		saveXml(actual,fXml,false);
		List expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), List.class);
		this.assertJaxbEquals(expected, actual);
	}
	
	@Test
	public void testLatex() throws OfxAuthoringException, IOException
	{
		List actual = factory.create(list);
		LatexListRenderer renderer = new LatexListRenderer(cp);
		renderer.render(actual,parentSection);
    	debug(renderer);
    	save(renderer,fTxt);
    	assertText(renderer,fTxt);
		
	}
	
	public static void main(String[] args) throws Exception
    {
		AhtUtilsDocBootstrap.init();
		
		TestOfxCategoryListFactory.initFiles();
		TestOfxCategoryListFactory test = new TestOfxCategoryListFactory();
		test.setSaveReference(true);
		test.init();
		test.testOfx();
		test.testLatex();
    }
}