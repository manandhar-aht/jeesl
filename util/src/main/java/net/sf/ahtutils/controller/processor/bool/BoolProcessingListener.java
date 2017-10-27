// Generated from BoolProcessing.g4 by ANTLR 4.7

package net.sf.ahtutils.controller.processor.bool;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BoolProcessingParser}.
 */
public interface BoolProcessingListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BoolProcessingParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(BoolProcessingParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link BoolProcessingParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(BoolProcessingParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link BoolProcessingParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(BoolProcessingParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BoolProcessingParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(BoolProcessingParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BoolProcessingParser#binary}.
	 * @param ctx the parse tree
	 */
	void enterBinary(BoolProcessingParser.BinaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link BoolProcessingParser#binary}.
	 * @param ctx the parse tree
	 */
	void exitBinary(BoolProcessingParser.BinaryContext ctx);
}