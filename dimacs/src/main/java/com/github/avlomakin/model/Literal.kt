package com.github.avlomakin.model

data class Literal(
    val id: Int,
    val negated: Boolean
){
    override fun toString(): String {
        return "${if (negated) "-" else ""}$id"
    }
}

fun Literal.contrary() : Literal{
    return Literal(id, !negated)
}