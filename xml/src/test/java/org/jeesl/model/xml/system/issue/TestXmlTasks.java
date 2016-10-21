package org.jeesl.model.xml.system.issue;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.issue.Tasks;

public class TestXmlTasks extends AbstractXmlIssueTest<Tasks>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTasks.class);
	
	public TestXmlTasks(){super(Tasks.class);}
	public static Tasks create(boolean withChildren){return (new TestXmlTasks()).build(withChildren);} 
    
    public Tasks build(boolean withChilds)
    {
    	Tasks xml = new Tasks();
    	
    	if(withChilds)
    	{
    		xml.getTask().add(TestXmlTask.create(false));xml.getTask().add(TestXmlTask.create(false));
    	}
    	    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlTasks test = new TestXmlTasks();
		test.saveReferenceXml();
    }
}