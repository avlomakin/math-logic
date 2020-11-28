package com.github.avlomakin.dpll.model

import com.github.avlomakin.model.Literal
import kotlin.collections.HashMap

class DpllClause(private val literals: Set<Literal>, val level: Int = 0) {

    private var propagatedLiteral: Pair<Int, Literal>? = null
    private val eliminatedLiterals = HashMap<Int, HashSet<Literal>>()

    fun isAllLiteralsEliminated(): Boolean {
        return getCurrentLiterals().isEmpty()
    }

    fun isClauseEliminated() = propagatedLiteral != null

    fun isUnit(): Boolean {
        return getCurrentLiterals().size == 1
    }

    fun getCurrentLiterals(): Set<Literal> {
        return literals - getAllEliminated()
    }

    private fun getAllEliminated(): Set<Literal> {
        return eliminatedLiterals.values.flatten().toSet()
    }


    fun unitPropagate(level: Int, literal: Literal) {
        if (isClauseEliminated()) return

        if (getCurrentLiterals().contains(literal)) {
            propagatedLiteral = Pair(level, literal)
        }
    }

    fun eliminateLiteral(level: Int, literal: Literal) {
        if (isClauseEliminated()) return

        eliminatedLiterals.putIfAbsent(level, HashSet())

        if (getCurrentLiterals().contains(literal)) {
            eliminatedLiterals[level]!!.add(literal)
        }
    }

    fun rollback(level: Int) {
        if (propagatedLiteral != null && propagatedLiteral!!.first > level) {
            propagatedLiteral = null
        }

        for (i in this.eliminatedLiterals.keys.filter { it > level }) {
                eliminatedLiterals[i] = HashSet()
        }
    }

    override fun toString(): String {
        val dimacs = "${this.getCurrentLiterals().joinToString(separator = " ")} 0"
        return if(isClauseEliminated()) {
            return "ELIMINATED ($dimacs)"
        } else {
            dimacs
        }
    }
}
