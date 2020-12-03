package com.github.avlomakin.dpll

import com.github.avlomakin.dpll.SatDecision.SAT
import com.github.avlomakin.dpll.SatDecision.UNSAT
import com.github.avlomakin.dpll.model.DpllCnf
import com.github.avlomakin.model.Literal
import com.github.avlomakin.dpll.model.Model
import com.github.avlomakin.model.contrary

fun dpll(cnf: DpllCnf, model: Model, level: Int, stat: DpllStat): Model? {

    if (cnf.isEmptySet()) {
        return model
    }
    if (cnf.containsEmptyDisjunction()) {
        return null
    }

    cnf.getUnits().forEach { literal ->
        if (!model.containsContraryWithLevel(literal, level)) {
            unitPropagate(cnf, literal, level)
            model.pushLiteral(literal, level)
        }
    }

    cnf.getPureLiterals().forEach { literal ->
        unitPropagate(cnf, literal, level)
        model.pushLiteral(literal, level)
    }

    if (cnf.isEmptySet()) {
        return model
    }
    if (cnf.containsEmptyDisjunction()) {
        return null
    }

    val id = chooseNextLiteralId(cnf, model) ?: return null
    val nextLiteral = Literal(id, false)
    val nextLevel = level + 1

    var result = nextGuess(cnf, nextLiteral, nextLevel, stat, model)

    if (result == null) {
        val contrary = nextLiteral.contrary()
        result = nextGuess(cnf, contrary, nextLevel, stat, model)
    }

    return result
}

private fun nextGuess(cnf: DpllCnf, nextLiteral: Literal, nextLevel: Int, stat: DpllStat, model: Model): Model? {
    cnf.pushLiteral(nextLiteral, nextLevel)
    stat.pushLiteral(nextLiteral)
    val result = dpll(cnf, model, nextLevel, stat)
    cnf.popLiteral()
    stat.popLiteral()
    if (result == null) {
        model.removeLevel(nextLevel)
    }
    return result
}

fun chooseNextLiteralId(cnf: DpllCnf, model: Model): Int? {
    return (cnf.propVariables.toList() - model.getVariableIdsWithValues()).firstOrNull()
}

fun unitPropagate(cnf: DpllCnf, literal: Literal, level: Int) {
    cnf.getClauses()
        .onEach { clause -> clause.unitPropagate(level, literal) }
        .onEach { clause -> clause.eliminateLiteral(level, literal.contrary()) }
}

enum class SatDecision {
    SAT,
    UNSAT
}

class SatResult(
    val decision: SatDecision,
    val model: Model?
)

fun solve(dpllCnf: DpllCnf): SatResult {
    val model = dpll(dpllCnf, Model(), 0, DpllStat())
    return SatResult(if (model == null) UNSAT else SAT, model)
}