
fun main() {

    fun List<String>.countPaths(start: String, target: String): Long {
        val graph = associate { line ->
            val (device, rest) = line.split(": ", limit = 2)
            device to rest.split(' ').toSet()
        }

        val memo = mutableMapOf<String, Long>()

        fun dfs(node: String): Long {
            if (node == target) return 1L
            return memo.getOrPut(node) {
                graph[node].orEmpty().sumOf { dfs(it) }
            }
        }

        return dfs(start)
    }

    fun part1(input: List<String>): Long {
        return input.countPaths("you", "out")
    }

    fun part2(input: List<String>): Long {
        return (input.countPaths("svr", "dac") * input.countPaths("dac", "fft") * input.countPaths("fft", "out")) +
                (input.countPaths("svr", "fft") * input.countPaths("fft", "dac") * input.countPaths("dac", "out"))
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 5L)
    val testInput2 = readInput("Day11_2_test")
    check(part2(testInput2) == 2L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
