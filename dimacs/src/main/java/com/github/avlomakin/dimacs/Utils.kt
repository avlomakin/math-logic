package com.github.avlomakin.dimacs

import com.github.avlomakin.model.CNF

fun CNF.toDimacs(): String {
    return """
        p cnf ${this.propVariables.maxOrNull()} ${this.clauses.size} 
        ${this.clauses.joinToString(separator = "\n")}
    """.trimIndent()

}