package com.shehuan.wanandroid.ui.z_test.coroutine

import android.util.Log
import com.shehuan.wanandroid.ui.z_test.TAG
import com.shehuan.wanandroid.ui.z_test.asynctask.excuteAsyncTask
import kotlin.coroutines.experimental.startCoroutine
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Created by HuangCong on 2018/12/27.
 */

/**
 * 开启协程
 */
fun startCoroutine(block: suspend () -> Unit) {
    block.startCoroutine((SelfContinuation(SelfCoroutineContext())))
}

/**
 * 开启异步任务
 */
suspend fun <T> startAsyncTask(block: () -> T) = suspendCoroutine<T> { continuation ->
    excuteAsyncTask {
        try {
            Log.d(TAG,"${Thread.currentThread()}-> 异步任务进行中... ")
            continuation.resume((block()))
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }
}