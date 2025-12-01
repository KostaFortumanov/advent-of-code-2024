package day09

import println
import readFileLines

fun main() {
    val input = readFileLines(9).first().map { it.digitToInt() }

    part1(input).println()
    part2(input).println()
}

private fun part1(input: List<Int>): Long {
    val expanded = expandMemory(input)
    var start = 0
    var end = expanded.lastIndex
    return calculateChecksum(expanded.toMutableList().apply {
        while (start <= end) {
            if (get(start) == -1L && get(end) != -1L) {
                val startValue = get(start)
                val endValue = get(end)
                set(start, endValue)
                set(end, startValue)
            }

            if (get(start) != -1L) {
                start++
            }

            if (get(end) == -1L) {
                end--
            }
        }
    })
}

private fun part2(input: List<Int>): Long {
    val expanded = expandMemory(input)
    val lastFile = expanded.last { it != -1L }
    return calculateChecksum(expanded.toMutableList().apply {
        lastFile.downTo(0).forEach { currentFile ->
            val firstIndex = indexOf(currentFile)
            val lastIndex = lastIndexOf(currentFile)
            val neededSize = lastIndex - firstIndex + 1
            val freeSpaceIndex = continuousEmptyBlocks(this).firstOrNull { (_, size) -> size >= neededSize }?.first
            if (freeSpaceIndex != null && freeSpaceIndex < firstIndex) {
                (0..<neededSize).forEach {
                    set(freeSpaceIndex + it, currentFile)
                    set(firstIndex + it, -1)
                }
            }
        }
    })
}

private fun calculateChecksum(blocks: List<Long>): Long = blocks.mapIndexed { index, value ->
    if (value == -1L) {
        0
    } else {
        index * value
    }
}.sum()


private fun expandMemory(input: List<Int>): List<Long> = input.flatMapIndexed { index, value ->
    if (index % 2 == 0) {
        List(value) { index / 2L }
    } else {
        List(value) { -1L }
    }
}

private fun continuousEmptyBlocks(list: List<Long>): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    var i = 0

    while (i < list.size) {
        if (list[i] == -1L) {
            val start = i
            while (i < list.size && list[i] == -1L) i++
            result += start to (i - start)
        } else {
            i++
        }
    }

    return result
}
