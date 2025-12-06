fun main() {

    fun List<List<Long>>.solve(instr: List<Char>): Long {
        return this.zip(instr) { col, instr ->
            when (instr) {
                '*' -> col.reduce(Long::times)
                '+' -> col.reduce(Long::plus)
                else -> error("Unknown instruction: $instr")
            }
        }.sum()
    }

    fun part1(input: List<String>): Long {
        val rows = input.dropLast(1)
            .map { line-> line.split(' ')
                .filter { it.isNotBlank() }
                .map { it.toLong() }
            }

        val columns = rows.first().indices.map { col -> rows.map { it[col] } }
        val instructions = input.last().filter { it != ' ' }.toList()
        return columns.solve(instructions)
    }

    fun <T> List<T>.split(where: (T) -> Boolean): List<List<T>> =
        fold(mutableListOf(mutableListOf<T>())) { acc, element ->
            if (where(element))
                acc.add(mutableListOf())
            else
                acc.last().add(element)
            acc
        }.map { it.toList() }

    fun part2(input: List<String>): Long {
        val rows = input.dropLast(1)
        val columns = rows.first().indices.map { col -> rows.map { it[col] } }
            .map { chars -> chars.joinToString("") }
            .split(String::isBlank)
            .map { l -> l.map { it.toList().joinToString("").trim().toLong() } }

        val instructions = input.last().filter { it != ' ' }.toList()
        return columns.solve(instructions)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
