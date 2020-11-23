package com.github.avlomakin.model

class CNF(
    val clauses: Set<Clause>,
    val propVariables: Set<Int>,
    val literals: Set<Literal>
)