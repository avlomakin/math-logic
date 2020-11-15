package com.github.avlomakin.dpll

import com.github.avlomakin.dpll.model.DpllCnf
import com.github.avlomakin.model.Literal
import com.github.avlomakin.dpll.model.Model
import com.github.avlomakin.model.contrary

fun dpll(cnf: DpllCnf, model: Model, level: Int) : Model?  {

    if(cnf.isEmptySet()){
        return model
    }
    if(cnf.containsEmptyDisjunction()) {
        return null
    }

    cnf.getUnits().forEach { literal ->
        unitPropagate(cnf, literal, level)
        model.putLiteral(literal)
    }

    cnf.getPureLiterals().forEach { literal ->
        unitPropagate(cnf, literal, level)
        model.putLiteral(literal)
    }

    if(cnf.isEmptySet()){
        return model
    }
    if(cnf.containsEmptyDisjunction()) {
        return null
    }

    val nextLiteral = Literal(chooseNextLiteralId(cnf, model), false)
    val nextLevel = level + 1

    return dpll(cnf.pushLiteral(nextLiteral, nextLevel), model.putLiteral(nextLiteral), nextLevel) ?: run {
        cnf.popLiteral()
        val contrary = nextLiteral.contrary()
        dpll(cnf.pushLiteral(contrary, nextLevel), model.putLiteral(contrary), nextLevel)
    }
}

fun chooseNextLiteralId(cnf: DpllCnf, model: Model): Int {
    return (cnf.literals.map{it.id}.toSet().toList() - model.getVariableIdsWithValues())[0]
}

fun unitPropagate(cnf: DpllCnf, literal: Literal, level: Int)  {
    cnf.getClauses()
        .onEach { clause -> clause.unitPropagate(level, literal) }
        .onEach { clause -> clause.eliminateLiteral(level, literal.contrary()) }
}