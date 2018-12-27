package com.shehuan.wanandroid.ui.z_test.httputil

import com.shehuan.wanandroid.base.net.RetrofitManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by HuangCong on 2018/12/27.
 */

class HttpService {

    companion object {
        val service by lazy {
            RetrofitManager.create(CoroutineTestApi::class.java)
        }
    }

}

interface CoroutineTestApi {

    @GET
    fun getPicData(@Url picUrl: String): Call<ResponseBody>
}