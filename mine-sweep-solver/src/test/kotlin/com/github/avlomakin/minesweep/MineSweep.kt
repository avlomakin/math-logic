package com.github.avlomakin.minesweep

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.slf4j.LoggerFactory

@RunWith(Parameterized::class)
class MineSweepTest(val params: Pair<Pair<Int, Int>, Boolean>) {
    private val log = LoggerFactory.getLogger(this::class.java)

    companion object {
        @Parameters
        @JvmStatic
        fun parameters() : List<Pair<Pair<Int, Int>, Boolean>>{
            return listOf(
                Pair(Pair(1,0), true),
                Pair(Pair(0,2), true),
                Pair(Pair(2,1), false),
                Pair(Pair(2,2), true),
            )
        }

    }

    @Test
    fun `check field safety`(){
        log.info("test (${params.first.first}, ${params.first.second})")
        val field = """
            1 * x
            x 2 x
            1 x x
        """.trimIndent().split("\n").map { line -> line.split(" ")}
        val formula = FieldParser.parseField(field)
        val answer = FieldParser.isFieldSafe(formula, params.first.first, params.first.second)
        assert(answer == params.second)
    }
}