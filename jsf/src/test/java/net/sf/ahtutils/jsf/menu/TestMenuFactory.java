package net.sf.ahtutils.jsf.menu;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.controller.factory.xml.acl.XmlViewFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangFactory;
import net.sf.ahtutils.test.AbstractAhtUtilsJsfTst;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.access.Views;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.ahtutils.xml.navigation.Navigation;
import net.sf.ahtutils.xml.navigation.UrlMapping;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.exlp.util.process.ProcessClock;

import org.jeesl.jsf.menu.MenuXmlBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMenuFactory extends AbstractAhtUtilsJsfTst
{
	final static Logger logger = LoggerFactory.getLogger(TestMenuFactory.class);
	
	private Access access;
	private Map<String,Boolean> mapViewAllowed;
	
	private Menu menu;

	private View v1;
	private MenuItem mWithLangs,mWithView,mWithHref,mTest;
	
	public static final String lang = "de";
	public static final String rootNode = "root";
	public static final String viewCode = "testView";
	
	public static final String code1 = "menuLevel1";
	public static final String code2 = "menuLevel2";
	
	private MenuXmlBuilder mf; public MenuXmlBuilder getMf() {return mf;}

	public static TestMenuFactory factory()
	{
		TestMenuFactory f = new TestMenuFactory();
		f.init();
		return f;
	}
	
	@Before
	public void init()
	{
		initAccess();
		initMenu();
		mf = new MenuXmlBuilder(menu,access,lang,rootNode);
	}
	
	private void initAccess()
	{
		mapViewAllowed = new Hashtable<String,Boolean>();
		
		v1 = XmlViewFactory.create(viewCode);
		v1.setPublic(false);
		v1.setLangs(new Langs());
		v1.getLangs().getLang().add(XmlLangFactory.create(lang, "viewTranslation"));
		v1.getLangs().getLang().add(XmlLangFactory.create("en", "dummyTranslation"));
		v1.setNavigation(new Navigation());
		v1.getNavigation().setUrlMapping(new UrlMapping());
		v1.getNavigation().getUrlMapping().setValue("myViewUrlHref");
		mapViewAllowed.put(viewCode, true);
				
		Category c1 = new Category();
		c1.setViews(new Views());
		c1.getViews().getView().add(v1);
		
		access = new Access();
		access.getCategory().add(c1);
	}
	
	private void initMenu()
	{
		menu = new Menu();
		
		mWithLangs = new MenuItem();
		mWithLangs.setCode("mWithLangs");
		mWithLangs.setLangs(new Langs());
		mWithLangs.getLangs().getLang().add(XmlLangFactory.create(lang, "myTranslation"));
		mWithLangs.getLangs().getLang().add(XmlLangFactory.create("en", "dummyTranslation"));
		menu.getMenuItem().add(mWithLangs);
		
		mWithView = new MenuItem();
		mWithView.setCode("mWithView");
		mWithView.setView(XmlViewFactory.create(viewCode));
		menu.getMenuItem().add(mWithView);
		
		mWithHref = new MenuItem();
		mWithHref.setCode("mWithHref");
		mWithHref.setHref("myHref");
		menu.getMenuItem().add(mWithHref);
		
		MenuItem mL2 = new MenuItem();
		mL2.setCode(code2);
		mL2.setHref("myHrefTest");
		
		mTest = new MenuItem();
		mTest.setCode(code1);
		mTest.setHref("myHrefTest");
		mTest.getMenuItem().add(mL2);
		menu.getMenuItem().add(mTest);
	}
	
	@Test
	public void testWithLangs()
	{
		Menu actualMenu = mf.build(mapViewAllowed,mTest.getCode());
		Assert.assertEquals(menu.getMenuItem().size(), actualMenu.getMenuItem().size());
		MenuItem actual = actualMenu.getMenuItem().get(0);
		Assert.assertTrue(actual.isSetName());
		Assert.assertEquals(mWithLangs.getLangs().getLang().get(0).getTranslation(), actual.getName());
	}

	@Test
	public void testWithView()
	{
		Menu actualMenu = mf.build(mapViewAllowed,mTest.getCode());
		MenuItem actual = actualMenu.getMenuItem().get(1);
		Assert.assertTrue(actual.isSetName());
		Assert.assertEquals(v1.getLangs().getLang().get(0).getTranslation(), actual.getName());
	}
	
	@Test
	public void testWithViewDenied()
	{
		mapViewAllowed.put(viewCode, false);
		mf = new MenuXmlBuilder(menu,access,lang);
		Menu actualMenu = mf.build(mapViewAllowed,mTest.getCode());
		Assert.assertEquals(menu.getMenuItem().size()-1, actualMenu.getMenuItem().size());
	}
	
	@Test
	public void testHrefDirect()
	{
		Menu actualMenu = mf.build(mapViewAllowed,mTest.getCode());
		Assert.assertNull(actualMenu.getMenuItem().get(0).getHref());
		
		MenuItem actual = actualMenu.getMenuItem().get(2);
		Assert.assertTrue("href not set",actual.isSetHref());
		Assert.assertEquals(mWithHref.getHref(), actual.getHref());
	}
	
	@Test
	public void testHrefInView()
	{
		Menu actualMenu = mf.build(mapViewAllowed,mTest.getCode());
		
		MenuItem actual = actualMenu.getMenuItem().get(1);
		Assert.assertTrue("href not set",actual.isSetHref());
		Assert.assertEquals(v1.getNavigation().getUrlMapping().getValue(), actual.getHref());
	}
	
	@Test
	public void speed()
	{
		ProcessClock pc = new ProcessClock();	
		pc.add("start");
		mf.build(mapViewAllowed,mTest.getCode());
		pc.add("stop");
		pc.info(logger);
	}
}