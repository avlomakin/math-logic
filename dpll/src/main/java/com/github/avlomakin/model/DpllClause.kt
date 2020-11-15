package com.github.avlomakin.model

import kotlin.collections.HashMap

class DpllClause(private val literals: Set<Literal>, private val level: Int = 0) {

    private var propagatedLiteral : Pair<Int, Literal>? = null
    private val eliminatedLiterals = HashMap<Int, HashSet<Literal>>()

    fun isAllLiteralsEliminated(): Boolean {
        return literals.map{it.id} == eliminatedLiterals.values.flatten()
    }

    fun isClauseEliminated() = propagatedLiteral != null

    fun isUnit(): Boolean {
        return getCurrentLiterals().size == 1
    }

    fun getCurrentLiterals(): Set<Literal> {
        return literals - getAllEliminated()
    }

    private fun getAllEliminated() : Set<Literal>{
        return eliminatedLiterals.values.flatten().toSet()
    }


    fun unitPropagate(level: Int, literal: Literal) {
        if(propagatedLiteral != null)
            return

        if(getCurrentLiterals().contains(literal)){
            propagatedLiteral  = Pair(level, literal)
        }
    }

    fun eliminateLiteral(level: Int, literal: Literal){
        eliminatedLiterals.putIfAbsent(level, HashSet())

        if(getCurrentLiterals().contains(literal)){
            eliminatedLiterals[level]!!.add(literal)
        }
    }

    fun rollback(level: Int) {
        check(level <= this.level) { "rollback level $level is greater than current clause level ${this.level}" }

        if(propagatedLiteral != null && propagatedLiteral!!.first >level) {
            propagatedLiteral = null
        }

        for (i in (level + 1)..this.level) {
            if(eliminatedLiterals.containsKey(i)){
                eliminatedLiterals[i] = HashSet()
            }
        }
    }
}
