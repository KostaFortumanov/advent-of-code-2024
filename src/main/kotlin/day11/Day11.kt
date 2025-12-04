package day11

import longs
import println
import readFileLines

fun main() {
    val input = readFileLines(11).first().longs()

    part1(input).println()
    part2(input).println()
}

val cache = mutableMapOf<Pair<Long, Int>, Long>()

private fun part1(input: List<Long>): Long = input.sumOf { 1 + lineLength(it, 25) }

private fun part2(input: List<Long>): Long = input.sumOf { 1 + lineLength(it, 75) }

private fun lineLength(stone: Long, stepsLeft: Int): Long =
    if (stepsLeft == 0) {
        0
    } else {
        cache.getOrPut(stone to stepsLeft) {
            val stoneString = stone.toString()
            when {
                stone == 0L -> lineLength(1, stepsLeft - 1)

                stoneString.length % 2 == 0 -> {
                    val left = stoneString.take(stoneString.length / 2).toLong()
                    val right = stoneString.takeLast(stoneString.length / 2).toLong()
                    1 + lineLength(left, stepsLeft - 1) + lineLength(right, stepsLeft - 1)
                }

                else -> lineLength(stone * 2024, stepsLeft - 1)
            }
        }
    }
