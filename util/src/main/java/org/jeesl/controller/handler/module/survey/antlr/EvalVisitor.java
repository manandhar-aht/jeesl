package org.jeesl.controller.handler.module.survey.antlr;

import java.util.Map;

public class EvalVisitor extends SimpleBooleanBaseVisitor<Boolean>
{
	private final Map<String,Boolean> variables;
	
	public EvalVisitor(Map<String,Boolean> variables)
	{
		this.variables=variables;
	}
	
	@Override
	public Boolean visitParse(SimpleBooleanParser.ParseContext ctx)
	{
		return super.visit(ctx.expression());
	}
	
	@Override
	public Boolean visitNotExpression(SimpleBooleanParser.NotExpressionContext ctx)
	{
		return !(this.visit(ctx.expression()));
	}
	
	@Override
	public Boolean visitIdentifierExpression(SimpleBooleanParser.IdentifierExpressionContext ctx)
	{
		return variables.get(ctx.identifier().getText());
	}
	
	@Override
	public Boolean visitParenExpression(SimpleBooleanParser.ParenExpressionContext ctx)
	{
		return super.visit(ctx.expression());
	}
	
	@Override
	public Boolean visitBinaryExpression(SimpleBooleanParser.BinaryExpressionContext ctx)
	{
		if(ctx.op.AND() != null)
		{
			return asBoolean(ctx.left) && asBoolean(ctx.right);
		}
		else if (ctx.op.OR() != null)
		{
			return asBoolean(ctx.left) || asBoolean(ctx.right);
		}
		throw new RuntimeException("not implemented: binary operator "+ctx.op.getText());
	}
	
	
	private boolean asBoolean(SimpleBooleanParser.ExpressionContext ctx)
	{
		return (boolean)visit(ctx);
	}
}
