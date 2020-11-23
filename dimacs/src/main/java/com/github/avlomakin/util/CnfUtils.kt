package com.github.avlomakin.util

import com.github.avlomakin.model.CNF
import com.github.avlomakin.model.Clause
import com.github.avlomakin.model.Literal
import com.github.avlomakin.model.StringClause
import java.util.concurrent.atomic.AtomicInteger

object CnfUtils {

    fun toCNF(stringClauses: Collection<StringClause>): Pair<CNF, Map<String, Int>> {
        val stringIdToIntId = HashMap<String, Int>()
        val literals = HashSet<Literal>()
        val nextValue = AtomicInteger()

        val clauses = stringClauses.map {
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