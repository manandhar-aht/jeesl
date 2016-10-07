package net.sf.ahtutils.xml.xpath;

import java.util.List;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.jeesl.model.xml.system.navigation.Menu;
import org.jeesl.model.xml.system.navigation.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavigationXpath
{
	final static Logger logger = LoggerFactory.getLogger(NavigationXpath.class);
	
	public static MenuItem getMenuItem(Menu menu,String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(menu);
		
		StringBuffer sb = new StringBuffer();
		sb.append("//menuItem[@code='").append(code).append("']");
		
		@SuppressWarnings("unchecked")
		List<MenuItem> listResult = (List<MenuItem>)context.selectNodes(sb.toString());
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+MenuItem.class.getSimpleName()+" for code="+code);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+MenuItem.class.getSimpleName()+" for code="+code);}
		return listResult.get(0);
	}
	
	public static MenuItem getMenuItemSilent(Menu menu,String code)
	{
		JXPathContext context = JXPathContext.newContext(menu);
		
		StringBuffer sb = new StringBuffer();
		sb.append("//menuItem[@code='").append(code).append("']");
		
		@SuppressWarnings("unchecked")
		List<MenuItem> listResult = (List<MenuItem>)context.selectNodes(sb.toString());
		if(listResult.size()==0)
		{
			logger.trace("No "+MenuItem.class.getSimpleName()+" for code="+code);
			return null;
		}
		else if(listResult.size()>1)
		{
			logger.trace("Multiple "+MenuItem.class.getSimpleName()+" for code="+code);
			return null;
		}
		return listResult.get(0);
	}
}