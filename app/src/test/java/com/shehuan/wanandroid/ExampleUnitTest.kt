package com.shehuan.wanandroid

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.io.Serializable
import java.math.BigDecimal
import kotlin.reflect.KProperty
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

    /* -------------------------------------先从一个简单的例子入手---------------------------------------------*/

    /**
     * 检测键值对中的数据类型，并进行类型转换
     */
    private inline fun <reified A, reified B> Pair<*, *>.asPairOf(): Pair<A, B>? {
        if (first !is A || second !is B) return null
        return first as A to second as B
    }

    /**
     * 泛型检测和类型擦除
     */
    @Test
    fun typesTest() {
        var somePair: Pair<Any?, Any?>? = null

        val anyToAny = somePair?.asPairOf<Any, Any>()
        println("toAnyThings = $anyToAny")

        val list: ArrayList<Any> = ArrayList()
        for (i in 8..10) {
            list.add(i)
        }

//        somePair = "items" to listOf(1, 2, 3)

        somePair = "items" to list

        val stringToSomething = somePair.asPairOf<String, Any>()
        println("stringToSomething = $stringToSomething")

        val stringToInt = somePair.asPairOf<String, Int>()
        println("stringToInt = $stringToInt")

        val stringToList = somePair.asPairOf<String, List<*>>()
        println("stringToList = $stringToList")

        val stringToStringList = somePair.asPairOf<String, List<String>>() // 破坏类型安全！
        println("stringToStringList = $stringToStringList")
    }

    /* --------------------------------------几个有趣的例子--------------------------------------------*/


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
            start <= 0 || start == end -> return
        }
        result.bigDecimal = result.bigDecimal.multiply(BigDecimal(start))
        factorial(start + 1, end, result)
    }

    @Test
    fun testFactorial() {
        val result = Result
        // 计算1到10000的阶乘
        factorial(1, 10000, result)
        println(result.bigDecimal)
    }


    /**
     * kotlin 调用RxJava
     *
     * 字符个数统计
     */
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


    /**
     * json解析测试
     */
    data class Data(val code: Int, val data: Singer)

    data class Song(val id: Int, val name: String, val singerId: Int)

    data class Singer(val id: Int, val name: String, val gender: Int, val age: Int, val brief: String, val songs: ArrayList<Song>)

    @Test
    fun dataTest() {
        val jsonStr = File(ClassLoader.getSystemResource("json_data")?.path).readText()
        // Gson解析后的数据并不安全
        val data = Gson().fromJson(jsonStr, Data::class.java)
        println("dataTest -> $data")
    }

/*------------------------------------------基础知识-----------------------------------------------*/


    @Test
    fun companionClassTest() {
        val companinoClassTest = CompanionClassTest()
    }


    @Test
    fun byParamTest() {
        val byParamTest = ByParamTest()
        byParamTest.lazyParamTwo = "360!"
        println("byParamTest -> ${byParamTest.lazyParam} ${byParamTest.lazyParamTwo}")
    }
}

open class ParentClass

abstract class AbsParentClass : ParentClass()

interface InterfaceClassOne {
    fun interfaceFunOne(): String {
        return "This is a interface func."
    }

    fun interfaceFunTwo()
}

interface InterfaceClassTwo
interface InterfaceClassThree

/**
 * 伴生对象
 */
class CompanionClassTest : AbsParentClass, InterfaceClassOne, InterfaceClassTwo, InterfaceClassThree {

    val paramOne: Any = "hello"
    lateinit var paramTwo: Any

    init {
        paramTwo = " World!"
    }

    constructor() {
        println("lateinit -> $paramOne $paramTwo")

        println("call interface func -> ${interfaceFunOne()}")
    }

    companion object {
        val TAG: String = "CompaninonClassTest"

        fun companionFun1() {

        }
    }

    override fun interfaceFunTwo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

/**
 * 类委托
 */
class ByClassTest : InterfaceClassOne by CompanionClassTest()


/**
 * 属性委托
 */
class ByParamTest {
    val lazyParam: String by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        "Hello"
    }

    var lazyParamTwo: String by SynchronizedLazyImpl({
        "World!"
    })
}

object UNINITIALIZED_VALUE

class SynchronizedLazyImpl<T>(initializer: () -> T, lock: Any? = null) : Serializable {
    private var initializer: (() -> T)? = initializer
    @Volatile
    var value_: Any? = UNINITIALIZED_VALUE
    // final field is required to enable safe publication of constructed instance
    private val lock = lock ?: this

    var value: T = UNINITIALIZED_VALUE as T
        get() {
            val _v1 = value_
            if (_v1 !== UNINITIALIZED_VALUE) {
                @Suppress("UNCHECKED_CAST")
                return _v1 as T
            }

            return synchronized(lock) {
                val _v2 = value_
                if (_v2 !== UNINITIALIZED_VALUE) {
                    @Suppress("UNCHECKED_CAST") (_v2 as T)
                } else {
                    val typedValue = initializer!!()
                    value_ = typedValue
                    initializer = null
                    typedValue
                }
            }
        }
        set(value) {
            @Suppress("UNCHECKED_CAST")
            field = value as T
        }

    fun isInitialized(): Boolean = value_ !== UNINITIALIZED_VALUE

    inline operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    inline operator fun setValue(thisRef: ByParamTest, property: KProperty<*>, value: Any) {
        this.value_ = value as T
    }
}


/**
 * object类
 */
object ObjectClassTest {
    fun getSomeThing(): Any? {
        return "Hello!"
    }
}

/**
 * 密封类
 */
sealed class SealedClassTest {
    class StatusOne : SealedClassTest()

    class StausTwo(progress: Int) : SealedClassTest()
}

/**
 * data类
 */
data class DataClassTest(var name: String, var year: Int)



