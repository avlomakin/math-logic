package com.github.avlomakin.tseytin

import org.junit.Test


class TseytinTransformationPerformerTest {

    @Test
    fun test(){
        val formula  = "!((p&q)|!r)"
        val context = TseytinTransformationPerformer.transform(formula)
        println(context.clauses)
        println(context.literal)

    }
}