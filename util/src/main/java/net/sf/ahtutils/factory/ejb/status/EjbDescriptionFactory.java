package net.sf.ahtutils.factory.ejb.status;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.model.xml.JeeslNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbDescriptionFactory<D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(EjbDescriptionFactory.class);
	
    final Class<D> clDescription;
	
    public EjbDescriptionFactory(final Class<D> clDescription)
    {
        this.clDescription = clDescription;
    } 
    
    public static <D extends UtilsDescription> EjbDescriptionFactory<D> createFactory(final Class<D> clDescription)
    {
        return new EjbDescriptionFactory<D>(clDescription);
    }
	
	public D create(Description description) throws UtilsConstraintViolationException
	{
		if(!description.isSetKey()){throw new UtilsConstraintViolationException("Key not set: "+JaxbUtil.toString(description, new JeeslNsPrefixMapper()));}
		if(!description.isSetValue()){throw new UtilsConstraintViolationException("Value not set: "+JaxbUtil.toString(description, new JeeslNsPrefixMapper()));}
    	return create(description.getKey(),description.getValue());
	}
    
	public D create(String key, String value) throws UtilsConstraintViolationException
	{
		if(key==null){throw new UtilsConstraintViolationException("Key not set");}
		if(value==null){throw new UtilsConstraintViolationException("Value not set");}
		D d = null;
		try
		{
			d = clDescription.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
    	d.setLang(value);
    	d.setLkey(key);
    	return d;
	}
	
	public Map<String,D> create(Descriptions descriptions) throws UtilsConstraintViolationException
	{
		if(descriptions!=null && descriptions.isSetDescription()){return create(descriptions.getDescription());}
		else{return  new Hashtable<String,D>();}
	}
	
	public Map<String,D> create(List<Description> lDescriptions) throws UtilsConstraintViolationException
	{
		Map<String,D> map = new Hashtable<String,D>();
		for(Description desc : lDescriptions)
		{
			D d = create(desc);
			map.put(d.getLkey(), d);
		}
		return map;
	}
	
	public Map<String,D> createEmpty(String[] keys)
	{
		Map<String,D> map = new Hashtable<String,D>();
		for(String key : keys)
		{
			try
			{
				map.put(key, create(key,""));
			}
			catch (UtilsConstraintViolationException e) {e.printStackTrace();}
		}
		return map;
	}
	
	public <M extends EjbWithDescription<D>> void rmDescription(UtilsFacade fUtils, M ejb)
	{
		Map<String,D> descMap = ejb.getDescription();
		ejb.setDescription(null);
		
		try{ejb=fUtils.update(ejb);}
		catch (UtilsConstraintViolationException e) {logger.error("",e);}
		catch (UtilsLockingException e) {logger.error("",e);}
		
		if(descMap!=null)
		{
			for(D desc : descMap.values())
			{
				try {fUtils.rm(desc);}
				catch (UtilsConstraintViolationException e) {logger.error("",e);}
			}
		}
	}
	
	public <T extends EjbWithDescription<D>> T persistMissingLangs(UtilsFacade fUtils, String[] keys, T ejb)
	{
		for(String key : keys)
		{
			if(!ejb.getDescription().containsKey(key))
			{
				try
				{
					D d = fUtils.persist(create(key, ""));
					ejb.getDescription().put(key, d);
					ejb = fUtils.update(ejb);
				}
				catch (UtilsConstraintViolationException e) {e.printStackTrace();}
				catch (UtilsLockingException e) {e.printStackTrace();}
			}
		}
		return ejb;
	}
}