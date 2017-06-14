package com.sll.gankapplication.app

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by sunlinlin on 2017/6/8.
 */
class APP : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this))
            return
        LeakCanary.install(this)
    }
}