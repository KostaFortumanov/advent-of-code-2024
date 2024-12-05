package day05

import integers
import println
import readFileLines
import split

fun main() {
    val (rules, updates) = readFileLines(5).split { it == "" }
    val (validUpdates, invalidUpdates) = partitionUpdates(rules.toSet(), updates)

    part1(validUpdates).println()
    part2(rules.toSet(), invalidUpdates).println()
}

private fun part1(validUpdates: List<List<Int>>): Int = validUpdates.sumOf { it[it.size / 2] }

private fun part2(rules: Set<String>, invalidUpdates: List<List<Int>>): Int =
    invalidUpdates.map {
        it.sortedWith { a, b ->
            val validRule = rules.contains("$a|$b")
            val invalidRule = rules.contains("$b|$a")

            when {
                validRule -> 1
                invalidRule -> -1
                else -> 0
            }
        }
    }.sumOf { it[it.size / 2] }

private fun partitionUpdates(rules: Set<String>, updates: List<String>): Pair<List<List<Int>>, List<List<Int>>> =
    updates
        .map { it.integers() }
        .partition { list ->
            val combinations = list.flatMapIndexed { index, value ->
                list.subList(index + 1, list.size).map { value to it }
            }
            combinations.all { (a, b) ->
                when {
                    rules.contains("$a|$b") -> true
                    rules.contains("$b|$a") -> false
                    else -> true
                }
            }
        }
