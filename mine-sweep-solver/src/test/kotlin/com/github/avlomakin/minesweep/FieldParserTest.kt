package com.github.avlomakin.minesweep

import com.github.avlomakin.minesweep.FieldParser.generateBombsAroundCondition
import com.github.avlomakin.minesweep.FieldParser.isFieldSafe
import com.github.avlomakin.minesweep.FieldParser.parseField
import org.junit.Test

class FieldParserTest {


    @Test
    fun `safe field`(){
        val field = """
            1 * x
            x 2 x
            1 x x
        """.trimIndent().split("\n").map { line -> line.split(" ")}
        val formula = parseField(field)
        val answer = isFieldSafe(formula, 2, 1)
        println(answer)
    }

    @Test
    fun `unsafe field`(){
        val field = """
            1 x
            x x
        """.trimIndent().split("\n").map { line -> line.split(" ")}
        val formula = parseField(field)
        val answer = isFieldSafe(formula, 0, 1)
        println(answer)
    }
}