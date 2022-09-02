import fp.factorial
import fp.fib
import org.junit.jupiter.api.Test
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
    }
}