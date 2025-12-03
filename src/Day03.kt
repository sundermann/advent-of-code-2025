fun main() {

    fun String.joltage(): Long {
        return dropLast(1)
            .mapIndexed { i, num -> drop(i + 1).maxOf { "$num$it" } }
            .maxOf { it.toLong() }
    }

    fun String.joltage2(batteryCount: Int = 12): Long {
        val digits = map { it.digitToInt() }
        var maxJoltage = 0L
        var start = 0
        repeat (batteryCount) { i ->
            val end = digits.size - batteryCount + i
            val maxIndex = (start .. end).maxBy { digits[it] }

            maxJoltage = maxJoltage * 10 + digits[maxIndex]
            start = maxIndex + 1
        }

        return maxJoltage
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { it.joltage() }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { it.joltage2() }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357L)
    check(part2(testInput) == 3121910778619L)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
