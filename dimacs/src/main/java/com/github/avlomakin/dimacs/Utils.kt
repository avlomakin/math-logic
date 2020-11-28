package com.github.avlomakin.dimacs

import com.github.avlomakin.model.CNF

fun CNF.toDimacs(): String {
    return """
        p cnf ${this.propVariables.size} ${this.clauses.size} 
        ${this.clauses.joinToString(separator = "\n")}
    """.trimIndent()

}