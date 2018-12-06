package com.shehuan.wanandroid

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import java.io.File
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
    fun main() {
        var text = File(ClassLoader.getSystemResource("input")?.path).readText()
        Observable.fromIterable(text.toCharArray().asIterable())
                //.filter { !it.isWhitespace() }
                .groupBy { it }
                .subscribe { o ->
                    o.count().subscribe(Consumer { println("${o.key} -> $it") })
                }
    }
}


