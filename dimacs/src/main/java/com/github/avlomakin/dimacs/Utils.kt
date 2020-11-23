package com.github.avlomakin.dimacs

import com.github.avlomakin.model.CNF

fun CNF.toDimacs(): String {
    return this.clauses.joinToString(separator = "\n")
}