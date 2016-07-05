// Generated from SetProcessing.g4 by ANTLR 4.5

	package net.sf.ahtutils.controller.processor.set;

	import java.util.List;
    import java.util.ArrayList;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SetProcessingParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SetProcessingVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SetProcessingParser#parse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParse(SetProcessingParser.ParseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SetProcessingParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(SetProcessingParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SetProcessingParser#binary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinary(SetProcessingParser.BinaryContext ctx);
}