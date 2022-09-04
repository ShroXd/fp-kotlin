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

sealed class MyList<out T> {
    // A companion object is added to the body of its definition
    // to add some behavior to the MyList type
    companion object {
        // vararg - accepts a variable number of arguments
        fun <T> of(vararg aa: T): MyList<T> {
            val tail = aa.sliceArray(1 until aa.size)
            // * - spread operator
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        fun sum(ints: MyList<Int>): Int =
            when (ints) {
                is Nil -> 0
                is Cons -> ints.head + sum(ints.tail)
            }

        fun product(doubles: MyList<Double>): Double =
            when (doubles) {
                is Nil -> 1.0
                is Cons ->
                    if (doubles.head == 0.0) 0.0
                    else doubles.head * product(doubles.tail)
            }
    }
}
object Nil : MyList<Nothing>()
data class Cons<out T>(val head: T, val tail: MyList<T>): MyList<T>()

fun <T> value(xs: MyList<T>, fn: (MyList<T>) -> MyList<T>): T =
    (fn(xs) as Cons).head

fun <T> tail(xs: MyList<T>): MyList<T> =
    when (xs) {
        is Nil -> Nil
        is Cons -> xs.tail
    }

fun <T> setHead(xs: MyList<T>, x: T): MyList<T> =
    when (xs) {
        is Nil -> Nil
        is Cons -> Cons(x, tail(xs))
    }

fun <T> drop(l: MyList<T>, n: Int): MyList<T> =
    when {
        l is Nil -> Nil
        n == 0 -> l
        else -> drop(tail(l), n - 1)
    }

fun <T> dropWhile(l: MyList<T>, f: (T) -> Boolean): MyList<T> =
    when (l) {
        is Nil -> Nil
        is Cons -> if (f(l.head)) l else dropWhile(tail(l), f)
    }

fun <T> append(a1: MyList<T>, a2: MyList<T>): MyList<T> =
    when (a1) {
        is Nil -> a2
        is Cons -> Cons(a1.head, append(a1.tail, a2))
    }

fun <T> init(l: MyList<T>): MyList<T> =
    when (l) {
        is Cons ->
            if (l.tail == Nil) Nil
            else Cons(l.head, init(l.tail))
        is Nil ->
            throw IllegalStateException("Cannot init Nil list")
    }

fun <A, B> foldRight(xs: MyList<A>, z: B, f: (A, B) -> B): B =
    when (xs) {
        is Nil -> z
        is Cons -> f(xs.head, foldRight(xs.tail, z, f))
    }
fun sum(ints: MyList<Int>): Int =
    foldRight(ints, 0) { a, b -> a + b }
fun product(dbs: MyList<Double>): Double =
    foldRight(dbs, 1.0) { a, b -> a * b }
