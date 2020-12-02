package com.github.avlomakin.minesweep

import com.github.avlomakin.dimacs.toDimacs
import com.github.avlomakin.dpll.DpllUtil.toDpllCNF
import com.github.avlomakin.dpll.SatDecision.UNSAT
import com.github.avlomakin.dpll.solve
import com.github.avlomakin.tseytin.TseytinTransformationPerformer.transform
import com.google.common.collect.Sets
import org.slf4j.LoggerFactory
import kotlin.math.max
import kotlin.math.min

object FieldParser {
    private val log = LoggerFactory.getLogger(this::class.java)
    private const val VP = "a" //VAR_PREFIX

    /**
     * legend: * - bomb, x - hidden, 1..9 - bombs around, . - no bomb
     *  1 * x
     *  x 2 x
     *  1 x x
     */


    /**
     * Main idea - generate the field formula and add prediction that bomb is at ([x],[y])
     * If [UNSAT] then it's safe, false otherwise
     * @param fieldFormula
     * @param x - x coordinate to check
     * @param y - x coordinate to check
     */
    fun isFieldSafe(fieldFormula: String, x: Int, y: Int): Boolean {
        val formula = fieldFormula.plus("&${VP}_${x}_${y}")
        log.debug("FORMULA: $formula")

        val context = transform(formula)

        log.debug("TSEYTIN: ${context.clauses}")

        val (cnf, map) = context.toCNF()

        log.debug(cnf.toDimacs())

        val result = solve(cnf.toDpllCNF())

        log.debug(result.model?.toPrettyString(map.entries.map { Pair(it.value, it.key) }.toMap()))

        return result.decision == UNSAT
    }

    /**
     * @param field - square field
     * @return formula describing the field
     */
    fun parseField(field: List<List<String>>): String {

        //main idea = p_i_j = bomb is in (i,j)
        val n = field.size

        val formula = StringBuilder()

        val allKnownBombs = getAllPositions(field, "*")
        val allKnownEmptyCells = getAllPositions(field, ".", "1", "2", "3", "4", "5", "6", "7", "8")

        for (i in 0 until n) {
            for (j in 0 until n) {
                val condition: String = when (val p = field[i][j]) {
                    "1", "2", "3", "4", "5", "6", "7", "8" -> generateBombsAroundCondition(
                        i,
                        j,
                        n,
                        p.toInt(),
                        allKnownBombs,
                        allKnownEmptyCells
                    )
                    "*" -> generateBombFoundCondition(i, j)
                    "." -> generateNoBombCondition(i, j)
                    "x" -> ""
                    else -> throw IllegalArgumentException("illegal symbol($i, $j): $p")
                }
                if (formula.isNotEmpty() && condition.isNotBlank()) {
                    formula.append("&")
                }
                formula.append(condition)
            }
        }
        return formula.toString()
    }

    private fun generateNoBombCondition(i: Int, j: Int): String {
        return "!${VP}_${i}_${j}"
    }

    private fun generateBombFoundCondition(i: Int, j: Int): String {
        return "${VP}_${i}_${j}"
    }

    /**
     * try all combinations
     */
    @Suppress("UnstableApiUsage")
    internal fun generateBombsAroundCondition(
        i: Int,
        j: Int,
        n: Int,
        initialBombsCount: Int,
        allKnownBombs: Set<String>,
        allKnownEmptyCells: Set<String>,
    ): String {


        val allVars = setOf(
            "${VP}_${max(i - 1, 0)}_${max(j - 1, 0)}",
            "${VP}_${max(i - 1, 0)}_$j",
            "${VP}_${max(i - 1, 0)}_${min(j + 1, n - 1)}",

            "${VP}_${i}_${max(j - 1, 0)}",
            "${VP}_${i}_${min(j + 1, n - 1)}",

            "${VP}_${min(i + 1, n - 1)}_${max(j - 1, 0)}",
            "${VP}_${min(i + 1, n - 1)}_$j",
            "${VP}_${min(i + 1, n - 1)}_${min(j + 1, n - 1)}",
        ).minus("${VP}_${i}_$j")

        val actualBombsCount = initialBombsCount - allVars.intersect(allKnownBombs).size

        val possibleVars = allVars.minus(allKnownEmptyCells)
        val knownBombsInArea = allVars.intersect(allKnownBombs)

        val allPossibleBombPositionVariations =
            Sets.combinations(possibleVars, actualBombsCount).mapNotNull { combination ->
                if (knownBombsInArea.all { bomb -> combination.contains(bomb) }) {
                    combination.joinToString(separator = "&", prefix = "(", postfix = ")")
                } else {
                    null
                }
            }.joinToString(separator = "|")

        val noTwoCombinationsAreAllowed = StringBuilder()
        val combinations = Sets.combinations(possibleVars, initialBombsCount).toList()
        for (i1 in combinations.indices) {
            for (j1 in combinations.indices) {
                if (i1 < j1) {
                    if (allKnownEmptyCells.any { combinations[i1].contains(it) }) {
                        continue
                    }
                    if (allKnownEmptyCells.any { combinations[j1].contains(it) }) {
                        continue
                    }

                    val combinationI = combinations[i1].minus(allKnownBombs)
                    val combinationsJ = combinations[j1].minus(allKnownBombs)

                    val allCombinations = (combinationI + combinationsJ).joinToString(separator = "&")

                    noTwoCombinationsAreAllowed.append("!($allCombinations)")
                    noTwoCombinationsAreAllowed.append("&")
                }
            }
        }
        val noTwoCombinationsAreAllowedFormula = noTwoCombinationsAreAllowed.removeSuffix("&").toString()


        return allPossibleBombPositionVariations.parenIfNotEmpty()
            .joinFormula("&", noTwoCombinationsAreAllowedFormula.parenIfNotEmpty())
    }


    private fun getAllPositions(field: List<List<String>>, vararg items: String): Set<String> {
        return field.flatMapIndexed { i, row ->
            row.mapIndexedNotNull { j, cell ->
                if (items.contains(cell)) {
                    "${VP}_${i}_$j"
                } else {
                    null
                }
            }
        }.toSet()
    }
}

private fun String.joinFormula(separator: String, other: String): String {
    return if (this.isNotEmpty() && other.isNotEmpty()) {
        "$this$separator$other"
    } else if (this.isNotEmpty()) {
        this
    } else {
        other
    }
}

private fun String.parenIfNotEmpty(): String {
    return if (this.isNotEmpty()) {
        "($this)"
    } else {
        this
    }

}
