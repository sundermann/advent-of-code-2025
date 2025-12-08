import java.util.PriorityQueue

fun main() {

    data class Coordinate(val x: Long, val y: Long, val z: Long) {
        fun distanceTo(other: Coordinate): Long {
            return (x - other.x) * (x - other.x) +
                   (y - other.y) * (y - other.y) +
                   (z - other.z) * (z - other.z)
        }
    }

    fun String.toCoordinate(): Coordinate {
        val (x, y, z) = split(",").map { it.toLong() }
        return Coordinate(x, y, z)
    }

    fun part1(input: List<String>, steps: Int, part2: Boolean = false): Long {
        val distances = PriorityQueue<Pair<Coordinate, Coordinate>>(
            compareBy { it.first.distanceTo(it.second) })
        val coordinates = input.map { it.toCoordinate() }
        distances.addAll(coordinates.withIndex().flatMap { (i, a) ->
            coordinates.drop(i + 1).map { b -> a to b }
        })

        val circuits = mutableListOf<MutableSet<Coordinate>>()
        coordinates.forEach { circuits += mutableSetOf(it) }

        repeat(steps) {
            val (a, b) = distances.poll()
            val da = circuits.find { it.contains(a) }
            val db = circuits.find { it.contains(b) }

            if (da != db) {
                da!!.addAll(db!!)
                circuits.remove(db)
            }

            if (part2 && circuits.size == 1)
                return a.x * b.x
        }

        return circuits.sortedByDescending { it.size }
            .take(3)
            .map { it.size.toLong() }
            .reduce(Long::times)
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput, 10) == 40L)
    check(part1(testInput, Int.MAX_VALUE, true) == 25272L)

    val input = readInput("Day08")
    part1(input, 1000).println()
    part1(input, Int.MAX_VALUE, true).println()
}
