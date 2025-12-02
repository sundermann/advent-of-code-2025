fun main() {

    fun String.toLongRange(): LongRange {
        val (a, b) = this.split("-").map { it.toLong() }
        return a..b
    }

    fun String.toLongRanges() = this.split(",").map { it.toLongRange() }

    fun String.repeated(): Boolean {
        val len = this.length
        for (i in 1..len / 2) {
            val repeat = take(i)
            if (drop(i).chunked(i).all { repeat == it }) return true
        }
        return false
    }

    fun part1(input: List<String>): Long {
        return input.first().toLongRanges()
            .flatMap { it.toList() }
            .map { it.toString() }
            .filter { it.take(it.length / 2) == it.drop(it.length / 2) }
            .sumOf { it.toLong() }
    }

    fun part2(input: List<String>): Long {
        return input.first().toLongRanges()
            .flatMap { it.toList() }
            .map { it.toString() }
            .filter { it.repeated() }
            .sumOf { it.toLong() }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
