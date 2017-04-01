package org.jeesl.interfaces.bean;

import java.util.Map;

public interface JiraConfig
{	
	String netRestJiraUrl  = "net.rest.jira.url";
	String netRestJiraUser  = "net.rest.jira.user";
	String netRestJiraPwd  = "net.rest.jira.pwd";
	
	enum Code{jiraHost,jiraScriptPath,jiraCollector}
	
	String getJiraHost();
	String getJiraScriptPath();
	Map<String,String> getCollectorId();
}