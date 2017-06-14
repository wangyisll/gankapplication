package com.sll.gankapplication.util

import android.app.Activity
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * Created by sunlinlin on 2017/6/9.
 */
object RxPermissionUtils {
    var instance : RxPermissions?=null
    fun createInstance(activity:Activity){
        if(instance==null){
            instance = RxPermissions(activity)
        }
    }
}