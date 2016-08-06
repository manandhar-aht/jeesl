package net.sf.ahtutils.controller.interfaces.mbean;

import java.util.Map;

public interface JiraConfig
{	
	enum Code{jiraHost,jiraScriptPath,jiraCollector}
	
	String getJiraHost();
	String getJiraScriptPath();
	Map<String,String> getCollectorId();
}