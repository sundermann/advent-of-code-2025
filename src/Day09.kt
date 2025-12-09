import kotlin.math.absoluteValue

fun main() {

    data class Coordinate(val x: Long, val y: Long) {
        fun areaTo(other: Coordinate): Long {
            return (1 + (x - other.x).absoluteValue) * (1 + (y - other.y).absoluteValue)
        }
    }

    data class Line(val start: Coordinate, val end: Coordinate)

    fun Line.intersects(rectA: Coordinate, rectB: Coordinate): Boolean {
        val rectMinX = minOf(rectA.x, rectB.x) + 1
        val rectMaxX = maxOf(rectA.x, rectB.x) - 1
        val rectMinY = minOf(rectA.y, rectB.y) + 1
        val rectMaxY = maxOf(rectA.y, rectB.y) - 1

        if (rectMinX > rectMaxX || rectMinY > rectMaxY)
            return false

        val dx = end.x - start.x
        val dy = end.y - start.y

        val checks = listOf(
            -dx to start.x - rectMinX, // left
            dx to rectMaxX - start.x, // right
            -dy to start.y - rectMinY, // bottom
            dy to rectMaxY - start.y // top
        )

        var start = 0.0
        var end = 1.0
        for ((p, q) in checks) {
            when {
                p == 0L -> if (q < 0L) return false
                p < 0L -> start = maxOf(start, q / p.toDouble())
                else -> end = minOf(end, q / p.toDouble())
            }
            if (start > end) return false
        }

        return true
    }

    fun String.toCoordinate(): Coordinate {
        val (x, y) = split(",").map { it.toLong() }
        return Coordinate(x, y)
    }

    fun part1(input: List<String>): Long {
        val redTiles = input.map { it.toCoordinate() }
        return redTiles.asSequence().flatMapIndexed { i, a ->
            redTiles.drop(i + 1).map { b -> a to b }
        }.maxOf { (a, b) -> a.areaTo(b) }
    }

    fun part2(input: List<String>): Long {
        val redTiles = input.map { it.toCoordinate() }
        val lines = (redTiles + redTiles.first())
            .windowed(2)
            .map { (a, b) -> Line(a, b) }

        return redTiles.asSequence()
            .flatMap { a -> redTiles.asSequence().filter { it != a }.map { b -> a to b } }
            .filter { (a, b) -> lines.none { it.intersects(a, b) } }
            .maxOf { (a, b) -> a.areaTo(b) }
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 50L)
    check(part2(testInput) == 24L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
