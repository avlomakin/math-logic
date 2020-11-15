package com.github.avlomakin.model

data class Literal(
    val id: Int,
    val negated: Boolean
)

fun Literal.contrary() : Literal{
    return Literal(id, !negated)
}