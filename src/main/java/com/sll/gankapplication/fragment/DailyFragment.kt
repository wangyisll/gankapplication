package com.sll.gankapplication.fragment

import android.util.Log
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.sll.gankapplication.`interface`.OnDatePikerSelectListener
import com.sll.gankapplication.activity.MainActivity
import com.sll.gankapplication.constant.Constant
import com.sll.gankapplication.http.RetrofitClient
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_gank.*
import onlyloveyd.com.gankioclient.data.DailyData
import java.util.*

/**
 * Created by sunlinlin on 2017/6/13.
 */
class DailyFragment : BaseFragment(), OnDatePikerSelectListener {

    override fun onDatePiker(year: Int, month: Int, day: Int) {
        rl_gank.beginRefreshing()
    }

    override fun onBGARefreshLayoutBeginRefreshing(p0: BGARefreshLayout?) {
        doRefresh()
    }

    private fun doRefresh() {
        if (Constant.YEAR == -1 && Constant.MONTH == -1 && Constant.DAY == -1) {
//            var date = Date(System.currentTimeMillis())
            var c = Calendar.getInstance()
//            getDailyData(date.year+1900,date.month+1,date.date)
            getDailyData(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH))
        } else {
            getDailyData(Constant.YEAR, Constant.MONTH+1, Constant.DAY)
        }
    }

    private fun getDailyData(year: Int, month: Int, day: Int) {
        var observer = object : Observer<DailyData> {
            override fun onComplete() {
                endLoading()
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                endLoading()
                onNetworkError()
            }

            override fun onNext(value: DailyData) {
                endLoading()
                listData.clear()
                if (value.category == null || value.category.size == 0)
                    onDataEmpty()
                else {
                    value.results.android?.let { listData.addAll(it) }
                    value.results.app?.let { listData.addAll(it) }
                    value.results.bonus?.let { listData.addAll(it) }
                    value.results.ios?.let { listData.addAll(it) }
                    value.results.js?.let { listData.addAll(it) }
                    value.results.rec?.let { listData.addAll(it) }
                    value.results.res?.let { listData.addAll(it) }
                    value.results.video?.let { listData.addAll(it) }
                }
                adapter.mData = listData
            }

            override fun onSubscribe(d: Disposable?) {
            }

        }
        Log.i(TAG, ": $year : $month : $day")
        RetrofitClient.getDailyData(observer, year, month, day)
    }

    override fun initBGAData() {
        (activity as MainActivity).setOnDatePikerListener(this)
        rl_gank.beginRefreshing()
    }

    companion object {
        fun getInstance() = DailyFragment()
    }
}