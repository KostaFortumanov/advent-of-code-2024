package day05

import integers
import println
import readFileLines
import split

fun main() {
    val (rules, updates) = readFileLines(5).split { it == "" }

    part1(rules.toSet(), updates).println()
    part2(rules.toSet(), updates).println()
}

private fun part1(rules: Set<String>, updates: List<String>): Int {
    val (validUpdates, _) = partitionRules(rules, updates)
    return validUpdates.sumOf { it[it.size / 2] }
}

private fun part2(rules: Set<String>, updates: List<String>): Int {
    val (_, invalidUpdates) = partitionRules(rules, updates)
    return invalidUpdates.map {
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
}

private fun partitionRules(rules: Set<String>, updates: List<String>): Pair<List<List<Int>>, List<List<Int>>> =
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
