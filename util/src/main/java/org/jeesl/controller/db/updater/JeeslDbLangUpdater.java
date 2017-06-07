package org.jeesl.controller.db.updater;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;

public class JeeslDbLangUpdater<C extends EjbWithLang<L>, L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslDbLangUpdater.class);
	
	final Class<C> cEjb;
	final Class<L> cL;
	
	private EjbLangFactory<L> efLang;

	public JeeslDbLangUpdater(final Class<C> cEjb, final Class<L> cL)
    {
        this.cEjb = cEjb;
        this.cL = cL;
        
        efLang = EjbLangFactory.factory(cL);
    } 
	
	public static <C extends EjbWithLang<L>, L extends UtilsLang> JeeslDbLangUpdater<C,L> factory(final Class<C> cEjb, final Class<L> cL)
	{
		return new JeeslDbLangUpdater<C,L>(cEjb,cL);
	}

	public C handle(UtilsFacade fUtils,C ejb, Langs langs) throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(ejb.getName()==null)
		{
			ejb.setName(efLang.getLangMap(langs));
		}
		else
		{
			ejb = remove(fUtils,ejb,langs);
			ejb = add(fUtils,ejb,langs);
			ejb = update(fUtils,ejb,langs);
		}
		return ejb;
	}
	
	
	private C remove(UtilsFacade fUtils,C ejb, Langs langs) throws UtilsConstraintViolationException
	{
		Set<String> actualInXml = new HashSet<String>();
		List<String> obsoleteInEjb = new ArrayList<String>();
		
		for(Lang lang : langs.getLang()){actualInXml.add(lang.getKey());}
		for(String key : ejb.getName().keySet()){if(!actualInXml.contains(key)){obsoleteInEjb.add(key);}}
		
		int before = 0;
		int after = 0;
		if(ejb.getName()!=null){before = ejb.getName().size();}
		
		for(String key : obsoleteInEjb)
		{
			L lang = ejb.getName().get(key);
			ejb.getName().remove(key);
			fUtils.rm(lang);
		}
		if(ejb.getName()!=null){after = ejb.getName().size();}
		logger.debug("Removed "+obsoleteInEjb.size()+" Before:"+before+" After:"+after);
		
		return ejb;
	}
	
	private C add(UtilsFacade fUtils,C ejb, Langs langs) throws UtilsConstraintViolationException
	{
		Set<String> actualInXml = new HashSet<String>();
		for(Lang lang : langs.getLang()){actualInXml.add(lang.getKey());}
		
		List<String> list = new ArrayList<String>(actualInXml);
		
		String[] keys = new String[list.size()];
		for(int i=0;i<list.size();i++)
		{
			keys[i] = list.get(i);
		}
		
		int before = 0;
		int after = 0;
		if(ejb.getName()!=null){before = ejb.getName().size();}
		
		ejb = efLang.persistMissingLangs(fUtils, keys, ejb);
		
		if(ejb.getName()!=null){after = ejb.getName().size();}
		logger.debug("Added "+keys.length+" Before:"+before+" After:"+after);
		return ejb;
	}
	
	private C update(UtilsFacade fUtils,C ejb, Langs langs) throws UtilsConstraintViolationException, UtilsLockingException
	{
		for(Lang lang : langs.getLang())
		{
			if(ejb.getName()==null){logger.warn("ejb.getName()==null "+ejb.toString());}
			else
			{
				L eLang = ejb.getName().get(lang.getKey());
				eLang.setLang(lang.getTranslation());
				eLang = fUtils.save(eLang);
				ejb.getName().put(eLang.getLkey(), eLang);
			}
		}
		return ejb;
	}
}