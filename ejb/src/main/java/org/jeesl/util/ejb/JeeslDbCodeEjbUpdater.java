package org.jeesl.util.ejb;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslDbCodeEjbUpdater<C extends EjbWithCode>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslDbCodeEjbUpdater.class);
	
	final Class<C> codeClass;
	
	private Map<String,C> ejbInDb;

	public JeeslDbCodeEjbUpdater(final Class<C> codeClass)
    {
        this.codeClass = codeClass;
        ejbInDb = new Hashtable<String,C>();
    } 
	
	public static <C extends EjbWithCode> JeeslDbCodeEjbUpdater<C> createFactory(final Class<C> codeClass)
	{
		return new JeeslDbCodeEjbUpdater<C>(codeClass);
	}
	
	@Deprecated //Use
	public void dbEjbs(List<C> list)
	{
		for(C c : list){ejbInDb.put(c.getCode(), c);}
	}
	
	public void dbEjbs(UtilsFacade fUtils)
	{
		for(C c : fUtils.all(codeClass)){ejbInDb.put(c.getCode(), c);}
	}
	
	@Deprecated
	public void actualAdd(String code)
	{
		if(ejbInDb.containsKey(code))
		{
			ejbInDb.remove(code);
		}
	}
	
	public void handled(C ejb)
	{
		if(ejbInDb.containsKey(ejb.getCode()))
		{
			ejbInDb.remove(ejb.getCode());
		}
	}
	
	public List<C> getEjbForRemove()
	{
		List<C> result = new ArrayList<C>();
		for(String key : ejbInDb.keySet())
		{
			result.add(ejbInDb.get(key));
		}
		return result;
	}
	
	public void remove(UtilsFacade fUtils)
	{
		if(!getEjbForRemove().isEmpty())
		{
			List<String> codes = new ArrayList<String>();
			for(C pc : getEjbForRemove()){codes.add(pc.getCode());}
			
			StringBuffer sb = new StringBuffer();
			sb.append("Removing "+getEjbForRemove().size()+" from "+codeClass.getSimpleName());
			sb.append(" (").append(StringUtils.join(codes, ",")).append(")");

			logger.info(sb.toString());
			for(C pc : getEjbForRemove())
			{
				if(pc instanceof EjbRemoveable)
				{
					try {fUtils.rm((EjbRemoveable)pc);}
					catch (UtilsConstraintViolationException e) {e.printStackTrace();}
				}
				else
				{
					logger.warn(pc.getClass().getSimpleName()+" does not implement "+EjbRemoveable.class.getSimpleName());
				}
			}
		}	
	}
}