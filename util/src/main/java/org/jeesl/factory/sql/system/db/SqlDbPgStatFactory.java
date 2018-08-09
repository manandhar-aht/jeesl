package org.jeesl.factory.sql.system.db;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.StringUtil;

public class SqlDbPgStatFactory
{
	public static String connections(String userName)
	{
		List<String> fileds = new ArrayList<String>();
		fileds.add("xact_start");
		fileds.add("query_start");
		fileds.add("state_change");
//		fileds.add("waiting");
		fileds.add("state");
		fileds.add("query");

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ").append(StringUtil.join(fileds,","));
		sb.append(" FROM pg_stat_activity");
		sb.append(" WHERE usename='").append(userName).append("'");
		
		return sb.toString();
	}
	
	public static String queries(String userName)
	{
		List<String> fileds = new ArrayList<String>();
		fileds.add("pd.datname");
		fileds.add("pss.query AS SQLQuery");
		fileds.add("pss.rows AS TotalRowCount");
		fileds.add("calls");
		fileds.add("pss.total_time");
		fileds.add("pss.mean_time");

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ").append(StringUtil.join(fileds,","));
		sb.append(" FROM pg_stat_statements AS pss");
		sb.append(" INNER JOIN pg_database AS pd");
		sb.append("            ON pss.dbid=pd.oid");
		sb.append(" ORDER BY pss.mean_time DESC ");
		sb.append(" LIMIT 200;");
		
		return sb.toString();
	}
}