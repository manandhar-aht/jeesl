package org.jeesl.factory.sql;

import java.util.List;

import org.jeesl.util.query.sql.UtilsSqlQueryHelper;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SqlFactory
{
	public static <E extends Enum<E>> String sum(String item, E attribute, String as, boolean newLine)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("sum(").append(path(item,attribute)).append(")");
		sb.append(" AS ").append(as);
		newLine(newLine,sb);
		return sb.toString();
	}
	
	public static <E extends Enum<E>> String id(String item, E attribute)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(path(item,attribute)).append("_id");
		return sb.toString();
	}
	
	public static <E extends Enum<E>> String path(String item, E attribute)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(item).append(".").append(attribute.toString());
		return sb.toString();
	}
	
	public static String from(String table, String as, boolean newLine)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("FROM ").append(table).append(" AS ").append(as);
		newLine(newLine,sb);
		return sb.toString();
	}
	
	public static <E extends Enum<E>> String inIdList(String item, E attribute, List<EjbWithId> ids, boolean newLine)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(item).append(".").append(attribute).append("_id");
		sb.append(" IN (").append(UtilsSqlQueryHelper.inIdList(ids)).append(")");
		newLine(newLine,sb);
		return sb.toString();
	}
	
	public static void limit(StringBuilder sb, boolean apply, int limit, boolean newLine)
	{
		if(apply)
		{
			sb.append("LIMIT ").append(limit);
			newLine(newLine,sb);
		}
	}
	
	private static void newLine(boolean newLine, StringBuilder sb)
	{
		if(newLine)
		{
			sb.append("\n");
		}
	}
}