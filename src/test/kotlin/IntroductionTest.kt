import fp.*
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class IntroductionTest {
    @Test
    fun testFactorial() {
        assertEquals(6, factorial(3))
    }

    @Test
    fun testFib() {
        assertContentEquals(
            arrayOf(0, 1, 1, 2, 3, 5, 8, 13, 21),
            arrayOf(fib(0), fib(1), fib(2), fib(3), fib(4), fib(5), fib(6), fib(7), fib(8))
        )

        assertEquals(5, fib(5))
    }

    @Test
    fun testUtils() {
        assertEquals(
            "The absolute value of -42 of 42",
            Utils.formatAbs(-42)
        )
        assertEquals(
            "The factorial of 7 is 5040",
            Utils.formatFactorial(7)
        )

        assertEquals(
            "The absolute of -42 is 42",
            Utils.formatResult("absolute", -42, ::abs)
        )

        assertEquals(
            "The factorial of 7 is 5040",
            Utils.formatResult("factorial", 7, ::factorial)
        )

        assertEquals(
            "The absolute of -42 is 42",
            Utils.formatResult("absolute", -42
            ) { if (it < 0) -it else it }
        )

        val ss: Array<String> = arrayOf("1", "2", "3")
        assertEquals(
            1,
            findFirst(ss, "2")
        )

        val xs: Array<Int> = arrayOf(1, 2, 3, 4, 5)
        assertEquals(
            2,
            findFirst(xs) { it == 3 }
        )

        val aa1: List<Int> = listOf(1, 2, 3)
        assertEquals(
            true,
            isSorted(aa1) { a, b -> a < b }
        )

        val aa2: List<Int> = listOf(1, 3, 2)
        assertEquals(
            false,
            isSorted(aa2) { a, b -> a < b }
        )
    }

    @Test
    fun testHOC() {
        val partial = partial1("Spike") { a, b: Int -> "My name is $a, my age is $b"}
        val curry = curry { a: String, b: Int -> "My name is $a, my age is $b"}
        val uncurry = uncurry(curry)
        val compose = compose({ b: Int -> "My age is $b"}) { a: Int -> a * 10 }

        val res = "My name is Spike, my age is 27"
        assertEquals(res, partial(27))
        assertEquals(res, curry("Spike")(27))
        assertEquals(res, uncurry("Spike", 27))
        assertEquals("My age is 270", compose(27))
    }
}
