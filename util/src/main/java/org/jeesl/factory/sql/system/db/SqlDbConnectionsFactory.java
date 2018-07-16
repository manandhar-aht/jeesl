package org.jeesl.factory.sql.system.db;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.StringUtil;

public class SqlDbConnectionsFactory
{
	public static String build(String userName)
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
}