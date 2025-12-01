fun main() {
    fun part1(input: List<String>): Int {
        return input.runningFold(50) { acc, s ->
            val op = s.first()
            val amount = s.drop(1).toInt()
            val next = if (op == 'L') acc - amount else acc + amount
            next.mod(100)
        }.count { it == 0 }
    }

    fun part2(input: List<String>): Int {
        var result = 0
        input.runningFold(50) { acc, s ->
            val op = s.first()
            val amount = s.drop(1).toInt()
            val shift = amount % 100
            val next = if (op == 'L') acc - shift else acc + shift
            if (acc != 0 && next !in (1..99))
                result++
            result += amount / 100
            next.mod(100)
        }.count { it == 0 }
        return result
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    println(part2(testInput))
    check(part2(testInput) == 6)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
