package org.jeesl.factory.txt.system.status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TxtStatusFactory <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription>
{
	private final String localeCode;
	
	public TxtStatusFactory(String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	public static <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> TxtStatusFactory<S,L,D> factory(String localeCode)
	{
		return new TxtStatusFactory<S,L,D>(localeCode);
	}
	
	public String debug(S status)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(status.getId()).append("]");
		sb.append(" (").append(status.getCode()).append(")");
		sb.append(" ").append(status.getName().get(localeCode).getLang());
		return sb.toString();
	}
	
	public String labels (List<S> list)
	{
		return label(localeCode,list);
	}
	
	public static <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription>
		String label(String lang, List<S> list)
	{
		if(list==null || list.isEmpty()){return null;}
		List<String> result = new ArrayList<String>();
		for(S ejb : list){result.add(ejb.getName().get(lang).getLang());}
		return StringUtils.join(result, ", ");
	}
	
	public static <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription>
			String label(String lang, S ejb)
	{
		return ejb.getName().get(lang).getLang();
	}
	
	
	
	public static <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription>
	List<String> toCodes(Collection<S> list)
	{
		List<String> result = new ArrayList<String>();
		for(S ejb : list){result.add(ejb.getCode());}
		return toCodes(new ArrayList<S>(list));
	}
	
	public static <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription>
		List<String> toCodes(List<S> list)
	{
		List<String> result = new ArrayList<String>();
		for(S ejb : list){result.add(ejb.getCode());}
		return result;
	}
}