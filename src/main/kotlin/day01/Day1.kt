package day01

import integers
import println
import readFileLines
import transpose
import kotlin.math.abs

fun main() {
    val (left, right) = readFileLines(1)
        .map { it.integers() }
        .transpose()

    part1(left, right).println()
    part2(left, right).println()
}

private fun part1(left: List<Int>, right: List<Int>): Int =
    left.sorted().zip(right.sorted()).sumOf { (a, b) -> abs(a - b) }

private fun part2(left: List<Int>, right: List<Int>): Int {
    val eachCount = right.groupingBy { it }.eachCount()
    return left.sumOf { it * (eachCount[it] ?: 0) }
}
