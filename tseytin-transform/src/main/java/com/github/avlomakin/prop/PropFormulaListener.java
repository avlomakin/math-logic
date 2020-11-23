package com.github.avlomakin.prop;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PropFormulaParser}.
 */
public interface PropFormulaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code logicalAnd}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 */
	void enterLogicalAnd(PropFormulaParser.LogicalAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalAnd}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 */
	void exitLogicalAnd(PropFormulaParser.LogicalAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalOr}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 */
	void enterLogicalOr(PropFormulaParser.LogicalOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalOr}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 */
	void exitLogicalOr(PropFormulaParser.LogicalOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenthesis}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 */
	void enterParenthesis(PropFormulaParser.ParenthesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenthesis}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 */
	void exitParenthesis(PropFormulaParser.ParenthesisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalImply}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 */
	void enterLogicalImply(PropFormulaParser.LogicalImplyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalImply}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 */
	void exitLogicalImply(PropFormulaParser.LogicalImplyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literal}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 */
	void enterLiteral(PropFormulaParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literal}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 */
	void exitLiteral(PropFormulaParser.LiteralContext ctx);
}