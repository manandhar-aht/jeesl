package org.jeesl.factory.sql.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlWeightedRatingFactory
{
	final static Logger logger = LoggerFactory.getLogger(SqlWeightedRatingFactory.class);


	public static String build(String table, String category, String domain)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("WITH weights (id, weight) AS (").append(weightValues()).append(")").append(System.lineSeparator());
		sb.append("SELECT ").append(domain).append("_id").append(System.lineSeparator());
		sb.append("       ,SUM(rating*weight) as rate").append(System.lineSeparator());
		sb.append("FROM ").append(table).append(System.lineSeparator());
		sb.append("INNER JOIN weights ON ").append(category.toString()).append("_id=weights.id").append(System.lineSeparator()); 
		sb.append("GROUP BY ").append(domain).append("_id").append(System.lineSeparator());
		sb.append("HAVING rate>0").append(System.lineSeparator());
		sb.append("ORDER BY rate DESC");
		sb.append(";");
		return sb.toString();
	}
	
	private static String weightValues()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("VALUES (601,5),(602,4)");
		return sb.toString();
	}
}