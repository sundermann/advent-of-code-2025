fun main() {

    fun <R> List<String>.parts(map: (List<String>) -> R): List<R> =
        this.joinToString("\n")
            .split("\n\n")
            .map { it.lines() }
            .map(map)

    fun part1(input: List<String>): Int {
        val inputParts = input.parts { it }
        val shapeCounts = inputParts
            .dropLast(1)
            .map { it.drop(1).sumOf { row -> row.count { ch -> ch == '#' } } }

        return inputParts.last().count { test ->
            val (m, n) = test.substringBefore(':').split('x').map(String::toInt)
            val sc = test.substringAfter(": ").split(' ').map(String::toInt)
            val total = sc.withIndex().sumOf { (i, c) -> shapeCounts[i] * c }
            total <= m * n
        }
    }

    val input = readInput("Day12")
    part1(input).println()
}
