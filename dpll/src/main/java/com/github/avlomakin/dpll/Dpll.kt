package com.github.avlomakin.dpll

import com.github.avlomakin.dpll.SatDecision.SAT
import com.github.avlomakin.dpll.SatDecision.UNSAT
import com.github.avlomakin.dpll.model.DpllCnf
import com.github.avlomakin.model.Literal
import com.github.avlomakin.dpll.model.Model
import com.github.avlomakin.model.contrary

fun dpll(cnf: DpllCnf, model: Model, level: Int): Model? {

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

    var result = dpll(cnf.pushLiteral(nextLiteral, nextLevel), model, nextLevel)
    cnf.popLiteral()
    if (result == null) {
        model.removeLevel(level)
        val contrary = nextLiteral.contrary()
        result = dpll(cnf.pushLiteral(contrary, nextLevel), model, nextLevel)
        cnf.popLiteral()
        if (result == null) {
            model.removeLevel(level)
        }
    }

    return result
}

fun chooseNextLiteralId(cnf: DpllCnf, model: Model): Int? {
    val unassignedLiterals = cnf.propVariables.toList() - model.getVariableIdsWithValues()
    return if (unassignedLiterals.isEmpty()) {
        null
    } else {
        unassignedLiterals[0]
    }
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
    val model = dpll(dpllCnf, Model(), 0)
    return SatResult(if (model == null) UNSAT else SAT, model)
}