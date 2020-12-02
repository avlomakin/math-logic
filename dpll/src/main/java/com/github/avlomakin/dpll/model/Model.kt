package com.github.avlomakin.dpll.model

import com.github.avlomakin.model.Literal
import com.github.avlomakin.model.contrary
import java.lang.Exception
import kotlin.collections.HashMap
import kotlin.text.StringBuilder

class Model(private val values: HashMap<Literal, Int> = HashMap()) {

    fun pushLiteral(literal: Literal, level: Int): Model {
        if (values.containsKey(literal.contrary())) {
            throw Exception("cannot push $literal with level $level: model already contains ${literal.contrary()} with level $level")
        }

        values[literal] = level
        return this
    }

    fun getVariableIdsWithValues(): Set<Int> {
        return values.keys.map { it.id }.toSet()
    }

    override fun toString(): String {
        return values.entries.sortedBy { it.key.id }.toString()
    }

    fun toPrettyString(variablePrefix: String = ""): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Model: ")
        values.forEach { (literal, _) ->
            stringBuilder.append("${variablePrefix}${literal.id} = ${!literal.negated}, ")
        }
        stringBuilder.append(" ...")
        return stringBuilder.toString()
    }

    fun toPrettyString(namings: Map<Int, String>): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Model: ")
        values.forEach { (literal, _) ->
            stringBuilder.append("${namings[literal.id]} = ${!literal.negated}, ")
        }
        stringBuilder.append(" ...")
        return stringBuilder.toString()
    }

    fun removeLevel(level: Int) {
        values.filter { it.value >= level }.keys.forEach{
            values.remove(it)
        }
    }

    fun containsContraryWithLevel(literal: Literal, level: Int): Boolean {
        return values[literal.contrary()] == level
    }
}
