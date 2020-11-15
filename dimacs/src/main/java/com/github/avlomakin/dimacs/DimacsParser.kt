package com.github.avlomakin.dimacs

import com.github.avlomakin.model.CNF
import com.github.avlomakin.model.Clause
import com.github.avlomakin.model.Literal
import java.io.File
import kotlin.math.abs
import kotlin.math.sign

class DimacsParser private constructor(private val fileLines: List<String>) {

    fun getCNF(): CNF {
        try {
            //ignore comments
            val preparedLines = fileLines.filter { line -> !line.startsWith(COMMENT_PREFIX) }.map { it.trim() }

            //first line should be the header in the  following format: cnf n1 n2
            check(preparedLines.isNotEmpty()) { "no header found" }

            val header = preparedLines[0].split(" ")
            check(header.size == 4) { "invalid header format" }

            val varCount = header[2].toInt()
            val linesCount = header[3].toInt()

            check(preparedLines.size == linesCount + 1) { "number of lines is different from the header value: $linesCount vs ${preparedLines.size}" }

            val propVariables = HashSet<Int>()
            val allLiterals = HashSet<Literal>()
            val clauses = HashSet<Clause>()
            for (i in 1..linesCount) {
                try {
                    clauses.add(parseClauseLine(preparedLines[i], propVariables, allLiterals, varCount))
                } catch (t: Throwable) {
                    throw Exception("Error occurred while processing line $i: ${t.message}", t)
                }
            }
            return CNF(clauses, propVariables, allLiterals)
        } catch (t: Throwable) {
            throw Exception("Cannot generate CNF: ${t.message}", t)
        }

    }

    private fun parseClauseLine(line: String, propVariables: HashSet<Int>, allLiterals: HashSet<Literal>, varCount: Int): Clause {

        check(line.endsWith(" 0")) { "each clause line should end with 0" }

        val numbers = line
            .removeSuffix(" 0")
            .split(" ")
            .map { it.toInt() }

        val literals = numbers.map { number ->
            val id = abs(number)

            check(id <= varCount) { "variable $number is out of bounds: [-$varCount, $varCount]" }

            propVariables.add(id)
            Literal(id, number.sign < 0)
        }.toSet()
        allLiterals.addAll(literals)

        return Clause(literals)
    }

    companion object {
        const val COMMENT_PREFIX = "c"

        fun createFromFile(file: File): DimacsParser {
            return DimacsParser(file.readLines())
        }

        fun createFromString(str: String): DimacsParser {
            return DimacsParser(str.split("\n"))
        }
    }

}