package day10

import println
import readFileIntMatrix

fun main() {
    val input = readFileIntMatrix(10)
    val trailHeads = input.flatMapIndexed { rowIndex, row ->
        row.mapIndexedNotNull { colIndex, value ->
            (rowIndex to colIndex).takeIf { value == 0 }
        }
    }

    part1(input, trailHeads).println()
    part2(input, trailHeads).println()
}

private fun part1(input: List<List<Int>>, trailHeads: List<Pair<Int, Int>>): Int =
    trailHeads.sumOf { countReachableSummits(input, it, mutableSetOf()) }


private fun part2(input: List<List<Int>>, trailHeads: List<Pair<Int, Int>>): Int =
    trailHeads.sumOf { countReachableSummits(input, it, mutableListOf()) }

private fun countReachableSummits(
    input: List<List<Int>>,
    start: Pair<Int, Int>,
    visited: MutableCollection<Pair<Int, Int>>
): Int {
    val directions = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)
    val queue = mutableListOf(start)
    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        val next = directions
            .map { (dx, dy) -> current.first + dx to current.second + dy }
            .filter { (row, col) -> row in input.indices && col in input[row].indices && input[row][col] - input[current.first][current.second] == 1 }

        visited.addAll(next)
        queue.addAll(next)
    }

    return visited.count { (row, col) -> input[row][col] == 9 }
}
