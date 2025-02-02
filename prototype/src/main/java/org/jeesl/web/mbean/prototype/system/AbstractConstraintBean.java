package org.jeesl.web.mbean.prototype.system;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.jeesl.api.bean.msg.JeeslConstraintsBean;
import org.jeesl.api.facade.system.JeeslSystemConstraintFacade;
import org.jeesl.controller.monitor.ProcessingTimeTracker;
import org.jeesl.factory.builder.system.ConstraintFactoryBuilder;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintResolution;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.jeesl.interfaces.model.system.constraint.algorithm.JeeslConstraintAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.xml.system.XmlConstraintFactory;
import net.sf.ahtutils.factory.xml.system.XmlConstraintScopeFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.system.Constraint;
import net.sf.ahtutils.xml.system.ConstraintScope;
import net.sf.ahtutils.xml.system.ConstraintSolution;
import net.sf.ahtutils.xml.system.Constraints;
import net.sf.exlp.util.xml.JaxbUtil;

public class AbstractConstraintBean <L extends UtilsLang, D extends UtilsDescription,
									ALGCAT extends UtilsStatus<ALGCAT,L,D>,
									ALGO extends JeeslConstraintAlgorithm<L,D,ALGCAT>,
									SCOPE extends JeeslConstraintScope<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
									CONCAT extends UtilsStatus<CONCAT,L,D>,
									CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
									LEVEL extends UtilsStatus<LEVEL,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
							implements Serializable,JeeslConstraintsBean<CONSTRAINT>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractConstraintBean.class);
	private static final long serialVersionUID = 1L;
	
//	private JeeslSystemConstraintFacade<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fConstraint;
	private final ConstraintFactoryBuilder<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fbConstraint;
	
	private final Map<String,Map<String,CONSTRAINT>> mapConstraints;
	
	public AbstractConstraintBean(ConstraintFactoryBuilder<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fbConstraint)
	{
		this.fbConstraint=fbConstraint;
		mapConstraints = new HashMap<String,Map<String,CONSTRAINT>>();
	}
	
	public void ping()
	{
		
	}
	
	protected void postConstruct(JeeslSystemConstraintFacade<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fConstraint)
	{
//		this.fConstraint=fConstraint;
		
		int i=0;
		logger.info("Loading "+fbConstraint.getClassConstraint());
		for(CONSTRAINT c : fConstraint.all(fbConstraint.getClassConstraint()))
		{
			i++;
			update(c);
		}
		logger.info("Loaded "+i+" "+fbConstraint.getClassConstraint());
	}
	
	@Override public void update(CONSTRAINT constraint)
	{
		String keyScope = constraint.getScope().getCode();
		if(!mapConstraints.containsKey(keyScope)) {mapConstraints.put(keyScope, new HashMap<String,CONSTRAINT>());}
		mapConstraints.get(keyScope).put(constraint.getCode(),constraint);
	}
	
	@Override public <S extends UtilsStatus<S,?,?>> CONSTRAINT getSilent(Class<?> cScope, S status)
	{
		try {return get(cScope.getSimpleName(),status.getCode());}
		catch (UtilsNotFoundException e)
		{
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override public <SID extends Enum<SID>, CID extends Enum<CID>> CONSTRAINT getSilent(SID sId, CID cId)
	{
		try {return get(sId.toString(),cId.toString());}
		catch (UtilsNotFoundException e)
		{
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override public <CID extends Enum<CID>> CONSTRAINT get(Class<?> c, CID cId) throws UtilsNotFoundException
	{
		return get(c.getSimpleName(),cId.toString());
	}
	@Override public <CID extends Enum<CID>> CONSTRAINT getSilent(Class<?> c, CID cId)
	{
		try {return get(c.getSimpleName(),cId.toString());}
		catch (UtilsNotFoundException e)
		{
			logger.error(e.getMessage());
			return null;
		}
	}
	
	private CONSTRAINT get(String sId, String cId) throws UtilsNotFoundException
	{
		if(!mapConstraints.containsKey(sId.toString())) {throw new UtilsNotFoundException("Scope "+sId+" not available");}
		if(!mapConstraints.get(sId.toString()).containsKey(cId.toString())) {throw new UtilsNotFoundException("Contraint "+cId+" not available in Scope "+sId);}
		return mapConstraints.get(sId.toString()).get(cId.toString());
	}
	
	// ************************************************************************************************
	//Below is depreacated
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