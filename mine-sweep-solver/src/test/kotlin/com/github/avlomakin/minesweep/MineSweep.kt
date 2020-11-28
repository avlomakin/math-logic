package com.github.avlomakin.minesweep

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class MineSweepTest(val params: Pair<Pair<Int, Int>, Boolean>) {

    companion object {
        @Parameters
        @JvmStatic
        fun parameters() : List<Pair<Pair<Int, Int>, Boolean>>{
            return listOf(
                Pair(Pair(0,1), true),
                Pair(Pair(0,2), true),
                Pair(Pair(2,1), true),
                Pair(Pair(2,2), true),
            )
        }

    }

    @Test
    fun `safe field`(){
        val field = """
            1 * x
            x 2 x
            1 x x
        """.trimIndent().split("\n").map { line -> line.split(" ")}
        val formula = FieldParser.parseField(field)
        val answer = FieldParser.isFieldSafe(formula, params.first.first, params.first.first)
        assert(answer == params.second)
    }
}