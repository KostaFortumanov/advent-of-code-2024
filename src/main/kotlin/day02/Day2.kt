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
    }).any { isSafe(it) }
}

private fun isSafe(report: List<Int>): Boolean {
    val windowedReport = report.windowed(2)
    val descending = windowedReport.all { (a, b) -> a > b }
    val ascending = windowedReport.all { (a, b) -> a < b }
    val correctDifference = windowedReport.all { (a, b) -> abs(a - b) in 1..3 }
    return (ascending || descending) && correctDifference
}
