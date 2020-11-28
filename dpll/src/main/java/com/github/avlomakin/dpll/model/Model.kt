package com.github.avlomakin.dpll.model

import com.github.avlomakin.model.Literal
import java.util.*
import kotlin.collections.HashMap
import kotlin.text.StringBuilder

class Model(private val values: HashMap<Int, Boolean> = HashMap()) {

    fun pushLiteral(literal: Literal): Model {
        values[literal.id] = !literal.negated
        return this
    }

    fun getVariableIdsWithValues(): Set<Int> {
        return values.keys
    }

    override fun toString(): String {
        return values.entries.sortedBy { it.key }.toString()
    }

    fun toPrettyString(variablePrefix: String = ""): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Model: ")
        values.forEach { (id, value) ->
            stringBuilder.append("${variablePrefix}$id = $value, ")
        }
        stringBuilder.append(" ...")
        return stringBuilder.toString()
    }

    fun toPrettyString(namings: Map<Int, String>): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Model: ")
        values.forEach { (id, value) ->
            stringBuilder.append("${namings[id]} = $value, ")
        }
        stringBuilder.append(" ...")
        return stringBuilder.toString()
    }

    fun removeValue(id: Int) {
        values.remove(id)
    }
}
