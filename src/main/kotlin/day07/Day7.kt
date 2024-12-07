package day07

import longs
import pmap
import println
import readFileLines

fun main() {
    val equations = readFileLines(7).map {
        val integers = it.longs()
        integers.first() to integers.drop(1)
    }

    part1(equations).println()
    part2(equations).println()
}

private fun part1(equations: List<Pair<Long, List<Long>>>): Long = sumValidEquations(equations, listOf("+", "*"))

private fun part2(equations: List<Pair<Long, List<Long>>>): Long = sumValidEquations(equations, listOf("+", "*", "|"))

private fun sumValidEquations(equations: List<Pair<Long, List<Long>>>, operators: List<String>): Long {
    val allPossibleOperations = equations.map { (_, equation) -> equation.size - 1 }.distinct()
        .associate { it to possibleOperations(operators, it).map { it.split("") } }
    return equations.pmap { (result, equation) ->
        val valid = allPossibleOperations[equation.size - 1]?.firstOrNull {
            equation.reduceIndexed { index, acc, number ->
                when (it[index]) {
                    "+" -> acc + number
                    "*" -> acc * number
                    else -> "$acc$number".toLong()
                }
            } == result
        } != null

        if (valid) {
            result
        } else {
            0L
        }
    }.sum()
}

private fun possibleOperations(characters: List<String>, length: Int, s: String = ""): List<String> =
    if (length == s.length) {
        listOf(s)
    } else {
        characters.flatMap { possibleOperations(characters, length, "$s$it") }
    }
