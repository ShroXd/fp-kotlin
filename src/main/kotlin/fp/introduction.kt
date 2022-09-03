package fp

fun factorial(i: Int): Int {
    tailrec fun go(n: Int, acc: Int): Int =
        if (n <= 0) acc
        else go(n - 1, n * acc)

    return go(i, 1)
}

fun fib(i: Int): Int {
    tailrec fun go(n: Int, curr: Int, next: Int): Int =
        if (n == 0) curr
        else go(n - 1, next, next + curr)

    return go(i, 0, 1)
}

object Utils {
    private fun abs(n: Int): Int =
        if (n < 0) -n
        else n

    private fun factorial(i: Int): Int {
        fun go(n: Int, acc: Int): Int =
            if (n <= 0) acc
            else go(n - 1, n * acc)

        return go(i, 1)
    }

    fun formatAbs(x: Int): String {
        val msg = "The absolute value of %d of %d"
        return msg.format(x, abs(x))
    }

    fun formatFactorial(x: Int): String {
        val msg = "The factorial of %d is %d"
        return msg.format(x, factorial(x))
    }

    fun formatResult(name: String, n: Int, f: (Int) -> Int): String {
        val msg = "The %s of %d is %d"
        return msg.format(name, n, f(n))
    }
}

fun findFirst(ss: Array<String>, key: String): Int {
    tailrec fun loop(n: Int): Int =
        when {
            n >= ss.size -> -1
            ss[n] == key -> n
            else -> loop(n + 1)
        }

    return loop(0)
}

fun <T> findFirst(xs: Array<T>, fn: (T) -> Boolean): Int {
    tailrec fun loop(n: Int): Int =
        when {
            n >= xs.size -> -1
            fn(xs[n]) -> n
            else -> loop(n + 1)
        }

    return loop(0)
}

// Note: The following is the traditional syntax
//fun <T> isSorted(aa: List<T>, order: (T, T) -> Boolean): Boolean {
//    fun loop(n: Int): Boolean {
//        return if (n > aa.size - 2) true
//        else if (order(aa[n], aa[n+1])) loop(n + 1)
//        else false
//    }
//
//    return loop(0)
//}

fun <T> isSorted(aa: List<T>, order: (T, T) -> Boolean): Boolean {
    tailrec fun loop(n: Int): Boolean =
        when {
            n > aa.size - 2 -> true
            order(aa[n], aa[n+1]) -> loop(n + 1)
            else -> false
        }

    return loop(0)
}

fun <A, B, C> partial(a: A, f: (A, B) -> C): (B) -> C =
    { b -> f(a, b) }

fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C =
    { a -> { b -> f(a, b)} }

fun <A, B, C> uncurry(f: (A) -> (B) -> C): (A, B) -> C =
    { a, b -> f(a)(b) }

fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C =
    { a -> f(g(a)) }
