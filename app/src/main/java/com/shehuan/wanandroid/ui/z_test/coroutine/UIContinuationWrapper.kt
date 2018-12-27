package com.shehuan.wanandroid.ui.z_test.coroutine

import android.os.Handler
import android.os.Looper
import kotlin.coroutines.experimental.Continuation

/**
 * Created by HuangCong on 2018/12/27.
 *
 * 切换到主线程
 */
class UIContinuationWrapper<T>(val continuation: Continuation<T>) : Continuation<T> {

    override val context = continuation.context

    override fun resume(value: T) {
        Handler(Looper.getMainLooper()).post {
            continuation.resume(value)
        }
    }

    override fun resumeWithException(exception: Throwable) {
        Handler(Looper.getMainLooper()).post {
            continuation.resumeWithException(exception)
        }
    }
}
