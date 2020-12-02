package com.github.avlomakin

import com.github.avlomakin.dpll.DpllUtil.toDpllCNF
import com.github.avlomakin.dpll.SatDecision
import com.github.avlomakin.dpll.solve
import com.github.avlomakin.model.CNF
import com.github.avlomakin.model.Clause
import com.github.avlomakin.util.CnfUtils.toCNF

fun getUnsatCore(cnf: CNF): CNF {
    val clauses = cnf.clauses.toMutableList()
    var currentPosition = 0

    for (i in 0 until clauses.size){
        val temp = clauses[currentPosition]
        clauses.removeAt(currentPosition)
        val decision = solve(toCNF(clauses).toDpllCNF()).decision
        if(decision == SatDecision.SAT){
            clauses.add(currentPosition, temp)
            currentPosition++
        }
    }
    return toCNF(clauses)
}