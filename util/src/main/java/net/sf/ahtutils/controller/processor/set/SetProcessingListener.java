// Generated from SetProcessing.g4 by ANTLR 4.7

package net.sf.ahtutils.controller.processor.set;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SetProcessingParser}.
 */
public interface SetProcessingListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SetProcessingParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(SetProcessingParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SetProcessingParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(SetProcessingParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SetProcessingParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(SetProcessingParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SetProcessingParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(SetProcessingParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SetProcessingParser#binary}.
	 * @param ctx the parse tree
	 */
	void enterBinary(SetProcessingParser.BinaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SetProcessingParser#binary}.
	 * @param ctx the parse tree
	 */
	void exitBinary(SetProcessingParser.BinaryContext ctx);
}