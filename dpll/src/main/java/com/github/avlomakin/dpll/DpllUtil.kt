package com.github.avlomakin.dpll

import com.github.avlomakin.dpll.model.DpllClause
import com.github.avlomakin.dpll.model.DpllCnf
import com.github.avlomakin.model.CNF
import com.github.avlomakin.model.Clause
import java.util.*

object DpllUtil {

    fun Clause.toDpllClause(): DpllClause{
        return DpllClause(this.literals)
    }

    fun CNF.toDpllCNF() : DpllCnf {
        val clauses = Stack<DpllClause>()
        clauses.addAll(this.clauses.map{it.toDpllClause()})
        return DpllCnf(clauses, this.literals)
    }
}