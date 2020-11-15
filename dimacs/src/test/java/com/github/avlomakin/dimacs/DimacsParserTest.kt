package com.github.avlomakin.dimacs

import org.junit.Test


class DimacsParserTest {

    @Test
    fun test(){
        val cnfString  = """
            c
            c start with comments
            c
            c
            p cnf 5 3
            1 -5 4 0
            -1 5 3 4 0
            -3 -4 0
        """.trimIndent()

        println(DimacsParser.createFromString(cnfString).getCNF().clauses)
    }


}