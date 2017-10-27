// Generated from BoolProcessing.g4 by ANTLR 4.7

package net.sf.ahtutils.controller.processor.bool;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BoolProcessingParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BoolProcessingVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BoolProcessingParser#parse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParse(BoolProcessingParser.ParseContext ctx);
	/**
	 * Visit a parse tree produced by {@link BoolProcessingParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(BoolProcessingParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BoolProcessingParser#binary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinary(BoolProcessingParser.BinaryContext ctx);
}