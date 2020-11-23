package com.github.avlomakin.tseytin

import com.github.avlomakin.model.*
import java.util.concurrent.atomic.AtomicInteger

class TseytinTransformationContext(
    val clauses: Set<StringClause>,
    val literal: StringLiteral
) {

    fun toCNF(): Pair<CNF, Map<String, Int>> {
        val allClauses = clauses.plus(StringClause(setOf(literal)))

        val stringIdToIntId = HashMap<String, Int>()
        val literals = HashSet<Literal>()
        val nextValue = AtomicInteger()

        val clauses = allClauses.map {
            Clause(it.literals.map { stringLiteral ->
                stringIdToIntId.computeIfAbsent(stringLiteral.id) { nextValue.incrementAndGet() }
                val literal = Literal(stringIdToIntId.getValue(stringLiteral.id), stringLiteral.negated)
                literals.add(literal)
                literal
            }.toSet())
        }.toSet()

        return Pair(CNF(clauses, stringIdToIntId.values.toSet(), literals), stringIdToIntId)
    }
}