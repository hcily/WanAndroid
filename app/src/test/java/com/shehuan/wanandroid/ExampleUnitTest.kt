package com.shehuan.wanandroid

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.math.BigDecimal
import kotlin.test.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @BeforeEach
    internal fun setUp() {
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun countLetter() {
        var text = File(ClassLoader.getSystemResource("input")?.path).readText()
        Observable.fromIterable(text.toCharArray().asIterable())
                //.filter { !it.isWhitespace() }
                .groupBy { it }
                .subscribe { o ->
                    o.count().subscribe(Consumer { println("${o.key} -> $it") })
                }
    }

    object Result {
        var bigDecimal: BigDecimal = BigDecimal(1)
    }

    /**
     * 阶乘
     *
     * -> tailrec -> 尾递归优化
     */
    private tailrec fun factorial(start: Int, end: Int, result: Result) {
        when {
            start <= 0 ||
                    start == end -> return
        }
        result.bigDecimal = result.bigDecimal.multiply(BigDecimal(start))
        factorial(start + 1, end, result)
    }

    @Test
    fun testFactorial() {
        val result = Result
        factorial(1, 1000000, result)
        println(result.bigDecimal)
    }


}


