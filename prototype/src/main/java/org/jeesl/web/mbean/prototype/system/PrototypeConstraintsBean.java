package org.jeesl.web.mbean.prototype.system;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import org.jeesl.api.bean.JeeslConstraintsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.system.XmlConstraintFactory;
import net.sf.ahtutils.factory.xml.system.XmlConstraintScopeFactory;
import net.sf.ahtutils.monitor.ProcessingTimeTracker;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.system.Constraint;
import net.sf.ahtutils.xml.system.ConstraintScope;
import net.sf.ahtutils.xml.system.ConstraintSolution;
import net.sf.ahtutils.xml.system.Constraints;
import net.sf.exlp.util.xml.JaxbUtil;

public class PrototypeConstraintsBean implements Serializable,JeeslConstraintsBean
{
	final static Logger logger = LoggerFactory.getLogger(PrototypeConstraintsBean.class);
	private static final long serialVersionUID = 1L;
		
	private Map<String,Constraint> constraints;
	private Map<String,ConstraintScope> scopes;

    public void init(String artifact) throws FileNotFoundException
    {
		ProcessingTimeTracker ptt = new ProcessingTimeTracker(true);

		constraints = new Hashtable<String,Constraint>();
		scopes = new Hashtable<String,ConstraintScope>();
		
		Constraints index = JaxbUtil.loadJAXB("constraints."+artifact+"/index.xml", Constraints.class);
		for(ConstraintScope scopeCategory : index.getConstraintScope())
		{
			Constraints c = JaxbUtil.loadJAXB("constraints."+artifact+"/"+scopeCategory.getCategory()+".xml", Constraints.class);
			for(ConstraintScope scope : c.getConstraintScope())
			{
				String scopeCode = scopeCategory.getCategory()+"-"+scope.getCode();
				scopes.put(scopeCode, scope);
				for(Constraint constraint : scope.getConstraint())
				{
					if(constraint.isSetCode())
					{
						String key = scopeCode+"-"+constraint.getCode();
						logger.info("Adding "+key);
						constraints.put(key, constraint);
					}
				}
			}
		}
		logger.info(AbstractLogMessage.postConstruct(ptt)+" with Constraints:"+constraints.size());
    }
    
    public void init2(String artifact) throws FileNotFoundException
    {
		ProcessingTimeTracker ptt = new ProcessingTimeTracker(true);

		constraints = new Hashtable<String,Constraint>();
		scopes = new Hashtable<String,ConstraintScope>();
		
		Constraints index = JaxbUtil.loadJAXB(artifact+"/constraints/index.xml", Constraints.class);
		for(ConstraintScope scopeCategory : index.getConstraintScope())
		{
			Constraints c = JaxbUtil.loadJAXB(artifact+"/constraints/"+scopeCategory.getCategory()+".xml", Constraints.class);
			for(ConstraintScope scope : c.getConstraintScope())
			{
				String scopeCode = scopeCategory.getCategory()+"-"+scope.getCode();
				scopes.put(scopeCode, scope);
				for(Constraint constraint : scope.getConstraint())
				{
					if(constraint.isSetCode())
					{
						String key = scopeCode+"-"+constraint.getCode();
						logger.info("Adding "+key);
						constraints.put(key, constraint);
					}
				}
			}
		}
		logger.info(AbstractLogMessage.postConstruct(ptt)+" with Constraints:"+constraints.size());
    }
    
    public String getMessage(String category, String scope, String code, String lang)
    {    	
    	String key = category+"-"+scope+"-"+code;
    	
    	if(constraints.containsKey(key))
    	{
    		Constraint c = constraints.get(key);
        	if(c.isSetDescriptions())
        	{
        		for(Description d : c.getDescriptions().getDescription())
            	{
            		if(d.getKey().equals(lang)){return d.getValue();}
            	}
        	}
    	}
    	
    	return "Constraint not found in list: "+key;
    }
    
    public String getSolution(String category, String scope, String code, String lang)
    {    	
    	String key = category+"-"+scope+"-"+code;
    	
    	if(constraints.containsKey(key))
    	{
    		Constraint c = constraints.get(key);
    		if(c.isSetConstraintSolution())
    		{
    			ConstraintSolution s = c.getConstraintSolution();
    			if(s.isSetDescriptions())
            	{
            		for(Description d : s.getDescriptions().getDescription())
                	{
                		if(d.getKey().equals(lang)){return d.getValue();}
                	}
            	}
    		}
    	}
    	
    	return "Solution not found in list: "+key;
    }
    
    public ConstraintScope getScope(String category, String scope, String lang)
    {    	
    	String key = category+"-"+scope;
    	ConstraintScope xml = XmlConstraintScopeFactory.build(key);
    	if(scopes.containsKey(key))
    	{
    		ConstraintScope x = scopes.get(key);
    		for(Lang l : x.getLangs().getLang()){if(l.getKey().equals(lang)){xml.setLang(l);}}
    		for(Description d : x.getDescriptions().getDescription()) {if(d.getKey().equals(lang)){xml.setDescription(d);}}
    		
    		for(Constraint c : x.getConstraint())
    		{
    			Constraint xmlC = XmlConstraintFactory.build();
    			if(c.isSetLangs())
    			{
    				for(Lang l : c.getLangs().getLang()){if(l.getKey().equals(lang)){xmlC.setLang(l);}}
    			}
    			if(c.isSetDescriptions())
    			{
    				for(Description d : c.getDescriptions().getDescription()) {if(d.getKey().equals(lang)){xmlC.setDescription(d);}}
    			}
        		xml.getConstraint().add(xmlC);
    		}
    	}
    	
    	return xml;
    }
}