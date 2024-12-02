package day02

import integers
import println
import readFileLines
import kotlin.math.abs

fun main() {
    val reports = readFileLines(2).map { it.integers() }

    part1(reports).println()
    part2(reports).println()
}

private fun part1(reports: List<List<Int>>): Int = reports.count { isSafe(it) }

private fun part2(reports: List<List<Int>>): Int = reports.count { report ->
    (listOf(report) + List(report.size) {
        report.subList(0, it) + report.subList(it + 1, report.size)
    }).fold(false) { acc, it -> acc || isSafe(it) }
}

private fun isSafe(report: List<Int>): Boolean {
    val windowed = report.windowed(2)
    val descending = windowed.map { (a, b) -> a > b }.all { it }
    val ascending = windowed.map { (a, b) -> a < b }.all { it }
    val validDifference = 1..3
    val correctDifference = windowed.map { (a, b) -> abs(a - b) in validDifference }.all { it }
    return (ascending || descending) && correctDifference
}
