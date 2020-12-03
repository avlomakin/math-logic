package com.github.avlomakin.clique

import com.github.avlomakin.dimacs.toDimacs
import com.github.avlomakin.dpll.DpllUtil.toDpllCNF
import com.github.avlomakin.dpll.SatDecision.SAT
import com.github.avlomakin.dpll.solve
import com.github.avlomakin.model.CNF
import com.github.avlomakin.model.StringClause
import com.github.avlomakin.model.StringLiteral
import com.github.avlomakin.util.CnfUtils.toCNF

fun maxClique(k: Int): Int {
    var i = 2
    var result = SAT
    while (result == SAT) {
        i += 1
        val cnf = generateCNF(i, k).first
        result = solve(cnf.toDpllCNF()).decision
        println("result for k = $k, n = $i: $result")
    }
    return i - 1
}

/**
 * @param n - vertex count
 * @param k - colors count
 * legend - e_i_j_m - edge i_j is in color m
 */
fun generateCNF(n: Int, k: Int): Pair<CNF, Map<String, Int>> {
    val formula = mutableSetOf<StringClause>()

    val eachEdgeHasColor = mutableSetOf<StringClause>()
    for (i1 in 1..n) {
        for (i2 in (i1 + 1)..n) {
            eachEdgeHasColor.add(StringClause((1..k).map { j -> StringLiteral("v_${i1}_${i2}_$j", false) }.toSet()))
        }
    }
    formula.addAll(eachEdgeHasColor)

    val eachEdgeHasOnlyOneColorList = mutableSetOf<StringClause>()
    for (i1 in 1..n) {
        for (i2 in (i1 + 1)..n) {
            for (j1 in 1..k) {
                for (j2 in (j1 + 1)..k) {
                    eachEdgeHasColor.add(
                        StringClause(
                            setOf(
                                StringLiteral("v_${i1}_${i2}_${j1}", true),
                                StringLiteral("v_${i1}_${i2}_${j2}", true),
                            )
                        )
                    )
                }
            }
        }
    }

    formula.addAll(eachEdgeHasOnlyOneColorList)

    val noThreeVerticesHasSameColor = mutableSetOf<StringClause>()
    for (j in 1..k) {
        for (i1 in 1..n) {
            for (i2 in (i1 + 1)..n) {
                for (i3 in (i2 + 1)..n) {
                    noThreeVerticesHasSameColor.add(
                        StringClause(
                            setOf(
                                StringLiteral("v_${i1}_${i2}_${j}", true),
                                StringLiteral("v_${i2}_${i3}_${j}", true),
                                StringLiteral("v_${i1}_${i3}_${j}", true),
                            )
                        )
                    )
                }
            }
        }
    }
    formula.addAll(noThreeVerticesHasSameColor)
    return toCNF(formula)
}