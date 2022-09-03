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
