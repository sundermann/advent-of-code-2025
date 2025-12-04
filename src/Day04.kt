fun main() {

    fun List<String>.removablePositions(): List<Pair<Int, Int>> {
        val deltas = listOf(
            0 to 1,
            0 to -1,
            1 to 0,
            1 to -1,
            1 to 1,
            -1 to 0,
            -1 to -1,
            -1 to 1,
        )

        return indices.flatMap { row ->
            this[row].indices.mapNotNull { col ->
                if (this[row][col] != '@') return@mapNotNull null

                val adjacentAtSigns = deltas.count { (dr, dc) ->
                    val r = row + dr
                    val c = col + dc
                    this.getOrNull(r)?.getOrNull(c) == '@'
                }

                if (adjacentAtSigns < 4) row to col else null
            }
        }
    }

    fun part1(input: List<String>): Int {
        return input.removablePositions().size
    }

    fun part2(input: List<String>): Int {
        val grid = input.toMutableList()

        return generateSequence {
            val positions = grid.removablePositions().takeIf { it.isNotEmpty() } ?: return@generateSequence null
            positions.forEach { (row, col) ->
                grid[row] = grid[row].replaceRange(col, col + 1, ".")
            }
            positions.size
        }.sum()
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
