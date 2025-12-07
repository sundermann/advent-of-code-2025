fun main() {

    fun part1(input: List<String>): Long {
        val start = input.first().indexOf('S')
        val (_, splits) = input.drop(1).fold(Pair(setOf(start), 0L)) { (open, acc), line ->
            val splitsThisRow = open.count { pos -> line[pos] == '^' }
            val newOpen = open.flatMap { pos ->
                if (line[pos] == '^') listOf(pos - 1, pos + 1) else listOf(pos)
            }.toSet()
            Pair(newOpen, acc + splitsThisRow)
        }
        return splits
    }

    fun part2(input: List<String>): Long {
        val start = input.first().indexOf('S')
        val counts = input.drop(1).fold(mapOf(start to 1L)) { counts, line ->
            val next = mutableMapOf<Int, Long>()
            counts.forEach { (x, c) ->
                if (line.getOrNull(x) == '^') {
                    next[x - 1] = next.getOrDefault(x - 1, 0L) + c
                    next[x + 1] = next.getOrDefault(x + 1, 0L) + c
                } else {
                    next[x] = next.getOrDefault(x, 0L) + c
                }
            }
            next
        }
        return counts.values.sum()
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 21L)
    check(part2(testInput) == 40L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
