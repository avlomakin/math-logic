package com.github.avlomakin.model

data class StringLiteral(
    val id: String,
    val negated: Boolean
) {
    override fun toString(): String {
        return "${if(negated) "!" else ""}$id"
    }
}

fun StringLiteral.contrary() : StringLiteral{
    return StringLiteral(id, !negated)
}