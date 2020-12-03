package com.github.avlomakin

import com.github.avlomakin.dimacs.DimacsParser.Companion.createFromString
import com.github.avlomakin.dimacs.toDimacs
import com.github.avlomakin.dpll.DpllUtil.toDpllCNF
import com.github.avlomakin.dpll.SatDecision.SAT
import com.github.avlomakin.dpll.SatDecision.UNSAT
import com.github.avlomakin.dpll.model.DpllClause
import com.github.avlomakin.dpll.model.DpllCnf
import com.github.avlomakin.dpll.solve
import com.github.avlomakin.model.Literal
import com.github.avlomakin.model.contrary
import com.github.avlomakin.tseytin.TseytinTransformationPerformer
import com.github.avlomakin.util.CnfUtils
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

        val result = solve(cnf)

        assert(result.decision == SAT)
        println(result.model!!.toPrettyString("p_"))
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

        val result = solve(cnf)

        assert(result.decision == SAT)
        println(result.model!!.toPrettyString("p_"))
    }

    @Test
    fun `test with Tseytin Transformation`(){
        val formula  = "!((p&q)|!r)"
        val stringClauses = TseytinTransformationPerformer.transform(formula).clauses

        println("Tseytin Transformation: ")
        println(stringClauses)

        val (cnf, map) = CnfUtils.toCNF(stringClauses)

        println("Mappings:")
        println(map.entries.joinToString { "${it.key} : ${it.value}" })
        println("DIMACS:")
        println(cnf.toDimacs())

        val result = solve(cnf.toDpllCNF())

        assert(result.decision == SAT)
        println(result.model!!.toPrettyString("p_"))
    }

    @Test
    fun `test unsat`(){
        val formula  = "p&!p"
        val context = TseytinTransformationPerformer.transform(formula)

        println("Tseytin Transformation: ")
        println(context.clauses)

        val (cnf, map) = context.toCNF()

        println("Mappings:")
        println(map.entries.joinToString { "${it.key} : ${it.value}" })
        println("DIMACS:")
        println(cnf.toDimacs())

        val result = solve(cnf.toDpllCNF())

        println(result.decision == UNSAT)
    }

    @Test
    fun `shouldn't work if dpll is incorrect`(){
        val s = """p cnf 4 5
            -1 2 3 0
            -1 4 -3 0
            -4 -2 0
            -2 3 4 0
            -4 -3 2 0"""

        val cnf = createFromString(s).getCNF()

        println(cnf.toDimacs())

        val result = solve(cnf.toDpllCNF())

        assert(result.decision == SAT)
        println(result.model!!.toPrettyString())

    }

    @Test
    fun `simple mine sweep DIMACS`(){
        val s ="""p cnf 46 53
        -29 30 0
        41 -2 -3 0
        28 -1 -9 0
        -24 44 0
        4 -31 -2 0
        -26 36 0
        8 -29 -9 0
        44 -34 46 0
        -15 36 0
        6 -37 39 0
        -33 19 0
        -20 -43 0
        -32 -4 0
        -21 2 0
        -39 40 0
        -34 36 0
        45 -8 -2 0
        -32 5 0
        -34 35 0
        -16 18 0
        -44 34 0
        -44 -46 0
        -45 2 0
        -18 20 0
        31 -3 -9 0
        40 -2 -9 0
        -6 37 0
        -29 -42 0
        26 -36 -2 0
        -14 26 27 0
        24 -44 25 0
        14 -27 0
        13 -14 0
        -30 32 0
        10 -2 -9 0
        -23 9 0
        -45 8 0
        -35 16 0
        -21 3 0
        20 33 43 0
        -1 3 0
        -12 24 0
        -4 31 0
        -6 -39 0
        27 -36 -3 0
        -30 12 0
        45 0
        33 -43 -19 0
        -31 3 0
        11 -21 -9 0
        -8 29 0
        -40 2 0
        -33 43 0""".trimIndent()

        val cnf = createFromString(s).getCNF()

        println(cnf.toDimacs())

        val result = solve(cnf.toDpllCNF())

        assert(result.decision == SAT)
        println(result.model!!.toPrettyString())

    }

    @Test
    fun getFailingCoreTest(){
        val s = """p cnf 46 106
        -29 30 0
        -34 35 0
        -12 24 0
        -8 29 0
        -35 16 0
        40 -2 -9 0
        -30 12 0
        -32 5 0
        -4 31 0
        31 -3 -9 0
        20 33 43 0
        -33 43 0
        -34 36 0
        -15 36 0
        -32 -4 0
        24 -44 25 0
        14 -27 0
        -21 2 0
        -45 8 0
        -16 18 0
        -20 -43 0
        -24 44 0
        28 -1 -9 0
        33 -43 -19 0
        45 -8 -2 0
        26 -36 -2 0
        -23 9 0
        -21 3 0
        -31 3 0
        44 -34 46 0
        13 -14 0
        -6 -39 0
        -33 19 0
        -44 -46 0
        27 -36 -3 0
        -1 3 0
        -29 -42 0
        -40 2 0
        -45 2 0
        10 -2 -9 0
        -30 32 0
        41 -2 -3 0
        11 -21 -9 0
        -6 37 0
        -39 40 0
        6 -37 39 0
        -18 20 0
        -44 34 0
        -14 26 27 0
        8 -29 -9 0
        -26 36 0
        4 -31 -2 0
        45 0""".trimIndent()

        val cnf = createFromString(s).getCNF()

        val unsatCore = getUnsatCore(cnf)
        println(unsatCore.toDimacs())
    }


    @Test
    fun `simple mine sweep model test`(){
        val formula = "((a_0_1)|(a_1_0)|(a_1_1)) & (!((a_0_1)&(a_1_0))&!((a_0_1)&(a_1_1))&!((a_1_0)&(a_1_1)))&a_0_1 "
        val context = TseytinTransformationPerformer.transform(formula)

        println("Tseytin Transformation: ")
        println(context.clauses)

        val (cnf, map) = context.toCNF()

        println("Mappings:")
        println(map.entries.joinToString { "${it.key} : ${it.value}" })
        println("DIMACS:")
        println(cnf.toDimacs())

        val result = solve(cnf.toDpllCNF())

        assert(result.decision == SAT)
        println(result.model!!.toPrettyString(map.entries.map{Pair(it.value, it.key)}.toMap()))

    }
}