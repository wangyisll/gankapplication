package com.sll.gankapplication.fragment

import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_gank.*
import onlyloveyd.com.gankioclient.data.MindData
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Created by sunlinlin on 2017/6/13.
 */
class MindFragment : BaseFragment() {

    override fun initBGAData() {
        rl_gank.beginRefreshing()
    }

    fun getContent() {
        Observable.create(ObservableOnSubscribe<ArrayList<MindData>> {
            var mindDates = ArrayList<MindData>()
            var doc:Document? = null
            doc = Jsoup.connect("http://gank.io/post/published").get()
            var trs = doc.select("table")?.select("tr")
            for (i in trs!!.indices){
                val time = trs[i].select("td")[1]
                val detail = trs[i].select("td")[0]
                val url = detail.select("a").attr("href")
                var data = MindData(detail.select("a").text(),detail.select("small").text(),time.text())
                data.url = url
                mindDates.add(data)
            }
            it.onNext(mindDates)
            it.onComplete()
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<MindData>>{
                    override fun onComplete() {
                        endLoading()
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        endLoading()
                        onNetworkError()
                    }

                    override fun onNext(value: ArrayList<MindData>) {
                        endLoading()
                        listData.clear()
                        if (value.size==0){
                            onDataEmpty()
                        }else{
                            listData.addAll(value)
                        }
                        adapter.mData = listData
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }
                })
    }


    override fun onBGARefreshLayoutBeginRefreshing(p0: BGARefreshLayout?) {
        getContent()
    }

    companion object {
        fun getInstance() = MindFragment()
    }
}