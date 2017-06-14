package com.sll.gankapplication.http

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import onlyloveyd.com.gankioclient.data.DailyData
import onlyloveyd.com.gankioclient.data.SearchData
import onlyloveyd.com.gankioclient.data.TypeData
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by sunlinlin on 2017/6/9.
 */
object RetrofitClient {
    private val retrofit: Retrofit
    private val api: RetrofitApiService
    private val baseUrl = "http://gank.io/api/"

    init {
        val httpclient = OkHttpClient.Builder()
        httpclient.connectTimeout(10, TimeUnit.SECONDS)
        retrofit = Retrofit.Builder().client(httpclient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
        api = retrofit.create(RetrofitApiService::class.java)
    }

    /**
     * 获取每日信息
     */
    fun getDailyData(subscriber: Observer<DailyData>, year: Int, month: Int, day: Int) {
        api.getDaily(year, month, day)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(subscriber)
    }

    /**
     * 获取干货数据
     */
    fun getData(subscriber: Observer<TypeData>, category: String, pageSize: String, pageNum: Int) {
        api.getContent(category, pageSize, pageNum)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .safeSubscribe(subscriber)
    }

    /**
     * 查询数据
     */
    fun searchData(subscriber: Observer<SearchData>, keyword: String, category: String, pageIndex: Int) {
        api.search(category, keyword, pageIndex)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
    }
}