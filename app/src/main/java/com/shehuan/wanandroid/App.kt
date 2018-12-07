package com.shehuan.wanandroid

import androidx.multidex.MultiDexApplication
import org.litepal.LitePal

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        LitePal.initialize(this)
    }

    companion object {
        private lateinit var instance: App
        fun getApp(): App {
            return instance
        }
    }
}