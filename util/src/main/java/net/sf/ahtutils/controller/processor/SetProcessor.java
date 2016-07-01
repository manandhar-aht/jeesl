package net.sf.ahtutils.controller.processor;

import java.util.Arrays;
import java.util.List;

import net.sf.ahtutils.controller.processor.set.SetProcessingBaseVisitor;
import net.sf.ahtutils.controller.processor.set.SetProcessingParser;
import org.apache.commons.collections.ListUtils;

public class SetProcessor extends SetProcessingBaseVisitor
{
	@SuppressWarnings("unchecked")
	public static <T extends Object> List<T> and(List<T> a, List<T> b)
	{
		return ListUtils.intersection(a,b);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Object> List<T> or(List<T> a, List<T> b)
	{
		return ListUtils.union(ListUtils.subtract(a,b),b);
	}
	
	public static <T extends Object> List<T> query(String query, List<T>... list)
	{
		return null;
	}

	@Override
	public Object visitExpression(SetProcessingParser.ExpressionContext ctx)
	{
		if(ctx.op.AND() != null)
		{
			List right;
			if(ctx.OPAREN() != null){right = (List)visitExpression(ctx.expression());}
			else right = (List)visitList(ctx.right);
			return and((List)visitList(ctx.left),right);
		}
		else if (ctx.op.OR() != null)
		{
			List right;
			if(ctx.OPAREN() != null){right = (List)visitExpression(ctx.expression());}
			else right = (List)visitList(ctx.right);
			return or((List)visitList(ctx.left),right);
		}
		throw new RuntimeException("*JediHandWave* This is never happened.");
	}

	@Override
	public Object visitList(SetProcessingParser.ListContext ctx)
	{
		return Arrays.asList(ctx.getText().replaceAll("\\[|\\]", "").split(", "));
	}

	@Override
	public Object visitParse(SetProcessingParser.ParseContext ctx)
	{
		return super.visitParse(ctx);
	}

	private boolean asBoolean(SetProcessingParser.ExpressionContext ctx) {
		return (Boolean)visit(ctx);
	}
}