package net.sf.ahtutils.web.mbean.util;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import org.jeesl.api.facade.system.JeeslSystemPropertyFacade;
import org.jeesl.interfaces.bean.JiraConfig;
import org.jeesl.interfaces.model.system.util.JeeslProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractJiraBean <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								P extends JeeslProperty<L,D>> 
				implements Serializable,JiraConfig
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJiraBean.class);
	private static final long serialVersionUID = 1L;
	
	protected String jiraHost;
	protected String jiraScriptPath;
	
	protected Map<String,String> collectorId;


	public AbstractJiraBean()
	{
		collectorId = new Hashtable<String,String>();
	}
	
    public void init(JeeslSystemPropertyFacade<L,D,CATEGORY,P> fUtils, Class<P> cl, String[] collectorKeys) throws UtilsNotFoundException
    {
    	jiraHost = fUtils.valueStringForKey(JiraConfig.Code.jiraHost.toString(), null);
    	jiraScriptPath = fUtils.valueStringForKey(JiraConfig.Code.jiraScriptPath.toString(), null);
    	
    	for(String key : collectorKeys)
    	{
    		collectorId.put(key, fUtils.valueStringForKey(JiraConfig.Code.jiraCollector.toString()+key, null));
    	}
    }

	@Override public String getJiraHost() {return jiraHost;}
	@Override public String getJiraScriptPath() {return jiraScriptPath;}
	@Override public Map<String, String> getCollectorId() {return collectorId;}
}