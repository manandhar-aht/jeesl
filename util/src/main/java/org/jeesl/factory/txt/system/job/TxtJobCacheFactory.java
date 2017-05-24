package org.jeesl.factory.txt.system.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class TxtJobCacheFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtJobCacheFactory.class);
		
	public static String jobCode(String localeCode, EjbWithId... ids)
	{
		List<Long> list = new ArrayList<Long>();
		for(EjbWithId id : ids){list.add(id.getId());}
		
		StringBuilder sb = new StringBuilder();
		sb.append(localeCode);
		sb.append(":").append(StringUtils.join(list,"-"));
		
		return sb.toString();
	}
	
	public static String jobCode(String localeCode, List<EjbWithId> list, EjbWithId... ids)
	{
		List<Long> singleIdList = new ArrayList<Long>();
		for(EjbWithId id : ids){singleIdList.add(id.getId());}
		
		List<Long> listIdList = new ArrayList<Long>();
		for(EjbWithId id : list){listIdList.add(id.getId());}
		
		StringBuilder sb = new StringBuilder();
		sb.append(localeCode);
		sb.append(":").append(StringUtils.join(singleIdList,"-"));
		sb.append(":").append(StringUtils.join(listIdList,","));
		
		return sb.toString();
	}
}