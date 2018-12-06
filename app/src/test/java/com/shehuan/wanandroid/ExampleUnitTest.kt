package com.shehuan.wanandroid

import io.reactivex.Observable
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun main(args: Array<String>) {
        var text = File(ClassLoader.getSystemResource("input").path).readText()
        Observable.fromIterable(text.toCharArray().asIterable()).filter { it.isWhitespace() }
                .subscribe {
                    println(it)
                }
    }
}


