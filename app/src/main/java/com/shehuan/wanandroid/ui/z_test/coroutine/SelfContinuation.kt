package com.shehuan.wanandroid.ui.z_test.coroutine

import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by HuangCong on 2018/12/27.
 */
class SelfContinuation<T>(override val context: CoroutineContext) : Continuation<T> {

    override fun resume(value: T) {
    }

    override fun resumeWithException(exception: Throwable) {
    }

}