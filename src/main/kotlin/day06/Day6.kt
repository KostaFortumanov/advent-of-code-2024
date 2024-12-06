package day06

import println
import readFileCharMatrix

fun main() {
    part1().println()
    part2().println()
}

private fun part1() = lastState(State()).visited.size

private fun part2(): Int {
    val visited = lastState(State()).visited
    return visited.drop(1).count {
        lastState(State(obstructions = (State.gridObstructions + it).toHashSet())).isLoop
    }
}

private fun lastState(state: State) = generateSequence(state) { it.next() }.last()

private data class State(
    val guardPosition: Position = startPosition,
    val obstructions: HashSet<Pair<Int, Int>> = gridObstructions,
    val isLoop: Boolean = false,
    private val visitedPositions: HashSet<Position> = hashSetOf(startPosition),
) {
    val visited: Set<Pair<Int, Int>>
        get() = visitedPositions.map { it.coordinates }.toSet()

    fun next(): State? {
        val nextPosition = if (guardPosition.next().coordinates in obstructions) {
            guardPosition.turnRight()
        } else {
            guardPosition.next()
        }

        return if (isLoop) {
            null
        } else if (nextPosition in visitedPositions) {
            copy(isLoop = true)
        } else if (nextPosition.x in grid.indices && nextPosition.y in grid[0].indices) {
            copy(guardPosition = nextPosition, visitedPositions = visitedPositions.also { it.add(nextPosition) })
        } else {
            null
        }
    }

    companion object {
        val grid = readFileCharMatrix(6)
        val startPosition = grid.flatMapIndexed { rowIndex, row ->
            row.mapIndexedNotNull { colIndex, it ->
                if (grid[rowIndex][colIndex] == '^') {
                    Position(rowIndex, colIndex, Direction.NORTH)
                } else {
                    null
                }
            }
        }.first()
        val gridObstructions = grid.flatMapIndexed { rowIndex, row ->
            row.mapIndexedNotNull { colIndex, it ->
                if (grid[rowIndex][colIndex] == '#') {
                    rowIndex to colIndex
                } else {
                    null
                }
            }
        }.toHashSet()
    }
}

private data class Position(val x: Int, val y: Int, val direction: Direction) {
    val coordinates: Pair<Int, Int>
        get() = Pair(x, y)

    fun next() = copy(x = x + direction.dx, y = y + direction.dy)

    fun turnRight() = when (direction) {
        Direction.NORTH -> copy(direction = Direction.EAST)
        Direction.EAST -> copy(direction = Direction.SOUTH)
        Direction.SOUTH -> copy(direction = Direction.WEST)
        Direction.WEST -> copy(direction = Direction.NORTH)
    }
}

private enum class Direction(val dx: Int, val dy: Int) {
    NORTH(-1, 0),
    SOUTH(1, 0),
    EAST(0, 1),
    WEST(0, -1),
}
