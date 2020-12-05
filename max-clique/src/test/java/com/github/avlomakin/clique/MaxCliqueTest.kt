package com.github.avlomakin.clique

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class MaxCliqueTest(val params: Pair<Int, Int>) {

    companion object {
        @Parameters
        @JvmStatic
        fun parameters() : List<Pair<Int, Int>>{
            return listOf(
                1 to 2,
                2 to 5,
            )
        }

    }

    @Test
    fun default(){
        val maxClique = maxClique(params.first)
        assert(maxClique == params.second)
    }
}