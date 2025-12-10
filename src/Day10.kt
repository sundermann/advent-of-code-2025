import com.microsoft.z3.*

fun main() {

    data class Procedure(
        val lights: List<Boolean>,
        val buttons: List<List<Int>>,
        val joltage: List<Int>
    ) {
        fun part1(): Int {
            val start = List(lights.size) { false }
            val visited = mutableSetOf<List<Boolean>>()
            val queue = ArrayDeque<Pair<List<Boolean>, Int>>().apply { add(start to 0) }

            while (queue.isNotEmpty()) {
                val (indicators, presses) = queue.removeFirst()
                if (indicators == lights) return presses
                if (!visited.add(indicators)) continue
                buttons.forEach { button ->
                    val next = indicators.mapIndexed { i, light ->
                        if (i in button) !light else light
                    }
                    if (next !in visited) queue.add(next to presses + 1)
                }
            }

            return 0
        }

        fun part2(): Int = with(Context()) {
            operator fun <R : ArithSort> ArithExpr<R>.plus(other: ArithExpr<R>) = mkAdd(this, other) as ArithExpr<R>
            fun Int.intExpr() = mkInt(this)

            val opt = mkOptimize()
            val buttonPresses = buttons.indices.map { mkIntConst("x$it") }

            buttonPresses.forEach { opt.Add(mkGe(it, 0.intExpr())) }

            val totalPresses: ArithExpr<IntSort> =
                if (buttonPresses.isEmpty()) 0.intExpr()
                else mkAdd(*buttonPresses.toTypedArray()) as ArithExpr<IntSort>

            val joltages = MutableList<ArithExpr<IntSort>>(joltage.size) { 0.intExpr() }

            opt.MkMinimize(totalPresses)

            buttons.forEachIndexed { idx, button ->
                val presses = buttonPresses[idx]
                button.forEach { i -> joltages[i] = joltages[i] + presses }
            }

            joltages.zip(joltage).forEach { (expr, target) -> opt.Add(mkEq(expr, target.intExpr())) }

            require(opt.Check() == Status.SATISFIABLE)
            val evaluated = opt.model.evaluate(totalPresses, false)
            require(evaluated is IntNum)
            evaluated.int
        }
    }

    fun String.toProcedure(): Procedure {
        val parts = split(' ')
        val lights = parts.first().drop(1).dropLast(1).map { it == '#' }
        val buttons = parts
            .drop(1).dropLast(1)
            .map { it.drop(1).dropLast(1).split(',').map { it.toInt() } }
        val joltage = parts.last().drop(1).dropLast(1).split(',').map { it.toInt() }
        return Procedure(lights, buttons, joltage)
    }

    fun part1(input: List<String>): Long {
        val parts = input.map { it.toProcedure() }
        return parts.sumOf { it.part1() }.toLong()
    }

    fun part2(input: List<String>): Long {
        val parts = input.map { it.toProcedure() }
        return parts.sumOf { it.part2() }.toLong()
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 7L)
    check(part2(testInput) == 33L)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
