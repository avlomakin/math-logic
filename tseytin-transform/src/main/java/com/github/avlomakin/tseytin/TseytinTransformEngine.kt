package com.github.avlomakin.tseytin

import com.github.avlomakin.model.*
import com.github.avlomakin.prop.PropFormulaBaseVisitor
import com.github.avlomakin.prop.PropFormulaParser.*
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class TseytinTransformEngine : PropFormulaBaseVisitor<TseytinTransformationContext>() {

    private val log = LoggerFactory.getLogger(this::class.java)

    companion object {
        const val VAR_PREFIX = "p_"
    }

    private val nextVal = AtomicInteger()

    override fun visitLiteral(ctx: LiteralContext): TseytinTransformationContext {
        val variable = ctx.PROP_VARIABLE().text
        check(!variable.startsWith(VAR_PREFIX)) { "variable name cannot start with '$VAR_PREFIX'" }

        log.trace("${ctx.NOT()?.text ?: ""}$variable generated")
        return TseytinTransformationContext(emptySet(), StringLiteral(variable, ctx.NOT() != null))
    }

    override fun visitLogicalAnd(ctx: LogicalAndContext): TseytinTransformationContext {
        val left = visit(ctx.formula(0))
        val right = visit(ctx.formula(1))
        val literal = StringLiteral(VAR_PREFIX + nextVal.incrementAndGet(), false)

        val clauses = HashSet<StringClause>()

        clauses.add(StringClause(setOf(literal.contrary(), left.literal)))
        clauses.add(StringClause(setOf(literal.contrary(), right.literal)))
        clauses.add(StringClause(setOf(literal, left.literal.contrary(), right.literal.contrary())))

        log.trace("new clauses: $clauses")

        clauses.addAll(left.clauses)
        clauses.addAll(right.clauses)

        log.trace("all clauses: $clauses")

        return TseytinTransformationContext(clauses, literal)
    }

    override fun visitParenthesis(ctx: ParenthesisContext): TseytinTransformationContext {
        val result = visit(ctx.formula())
        return TseytinTransformationContext(
            result.clauses,
            if (ctx.NOT() != null) result.literal.contrary() else result.literal
        )
    }

    override fun visitLogicalOr(ctx: LogicalOrContext): TseytinTransformationContext {
        val left = visit(ctx.formula(0))
        val right = visit(ctx.formula(1))
        val literal = StringLiteral(VAR_PREFIX + nextVal.incrementAndGet(), false)

        val clauses = HashSet<StringClause>()

        clauses.add(StringClause(setOf(literal.contrary(), left.literal, right.literal)))
        clauses.add(StringClause(setOf(literal, left.literal.contrary())))
        clauses.add(StringClause(setOf(literal, right.literal.contrary())))

        log.trace("new clauses: $clauses")

        clauses.addAll(left.clauses)
        clauses.addAll(right.clauses)

        log.trace("all clauses: $clauses")

        return TseytinTransformationContext(clauses, literal)
    }
}