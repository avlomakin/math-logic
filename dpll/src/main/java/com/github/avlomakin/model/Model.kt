package com.github.avlomakin.model

import kotlin.collections.HashMap
import kotlin.text.StringBuilder

class Model(private val values: HashMap<Int, Boolean> = HashMap()) {

    fun putLiteral(literal: Literal) : Model {
        values[literal.id] = !literal.negated
        return this
    }

    fun getVariableIdsWithValues(): Set<Int> {
        return values.keys
    }

    fun toPrettyString(variablePrefix: String = ""): String{
        val stringBuilder = StringBuilder()
        stringBuilder.append("Model: ")
        values.forEach {(id, value) ->
            stringBuilder.append("${variablePrefix}$id = $value, ")
        }
        stringBuilder.append(" ...")
        return stringBuilder.toString()
    }
}
