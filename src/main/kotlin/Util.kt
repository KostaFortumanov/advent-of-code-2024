import java.io.File

fun readFileLines(day: Int): List<String> = File({}.javaClass.getResource("input_$day.txt")!!.path).readLines()

fun String.integers() = Regex("-?\\d+").findAll(this).map { it.value.toInt() }.toList()

fun <T> List<List<T>>.transpose(): List<List<T>> = List(first().size) { i -> List(size) { j -> this[j][i] } }

fun Any?.println() = println(this)
