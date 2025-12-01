package day08

import println
import readFileCharMatrix

fun main() {
    val input = readFileCharMatrix(8)

    part1(input).println()
    part2(input).println()
}

private fun part1(input: List<List<Char>>): Int = findAntinodePositions(input)

private fun part2(input: List<List<Char>>): Int = findAntinodePositions(input, 0..input.size)

private fun findAntinodePositions(input: List<List<Char>>, multiplierRange: IntRange = 1..1): Int {
    val antennas = input.flatMapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, char ->
            char to (rowIndex to colIndex)
        }.filter { it.first != '.' }
    }.groupBy({ it.first }, { it.second })

    return antennas.entries.flatMap { (_, positions) ->
        positions.flatMapIndexed { index, antennaOne ->
            positions.drop(index + 1).map { antennaTwo -> antennaOne to antennaTwo }
        }.flatMap { (antennaOne, antennaTwo) -> antinodePositions(antennaOne, antennaTwo, multiplierRange) }
    }.filter { (x, y) -> x in input.indices && y in input[x].indices }.distinct().size
}

private fun antinodePositions(
    antennaOne: Pair<Int, Int>,
    antennaTwo: Pair<Int, Int>,
    multiplierRange: IntRange,
): List<Pair<Int, Int>> {
    val dx = antennaOne.first - antennaTwo.first
    val dy = antennaOne.second - antennaTwo.second
    return multiplierRange.flatMap { multiplier ->
        listOf(
            antennaOne.first + (dx * multiplier) to antennaOne.second + (dy * multiplier),
            antennaTwo.first - (dx * multiplier) to antennaTwo.second - (dy * multiplier),
        )
    }
}
