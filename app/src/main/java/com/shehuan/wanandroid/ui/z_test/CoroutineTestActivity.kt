package com.shehuan.wanandroid.ui.z_test

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.ui.z_test.coroutine.startAsyncTask
import com.shehuan.wanandroid.ui.z_test.coroutine.startCoroutine
import com.shehuan.wanandroid.ui.z_test.httputil.HttpService
import kotlinx.android.synthetic.main.activity_coroutine_test.*

const val PIC_URL = "https://p2.ssl.qhimg.com/t01d91636862957f76e.png"

/**
 * 协程测试用例
 */
class CoroutineTestActivity : AppCompatActivity() {

     val TAG ="CoroutineTestActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_test)


        test_btn.setOnClickListener {
            Log.d(TAG,"${Thread.currentThread()}-> 协程开始")
            startCoroutine {
                Log.d(TAG,"${Thread.currentThread()}-> 异步任务开始")
                var bitmap = startAsyncTask {
                    downloadPic(PIC_URL)
                }
                Log.d(TAG,"${Thread.currentThread()}-> 异步任务结束 $bitmap")
                test_image.setImageBitmap(bitmap)
            }
            Log.d(TAG,"${Thread.currentThread()}-> 协程结束")
        }
    }

    private fun downloadPic(picUrl: String): Bitmap {
        val responseBody = HttpService.service.getPicData(picUrl).execute()
        if (responseBody.isSuccessful) {
            responseBody.body()?.byteStream()?.readBytes()?.let { byteArray ->
                return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            }
            throw Exception("download failed : no data")
        } else {
            throw Exception("http connect failed : ${responseBody.code()}")
        }
    }

}
