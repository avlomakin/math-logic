package com.github.avlomakin.dpll

import com.github.avlomakin.model.Literal
import java.util.concurrent.atomic.AtomicInteger

class DpllStat {

    private val recursionLevel = AtomicInteger()
    private val guessesCount = AtomicInteger()
    private val onceSetPropVariables = mutableSetOf<Int>()

    fun pushLiteral(literal: Literal){
        guessesCount.incrementAndGet()
        recursionLevel.incrementAndGet()
        onceSetPropVariables.add(literal.id)
    }

    fun popLiteral(){
        recursionLevel.decrementAndGet()
    }

    override fun toString(): String {
        return "recursionLevel=$recursionLevel, guessesCount=$guessesCount, onceSetPropVariables=$onceSetPropVariables"
    }
}
