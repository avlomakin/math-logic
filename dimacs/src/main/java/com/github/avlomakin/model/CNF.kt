package com.github.avlomakin.model

import java.util.*

class CNF(
    val clauses: Set<Clause>,
    val propVariables: Set<Int>,
    val literals: Set<Literal>
)