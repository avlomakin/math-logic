package com.github.avlomakin.dpll.model

import com.github.avlomakin.model.Literal
import java.util.*

class DpllCnf(
    private val clauses: Stack<DpllClause>,
    literals: Set<Literal>
) {
    val propVariables = literals.map { it.id }.toSet()

    fun getClauses(): List<DpllClause> {
        return clauses
    }

    fun isEmptySet(): Boolean {
        return clauses.all { clause -> clause.isClauseEliminated() }
    }

    fun containsEmptyDisjunction(): Boolean {
        return clauses.any { clause -> clause.isAllLiteralsEliminated() }
    }

    fun getUnits(): List<Literal> {
        return clauses.filter { !it.isClauseEliminated() }.mapNotNull { clause ->
            if (clause.isUnit()) {
                clause.getCurrentLiterals().first()
            } else {
                null
            }
        }
    }

    fun getPureLiterals(): List<Literal> {
        return clauses.filter { !it.isClauseEliminated() }.flatMap { it.getCurrentLiterals() }
            .groupBy { l -> l.id }
            .filter { (_, literals) -> literals.all { it == literals[0] } }
            .map { it.value[0] }
    }

    fun pushLiteral(literal: Literal, level: Int): DpllCnf {
        clauses.push(DpllClause(setOf(literal), level))
        return this
    }

    fun popLiteral(): DpllCnf {
        val clause = clauses.pop()
        clauses.forEach { it.rollback(clause.level - 1) }
        return this
    }

}