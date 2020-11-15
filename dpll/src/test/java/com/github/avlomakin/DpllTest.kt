package com.github.avlomakin

import com.github.avlomakin.model.*
import org.junit.Test
import java.util.*


class DpllTest {

    @Test
    fun simpleTest() {
        val a0 = Literal(0, false)
        val a1 = Literal(1, false)
        val a0Contrary = a0.contrary()
        val clauses = Stack<DpllClause>()
        clauses.addAll(
            listOf(
                DpllClause(setOf(a0, a1)),
                DpllClause(setOf(a0Contrary, a1))
            )
        )

        val cnf = DpllCnf(clauses, setOf(a0, a1, a0Contrary))

        val result = dpll(cnf, Model(), 0)

        println(result?.toPrettyString("a") ?: "UNSAT")
    }


    @Test
    fun `test from lecture`() {
        val a = Literal(0, false)
        val b = Literal(1, false)
        val c = Literal(2, false)
        val d = Literal(3, false)

        val notA = Literal(0, true)
        val notB = Literal(1, true)
        val notC = Literal(2, true)
        val notD = Literal(3, true)

        val clauses = Stack<DpllClause>()
        clauses.addAll(
            listOf(
                DpllClause(setOf(notA, b, c)),
                DpllClause(setOf(a, c, d)),
                DpllClause(setOf(a, c, notD)),
                DpllClause(setOf(a, notC, d)),
                DpllClause(setOf(a, notC, notD)),
                DpllClause(setOf(notB, notC, d)),
                DpllClause(setOf(notA, b, notC)),
                DpllClause(setOf(notA, notB, c))
            )
        )

        val cnf = DpllCnf(clauses, setOf(a, b, c, d, notA, notB, notC, notD))

        val result = dpll(cnf, Model(), 0)

        assert(result != null)
    }
}