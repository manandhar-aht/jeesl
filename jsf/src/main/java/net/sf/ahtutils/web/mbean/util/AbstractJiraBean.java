package net.sf.ahtutils.web.mbean.util;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.controller.interfaces.mbean.JiraConfig;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;

import org.jeesl.interfaces.model.system.util.JeeslProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractJiraBean implements Serializable,JiraConfig
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJiraBean.class);
	private static final long serialVersionUID = 1L;
	
	protected String jiraHost;
	protected String jiraScriptPath;
	
	protected Map<String,String> collectorId;
	
	//******* Methods *******************************

	public AbstractJiraBean()
	{
		collectorId = new Hashtable<String,String>();
	}
	
    public <T extends JeeslProperty> void init(UtilsFacade fUtils, Class<T> cl, String[] collectorKeys) throws UtilsNotFoundException
    {
    	jiraHost = fUtils.valueStringForKey(cl, JiraConfig.Code.jiraHost.toString(), null);
    	jiraScriptPath = fUtils.valueStringForKey(cl, JiraConfig.Code.jiraScriptPath.toString(), null);
    	
    	for(String key : collectorKeys)
    	{
    		collectorId.put(key, fUtils.valueStringForKey(cl, JiraConfig.Code.jiraCollector.toString()+key, null));
    	}
    }

	@Override public String getJiraHost() {return jiraHost;}
	@Override public String getJiraScriptPath() {return jiraScriptPath;}
	@Override public Map<String, String> getCollectorId() {return collectorId;}
}