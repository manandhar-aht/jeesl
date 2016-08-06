package net.sf.ahtutils.msgbundle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

import org.jeesl.AbstractJeeslUtilTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTranslationMap extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTranslationMap.class);
	
	private TranslationMap tMap;
	
	private String[] t1,t2,t3;
	private List<String[]> translations;
	private Set<String> langs;
	private Map<String,String> deTranslations;
	
	@Before
	public void init()
	{
		tMap = new TranslationMap();
		translations = new ArrayList<String[]>();
		langs = new HashSet<String>();
		deTranslations = new Hashtable<String,String>();
		
		t1 = new String[] {"de","key1","lang1"};translations.add(t1);
		t2 = new String[] {"de","key2","lang2"};translations.add(t2);
		t3 = new String[] {"en","key3","lang3"};translations.add(t3);
	}
	
	private void addAllTranslations()
	{
		for(String[] s : translations)
		{
			tMap.add(s);
			langs.add(s[0]);
			if(s[0].equals("de")){deTranslations.put(s[1], s[2]);}
		}
	}
	
	@Test
	public void sizeLanguages()
    {	
		tMap.add(t1);
		Assert.assertEquals(1, tMap.sizeLanguages());
		
		tMap.add(t2);
		Assert.assertEquals(1, tMap.sizeLanguages());
		
		tMap.add(t3);
		Assert.assertEquals(2, tMap.sizeLanguages());
    }
	
	@Test
	public void sizeKeys() throws UtilsNotFoundException
	{
		addAllTranslations();
		Assert.assertEquals(2, tMap.sizeKeys("de"));
		Assert.assertEquals(1, tMap.sizeKeys("en"));
	}
	
	@Test(expected=UtilsNotFoundException.class)
	public void sizeKeysUnknown() throws UtilsNotFoundException
	{
		tMap.sizeKeys("-1");
	}
	
	@Test
	public void translate()
	{
		addAllTranslations();
		for(String[] s : translations)
		{
			Assert.assertEquals(s[2], tMap.translate(s[0], s[1]));
		}
	}
	
	@Test(expected=UtilsNotFoundException.class)
	public void translateUnknowLang() throws UtilsNotFoundException
	{
		addAllTranslations();
		tMap.translateWithException("-1","-1");
	}
	
	@Test(expected=UtilsNotFoundException.class)
	public void translateUnknowKey() throws UtilsNotFoundException
	{
		addAllTranslations();
		tMap.translateWithException("de","-1");
	}
	
	@Test
	public void getLangKeys()
	{
		addAllTranslations();
		List<String> list = tMap.getLangKeys();
		Assert.assertEquals(langs.size(), list.size());
		for(String langKey : list)
		{
			Assert.assertTrue(langs.contains(langKey));
		}
	}
	
	@Test
	public void getTranslationKeys() throws UtilsNotFoundException
	{
		addAllTranslations();
		List<String> list = tMap.getTranslationKeys("de");
		Assert.assertEquals(deTranslations.size(), list.size());
		for(String key : list)
		{
			Assert.assertTrue(deTranslations.containsKey(key));
			Assert.assertEquals(deTranslations.get(key), tMap.translate("de", key));
		}
	}
	
	@Test(expected=UtilsNotFoundException.class)
	public void getTranslationKeysUnknows() throws UtilsNotFoundException
	{
		tMap.getTranslationKeys("-1");
	}
}
