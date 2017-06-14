package com.sll.gankapplication.fragment

import android.os.Bundle
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.sll.gankapplication.http.RetrofitClient
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_gank.*
import onlyloveyd.com.gankioclient.data.TypeData

/**
 * Created by sunlinlin on 2017/6/13.
 */
class SortChildFragment : BaseFragment() {
    private var pageNum = 1
    private var category = "all"
    override fun onBGARefreshLayoutBeginLoadingMore(p0: BGARefreshLayout?): Boolean {
        getContent(category,++pageNum)
        return true
    }

    override fun onBGARefreshLayoutBeginRefreshing(p0: BGARefreshLayout?) {
        getContent(category,1)
    }

    override fun initBGAData() {
        category = arguments.getString("ARG")
        rl_gank.beginRefreshing()
    }

    fun getContent(category: String, pageNum: Int) {
        var observer = object : Observer<TypeData>{
            override fun onSubscribe(d: Disposable?) {

            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                endLoading()
                onNetworkError()
            }

            override fun onComplete() {
                endLoading()
            }

            override fun onNext(value: TypeData) {
                endLoading()
                listData.clear()
                if(value.results==null||value.results.size==0){
                    onDataEmpty()
                }else{
                    listData.addAll(value.results)
                }
                  adapter.mData = listData
            }
        }

        RetrofitClient.getData(observer,category,"10",pageNum)

    }

    companion object {
        fun getInstance(category: String): SortChildFragment {
            var fragment = SortChildFragment()
            var arg = Bundle()
            arg.putString("ARG", category)
            fragment.arguments= arg
            return fragment
        }
    }

}