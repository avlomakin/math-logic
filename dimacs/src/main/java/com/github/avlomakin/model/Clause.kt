package com.github.avlomakin.model

open class Clause(val literals: Set<Literal>) {

    override fun toString(): String {
        return "${this.literals.joinToString(separator = " ") } 0"
    }
}
