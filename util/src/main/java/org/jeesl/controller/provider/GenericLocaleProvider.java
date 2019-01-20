package org.jeesl.controller.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.factory.ejb.util.EjbCodeFactory;
import org.jeesl.factory.txt.system.status.TxtStatusFactory;
import org.jeesl.interfaces.model.system.locale.JeeslLocaleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class GenericLocaleProvider <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>>
					implements JeeslLocaleProvider<LOC>
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(GenericLocaleProvider.class);
	
	private final List<String> localeCodes; @Override public List<String> getLocaleCodes() {return localeCodes;}
	private final Map<String,LOC> mapLocales;

	
	public GenericLocaleProvider()
	{
		localeCodes = new ArrayList<>();
		mapLocales = new HashMap<String,LOC>();
	}
	
	public void setLocales(List<LOC> locales)
	{
		localeCodes.clear();
		mapLocales.clear();
		
		localeCodes.addAll(TxtStatusFactory.toCodes(locales));
		mapLocales.putAll(EjbCodeFactory.toMapCode(locales));
	}

	@Override public boolean hasLocale(String localeCode){return mapLocales.containsKey(localeCode);}
}