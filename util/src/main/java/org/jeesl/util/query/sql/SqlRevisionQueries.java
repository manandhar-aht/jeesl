package org.jeesl.util.query.sql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlRevisionQueries
{
	final static Logger logger = LoggerFactory.getLogger(SqlRevisionQueries.class);

	public static String revisionsIn(String entityTable, String revisionTable, Date from, Date to, List<Integer> types)
	{	
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT e.id as id,\n");
		sb.append("        r.id as rev,\n");
		sb.append("        e.revtype as type,\n");
		sb.append("        r.userid as user\n");
		sb.append(" FROM ").append(entityTable).append(" e\n");
		sb.append(" JOIN ").append(revisionTable).append(" r ON e.rev = r.id\n");
		sb.append(" WHERE e.revtype IN (").append(StringUtil.join(types,",")).append(")");

		return sb.toString();
	}
	
	public static List<Integer> typesCreateRemove()
	{
		List<Integer> result = new ArrayList<Integer>();
		result.add(0);
		result.add(2);
		return result;
	}
}