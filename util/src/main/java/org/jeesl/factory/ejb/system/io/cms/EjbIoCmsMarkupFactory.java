package org.jeesl.factory.ejb.system.io.cms;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbIoCmsMarkupFactory<M extends JeeslIoCmsMarkup<?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoCmsMarkupFactory.class);
	
    private final Class<M> cM;
	
    public EjbIoCmsMarkupFactory(final Class<M> cM)
    {
        this.cM = cM;
    } 
 
/*	
	public D create(Description description) throws UtilsConstraintViolationException
	{
		if(!description.isSetKey()){throw new UtilsConstraintViolationException("Key not set: "+JaxbUtil.toString(description));}
		if(!description.isSetValue()){throw new UtilsConstraintViolationException("Value not set: "+JaxbUtil.toString(description));}
    		return create(description.getKey(),description.getValue());
	}
    
	public D create(String key, String value) throws UtilsConstraintViolationException
	{
		if(key==null){throw new UtilsConstraintViolationException("Key not set");}
		if(value==null){throw new UtilsConstraintViolationException("Value not set");}
		D d = null;
		try
		{
			d = cD.newInstance();
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
	
	public <S extends UtilsStatus<S,L,D>, L extends UtilsLang> Map<String,D> createEmpty(List<S> locales)
	{
		return createEmpty(TxtStatusFactory.toCodes(locales).toArray(new String[0]));
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
	
	public Map<String,D> clone(Map<String,D> original) 
	{
		Map<String,D> map = new HashMap<String,D>();
		for(String key : original.keySet())
		{
			try {
				map.put(key, create(key, original.get(key).getLang()));
			} catch (UtilsConstraintViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public <T extends EjbWithDescription<D>, S extends UtilsStatus<S,L,D>, L extends UtilsLang> T persistMissingLangs(UtilsFacade fUtils, List<S> locales, T ejb)
	{
		return persistMissingLangs(fUtils,TxtStatusFactory.toCodes(locales).toArray(new String[0]),ejb);
	}
	
	public <T extends EjbWithDescription<D>> T persistMissingLangsForCode(UtilsFacade fUtils, List<String> codes, T ejb)
	{
		String[] localeCodes = new String[codes.size()];
		for(int i=0;i<codes.size();i++)
		{
			localeCodes[i] = codes.get(i);
		}
		return persistMissingLangs(fUtils,localeCodes,ejb);
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
	*/
}