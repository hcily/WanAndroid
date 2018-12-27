package com.shehuan.wanandroid.ui.z_test.asynctask

import java.util.concurrent.Executors

/**
 * Created by HuangCong on 2018/12/27.
 */

val threadPoll by lazy {
    Executors.newCachedThreadPool()
}

fun <T> excuteAsyncTask(block: () -> T) {
    threadPoll.execute { block() }
}