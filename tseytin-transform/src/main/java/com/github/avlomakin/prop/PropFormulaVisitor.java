package com.github.avlomakin.prop;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PropFormulaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PropFormulaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code logicalAnd}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAnd(PropFormulaParser.LogicalAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalOr}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOr(PropFormulaParser.LogicalOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenthesis}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesis(PropFormulaParser.ParenthesisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalImply}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalImply(PropFormulaParser.LogicalImplyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literal}
	 * labeled alternative in .
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(PropFormulaParser.LiteralContext ctx);
}