package com.shehuan.wanandroid.ui.z_test.coroutine

import com.shehuan.wanandroid.ui.z_test.asynctask.excuteAsyncTask
import kotlin.coroutines.experimental.EmptyCoroutineContext
import kotlin.coroutines.experimental.startCoroutine
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Created by HuangCong on 2018/12/27.
 */

/**
 * 开启协程
 */
fun startCoroutine(block: suspend () -> Unit) {
    block.startCoroutine((SelfContinuation(EmptyCoroutineContext)))
}

/**
 * 开启异步任务
 */
suspend fun <T> startAsyncTask(block: () -> T) = suspendCoroutine<T> { continuation ->
    excuteAsyncTask {
        try {
            continuation.resume((block()))
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }
}