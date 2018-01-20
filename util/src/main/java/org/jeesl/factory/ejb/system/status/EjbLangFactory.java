package org.jeesl.factory.ejb.system.status;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.factory.txt.system.status.TxtStatusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLangFactory<L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLangFactory.class);
	
    final Class<L> cL;
	
    public EjbLangFactory(final Class<L> cL)
    {
        this.cL = cL;
    } 
    
    public static <L extends UtilsLang> EjbLangFactory<L> factory(final Class<L> cL)
    {
        return new EjbLangFactory<L>(cL);
    }
	
	public Map<String,L> getLangMap(Langs langs) throws UtilsConstraintViolationException
	{
		if(langs==null){throw new UtilsConstraintViolationException(Langs.class.getSimpleName()+" is null");}
		return getLangMap(langs.getLang()); 
	}
	
	public Map<String,L> getLangMap(List<Lang> langList) throws UtilsConstraintViolationException
	{
		if(langList.size()<1){throw new UtilsConstraintViolationException(Langs.class.getSimpleName()+" with 0 "+Lang.class.getSimpleName());}
		Map<String,L> map = new Hashtable<String,L>();
		for(Lang lang : langList)
		{
			L l = createLang(lang);
			map.put(l.getLkey(), l);
		}
		return map;
	}
	
	public <S extends UtilsStatus<S,L,D>, D extends UtilsDescription> Map<String,L> createEmpty(List<S> locales)
	{
		return createEmpty(TxtStatusFactory.toCodes(locales).toArray(new String[0]));
	}
	
	public Map<String,L> createEmpty(String[] keys)
	{
		Map<String,L> map = new Hashtable<String,L>();
		for(String key : keys)
		{
			map.put(key, createLang(key,""));
		}
		return map;
	}
	
	public Map<String,L> createLangMap(String key, String translation) throws InstantiationException, IllegalAccessException
	{
		Map<String,L> map = new Hashtable<String,L>();
		map.put(key, createLang(key, translation));
		return map;
	}
	
	public Map<String,L> createLangMap(L... langs) 
	{
		Map<String,L> map = new Hashtable<String,L>();
		for(L l : langs)
		{
			map.put(l.getLkey(), l);
		}
		return map;
	}
	
	public Map<String,L> clone(Map<String,L> original) 
	{
		Map<String,L> map = new Hashtable<String,L>();
		for(String key : original.keySet())
		{
			map.put(key, createLang(key, original.get(key).getLang()));
		}
		return map;
	}
	
	public L createLang(String key, String translation)
	{
		try
		{
			L l = cL.newInstance();
			l.setLkey(key);
			l.setLang(translation);
			return l;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		logger.error("Something went terribly wrong, see stacktrace. Unfortunately null is returned here!");
		return null;
	}
	
	public L createLang(Lang lang) throws UtilsConstraintViolationException
	{
		if(lang.getKey()==null){throw new UtilsConstraintViolationException("Key not set for: "+JaxbUtil.toString(lang));}
		if(lang.getTranslation()==null){throw new UtilsConstraintViolationException("Translation not set for: "+JaxbUtil.toString(lang));}
		return createLang(lang.getKey(), lang.getTranslation());
	}
	
	
	public <T extends EjbWithLang<L>, LOC extends UtilsStatus<LOC,L,D>, D extends UtilsDescription> T persistMissingLangs(UtilsFacade fUtils, List<LOC> locales, T ejb)
	{
		return persistMissingLangs(fUtils,TxtStatusFactory.toCodes(locales).toArray(new String[0]),ejb);
	}
	public <T extends EjbWithLang<L>, LOC extends UtilsStatus<LOC,L,D>, D extends UtilsDescription> T persistMissingLangsForCode(UtilsFacade fUtils, List<String> codes, T ejb)
	{
		String[] localeCodes = new String[codes.size()];
		for(int i=0;i<codes.size();i++)
		{
			localeCodes[i] = codes.get(i);
		}
		return persistMissingLangs(fUtils,localeCodes,ejb);
	}
	
	public <T extends EjbWithLang<L>> T persistMissingLangs(UtilsFacade fUtils, String[] keys, T ejb)
	{
		for(String key : keys)
		{
			if(!ejb.getName().containsKey(key))
			{
				try
				{
					L l = fUtils.persist(createLang(key, ""));
					ejb.getName().put(key, l);
					ejb = fUtils.update(ejb);
				}
				catch (UtilsConstraintViolationException e) {e.printStackTrace();}
				catch (UtilsLockingException e) {e.printStackTrace();}
			}
		}
		return ejb;
	}
	
	public <M extends EjbWithLang<L>> void rmLang(UtilsFacade fUtils, M ejb)
	{
		Map<String,L> langMap = ejb.getName();
		ejb.setName(null);
		
		try{ejb=fUtils.update(ejb);}
		catch (UtilsConstraintViolationException e) {logger.error("",e);}
		catch (UtilsLockingException e) {logger.error("",e);}
		
		for(L lang : langMap.values())
		{
			try {fUtils.rm(lang);}
			catch (UtilsConstraintViolationException e) {logger.error("",e);}
		}
	}
}