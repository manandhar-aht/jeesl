package org.jeesl.controller.processor;

import java.util.Arrays;
import java.util.List;

import net.sf.ahtutils.controller.processor.set.SetProcessingLexer;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.collections.ListUtils;

import net.sf.ahtutils.controller.processor.set.SetProcessingBaseVisitor;
import net.sf.ahtutils.controller.processor.set.SetProcessingParser;

public class IntersectionProcessor extends SetProcessingBaseVisitor
{
	@SuppressWarnings("unchecked")
	public static <T> List<T> and(List<T> a, List<T> b)
	{
		return ListUtils.intersection(a,b);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> or(List<T> a, List<T> b)
	{
		return ListUtils.union(ListUtils.subtract(a,b),b);
	}

	static List lists;

	public static <T> List<T> query(String query, List<T>... list)
	{
		lists = Arrays.asList(list);
		SetProcessingLexer lexer = new SetProcessingLexer(new ANTLRInputStream(query));
		SetProcessingParser parser = new SetProcessingParser(new CommonTokenStream(lexer));
		return (List<T>)new IntersectionProcessor().visit(parser.parse());
	}

	@Override
	public Object visitExpression(SetProcessingParser.ExpressionContext ctx)
	{
		if(ctx.op.AND() != null)
		{
			if(ctx.OPAREN()	!= null)
				return and(asList(ctx.left.getText().charAt(0)),(List)visitExpression(ctx.expression()));
			return and(asList(ctx.left.getText().charAt(0)), asList(ctx.right.getText().charAt(0)));
		}
		if(ctx.op.OR() != null)
		{
			if(ctx.OPAREN()	!= null)
				return or(asList(ctx.left.getText().charAt(0)),(List)visitExpression(ctx.expression()));
			return or(asList(ctx.left.getText().charAt(0)), asList(ctx.right.getText().charAt(0)));
		}
		throw new RuntimeException("*JediHandWave* This is never happened.");
	}

	@Override
	public Object visitParse(SetProcessingParser.ParseContext ctx)
	{
		return super.visitParse(ctx);
	}

	private List asList(char s) {
		int i = s - 97;
		return (List)lists.get(i);
	}


}