import java.io.File

fun readFileLines(day: Int): List<String> = File({}.javaClass.getResource("input_$day.txt")!!.path).readLines()

private fun readFileMatrix(day: Int): List<List<String>> =
    readFileLines(day).map { line -> line.split("").filter { it.isNotEmpty() } }

fun readFileCharMatrix(day: Int): List<List<Char>> =
    readFileMatrix(day).map { line -> line.map { it.first() } }

fun String.integers() = Regex("-?\\d+").findAll(this).map { it.value.toInt() }.toList()

fun <T> List<List<T>>.transpose(): List<List<T>> = List(first().size) { i -> List(size) { j -> this[j][i] } }

fun Any?.println() = println(this)
