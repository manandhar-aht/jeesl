package org.jeesl.controller.provider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.controller.handler.TranslationHandler;
import org.jeesl.factory.txt.system.status.TxtStatusFactory;
import org.jeesl.interfaces.controller.handler.JeeslTranslationProvider;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class GenericTranslationProvider <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
										RE extends JeeslRevisionEntity<L,D,?,?,RA>,
										RA extends JeeslRevisionAttribute<L,D,RE,?,?>>
					implements JeeslTranslationProvider<LOC>
{
	final static Logger logger = LoggerFactory.getLogger(GenericTranslationProvider.class);
	
	private final Set<String> setLocaleCodes;
	private final List<String> localeCodes; @Override public List<String> getLocaleCodes() {return localeCodes;}
	
	private final SimpleDateFormat sdfDate;
	private final SimpleDateFormat sdfTime;
	private final TranslationHandler<L,D,RE,RA> th;
	
	public GenericTranslationProvider(TranslationHandler<L,D,RE,RA> th)
	{
		this.th=th;
		setLocaleCodes = new HashSet<String>();
		localeCodes = new ArrayList<String>();
		sdfDate = new SimpleDateFormat("dd.MM.yyyy");
		sdfTime = new SimpleDateFormat("hh:mm");
	}
	
	public void setLanguages(List<LOC> languages)
	{
		localeCodes.clear();
		localeCodes.addAll(TxtStatusFactory.toCodes(languages));
		setLocaleCodes.addAll(localeCodes);
	}
	
	@Override
	public String toTranslation(String localeCode, String key)
	{
		return th.getEntities().get(key).get(localeCode).getLang();
	}
	
	@Override public <E extends Enum<E>> String toTranslation(String localeCode, Class<?> c, E code)
	{
		return toTranslation(localeCode,c.getSimpleName(),code.toString());
	}

	@Override public String toTranslation(String localeCode, String key1, String key2)
	{
		if(key2==null) {return toTranslation(localeCode,key1);}		
		return th.getLabels().get(key1).get(key2).get(localeCode).getLang();
	}

	@Override public boolean hasLocale(String localeCode) {return setLocaleCodes.contains(localeCode);}

	@Override public String toDate(String locleCode, Date record)
	{
		if(record==null){return "";}
		return sdfDate.format(record);
	}
	
	@Override public String toTime(String localeCode, Date record)
	{
		if(record==null){return "";}
		return sdfTime.format(record);
	}

	
}