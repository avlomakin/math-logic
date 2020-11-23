package com.github.avlomakin.model

data class StringClause(val literals: Set<StringLiteral>) {

    override fun toString(): String {
        return "(${literals.joinToString(separator = " | ")})"
    }
}
