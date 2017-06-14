package com.sll.gankapplication.http

import io.reactivex.Observable
import onlyloveyd.com.gankioclient.data.DailyData
import onlyloveyd.com.gankioclient.data.SearchData
import onlyloveyd.com.gankioclient.data.TypeData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by sunlinlin on 2017/6/9.
 */
interface RetrofitApiService {

    /* category:  福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * pagesize： 数字，大于0
     * pagenum ： 数字，大于0 */
    @GET("data/{category}/{pagesize}/{pagenum}")
    fun getContent(@Path("category") category: String,
                   @Path("pagesize") pagesize: String,
                   @Path("pagenum") pagenum: Int):Observable<TypeData>

    @GET("day/{year}/{month}/{day}")
    fun getDaily(@Path("year") year: Int,
                 @Path("month") month: Int, @Path("day") day: Int): Observable<DailyData>

    /**
     * 搜索
     */
    @GET("search/query/{keyword}/category/{category}/count/20/page/{pageIndex}")
    fun search(@Path("category") category: String,
               @Path("keyword") keyword: String,
               @Path("pageIndex") pageIndex: Int): Observable<SearchData>
}