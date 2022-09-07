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
        val partial = partial("Spike") { a, b: Int -> "My name is $a, my age is $b"}
        val curry = curry { a: String, b: Int -> "My name is $a, my age is $b"}
        val uncurry = uncurry(curry)
        val compose = compose({ b: Int -> "My age is $b"}) { a: Int -> a * 10 }

        val res = "My name is Spike, my age is 27"
        assertEquals(res, partial(27))
        assertEquals(res, curry("Spike")(27))
        assertEquals(res, uncurry("Spike", 27))
        assertEquals("My age is 270", compose(27))
    }

    @Test
    fun testMyListWithCons() {
        val mlint: MyList<Int> = MyList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val mldouble = MyList.of(1.0, 2.0, 3.0, 4.0, 5.0)

        assertEquals(55, MyList.sum(mlint))
        assertEquals(120.0, MyList.product(mldouble))

        assertEquals(2, value(mlint, ::tail))
        assertEquals(100, (setHead(mlint, 100) as Cons).head)
        assertEquals(5, (drop(mlint, 4) as Cons).head)
        assertEquals(5, (dropWhile(mlint) { it == 5 } as Cons).head)
        assertEquals(1, (drop(append(MyList.of(11, 12, 13), mlint), 3) as Cons).head)
        assertEquals(Nil, (dropWhile(init(mlint)) { it == 9 } as Cons).tail)
    }

    @Test
    fun testFoldRight() {
        val mlint: MyList<Int> = MyList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val mldouble = MyList.of(1.0, 2.0, 3.0, 4.0, 5.0)

        assertEquals(55, sum(mlint))
        assertEquals(120.0, product(mldouble))
        assertEquals(10, length(mlint))
    }

    @Test
    fun testFoldLeft() {
        val mlint: MyList<Int> = MyList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val mldouble = MyList.of(1.0, 2.0, 3.0, 4.0, 5.0)

        assertEquals(55, sumLeft(mlint))
        assertEquals(120.0, productLeft(mldouble))
        assertEquals(10, lengthLeft(mlint))
        assertEquals(
            MyList.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1),
            reverse(mlint)
        )
    }

    @Test
    fun testFoldRightOnLeft() {
        val mlint: MyList<Int> = MyList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val mldouble = MyList.of(1.0, 2.0, 3.0, 4.0, 5.0)
        // Wrong!

        assertEquals(55, sumLeftOL(mlint))
        assertEquals(120.0, productLeftOL(mldouble))
        assertEquals(10, lengthLeftOL(mlint))
        assertEquals(
            MyList.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1),
            reverseOL(mlint)
        )
    }

    @Test
    fun testFoldLeftOnRight() {
        val mlint: MyList<Int> = MyList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val mldouble = MyList.of(1.0, 2.0, 3.0, 4.0, 5.0)
        // Wrong!

        assertEquals(55, sumLeftOR(mlint))
        assertEquals(120.0, productLeftOR(mldouble))
        assertEquals(10, lengthLeftOR(mlint))
//        assertEquals(
//            MyList.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1),
//            reverseOR(mlint)
//        )
    }

    @Test
    fun testAppend() {
        val a1: MyList<Int> = MyList.of(1, 2, 3, 4, 5)
        val a2: MyList<Int> = MyList.of(6, 7, 8, 9, 10)
        val res: MyList<Int> = MyList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        assertEquals(res, appendR(a1, a2))
        assertEquals(res, appendL(a1, a2))
    }

    @Test
    fun testConcat() {
        val xs: MyList<MyList<Int>> = MyList.of(
            MyList.of(1),
            MyList.of(2),
            MyList.of(3),
            MyList.of(4),
            MyList.of(5),
        )
        val res: MyList<Int> = MyList.of(1, 2, 3, 4, 5)

        assertEquals(res, concat(xs))
    }

    @Test
    fun testListUtils() {
        val original: MyList<Int> = MyList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        assertEquals(
            MyList.of(2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
            increase1(original)
        )

        assertEquals(
            MyList.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
            intToString(original)
        )

        assertEquals(
            MyList.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
            map(original) { a -> a.toString() }
        )

        assertEquals(
            MyList.of(1, 3, 5, 7, 9),
            filter(original) { a -> a % 2 != 0 }
        )

        assertEquals(
            MyList.of(1, 1, 2, 2, 3, 3),
            flatMap(
                MyList.of(1, 2, 3),
            ) { i -> MyList.of(i, i) }
        )

        assertEquals(
            MyList.of(1, 3, 5, 7, 9),
            filterFM(original) { a -> a % 2 != 0 }
        )

        assertEquals(
            MyList.of(7, 9, 11, 13, 15),
            addEach(
                MyList.of(1, 2, 3, 4, 5),
                MyList.of(6, 7, 8, 9, 10)
            )
        )

        assertEquals(
            MyList.of(7, 9, 11, 13, 15),
            zipWith(
                MyList.of(1, 2, 3, 4, 5),
                MyList.of(6, 7, 8, 9, 10)
            ) { a, b -> a + b }
        )
    }

    @Test
    fun testHasSubsequence() {
        val xs: MyList<Int> = MyList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val sub: MyList<Int> = MyList.of(4, 5, 6, 7)

        assertEquals(true, hasSubsequence(xs, sub))
    }

    @Test
    fun testTree() {
        val tree: Tree<Int> = Branch(
            Branch(
                Branch(Leaf(10), Leaf(2)),
                Branch(Leaf(1), Leaf(4)),
            ),
            Branch(
                Branch(Leaf(5), Leaf(7)),
                Branch(Leaf(3), Leaf(9)),
            ),
        )

        assertEquals(15, size(tree))
        assertEquals(10, max(tree))
        assertEquals(3, depth(tree))
        assertEquals(
            Branch(
                Branch(
                    Branch(Leaf(100), Leaf(20)),
                    Branch(Leaf(10), Leaf(40)),
                ),
                Branch(
                    Branch(Leaf(50), Leaf(70)),
                    Branch(Leaf(30), Leaf(90)),
                ),
            ), map(tree) { it * 10 }
        )

        assertEquals(15, sizeF(tree))
        assertEquals(10, maxF(tree))
        assertEquals(3, depthF(tree))
        assertEquals(
            Branch(
                Branch(
                    Branch(Leaf(100), Leaf(20)),
                    Branch(Leaf(10), Leaf(40)),
                ),
                Branch(
                    Branch(Leaf(50), Leaf(70)),
                    Branch(Leaf(30), Leaf(90)),
                ),
            ), mapF(tree) { it * 10 }
        )
    }
}
