fun main() {

    fun String.toLongRange(): LongRange {
        val (start, end) = split("-").map { it.toLong() }
        return start..end
    }

    fun part1(input: List<String>): Int {
        val ranges = input.takeWhile { it.isNotEmpty() }.map { it.toLongRange() }
        val ingredients = input.dropWhile { it.isNotEmpty() }.drop(1).map { it.toLong() }
        return ingredients.count { num -> ranges.any { num in it } }
    }

    fun part2(input: List<String>): Long {
        val ranges = input.takeWhile { it.isNotEmpty() }.map { it.toLongRange() }

        val merged = ranges.drop(1).sortedBy { it.first }.fold(mutableListOf(ranges.first())) { acc, r ->
            val last = acc.last()
            if (r.first <= last.last + 1)
                acc[acc.lastIndex] = last.first..maxOf(last.last, r.last)
            else
                acc.add(r)
            acc
        }

        return merged.sumOf { it.last - it.first + 1 }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
