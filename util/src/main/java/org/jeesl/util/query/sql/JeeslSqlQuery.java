package org.jeesl.util.query.sql;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslSqlQuery
{	
	private static final SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String inIds(EjbWithId ejb)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(ejb.getId());
		return sb.toString();
	}
	
	public static String inIdList(List<EjbWithId> list)
	{
		if(list==null || list.size()==0){return "0";}
		StringBuffer sb = new StringBuffer();
		for(EjbWithId ejb : list)
		{
			sb.append(ejb.getId());
			sb.append(",");
		}
		return sb.substring(0,sb.length()-1);
	}
	
	public static String inLongIds(List<Long> list)
	{
		if(list==null || list.size()==0){return "0";}
		StringBuffer sb = new StringBuffer();
		for(Long id : list)
		{
			sb.append(id);
			sb.append(",");
		}
		return sb.substring(0,sb.length()-1);
	}
	
	public static String inCodes(EjbWithCode ejb)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("'").append(ejb.getCode()).append("'");
		return sb.toString();
	}
	
	public static String date(Date date)
	{
		return "'"+dfDate.format(date)+"'";
	}
}