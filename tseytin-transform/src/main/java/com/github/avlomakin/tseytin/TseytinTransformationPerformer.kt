package com.github.avlomakin.tseytin

import com.github.avlomakin.prop.PropFormulaLexer
import com.github.avlomakin.prop.PropFormulaParser
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree


object TseytinTransformationPerformer {
    fun transform(expression: String): TseytinTransformationContext {
        val stream: CharStream = CharStreams.fromString(expression.trim())
        val lexer = PropFormulaLexer(stream)
        lexer.removeErrorListeners()
        val throwingErrorListener = ThrowingErrorListener()
        lexer.addErrorListener(throwingErrorListener)
        val tokens = CommonTokenStream(lexer)
        val parser = PropFormulaParser(tokens)
        parser.removeErrorListeners()
        parser.addErrorListener(throwingErrorListener)
        val parseTree: ParseTree = parser.formula()
        val evaluator = TseytinTransformEngine()
        return evaluator.visit(parseTree)
    }
}