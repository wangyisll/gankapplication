package com.sll.gankapplication.constant

import com.sll.gankapplication.R

/**
 * Created by sunlinlin on 2017/6/8.
 */
object Constant {

    var sTabTitles = arrayOf("每日干货", "分类阅读", "匠心写作", "关于")

    var sTypeColor = mutableMapOf("Android" to R.drawable.bg_android_tag,
            "iOS" to R.drawable.bg_ios_tag,
            "瞎推荐" to R.drawable.bg_rec_tag,
            "拓展资源" to R.drawable.bg_res_tag,
            "App" to R.drawable.bg_app_tag,
            "福利" to R.drawable.bg_bonus_tag,
            "前端" to R.drawable.bg_js_tag,
            "休息视频" to R.drawable.bg_video_tag)
    var sCategoryList = mutableListOf("all", "Android", "瞎推荐", "iOS", "前端", "拓展资源", "App", "休息视频", "福利")

    var sCategryListChanged = false

    var YEAR = -1
    var MONTH = -1
    var DAY = -1

    val ONE_SECOND: Long = 1000
    val ONE_MINUTE = ONE_SECOND * 60
    val ONE_HOUR = ONE_MINUTE * 60
    val ONE_DAY = ONE_HOUR * 24
}