package com.sll.gankapplication.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.sll.gankapplication.R
import com.sll.gankapplication.`interface`.MultiTypeData
import com.sll.gankapplication.adapter.MultiRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_gank.*
import onlyloveyd.com.gankioclient.data.EmptyData


/**
 * Created by sunlinlin on 2017/6/13.
 */
open class BaseFragment : Fragment(), BGARefreshLayout.BGARefreshLayoutDelegate {

    internal var TAG = javaClass.simpleName
    internal var adapter = MultiRecyclerAdapter()
    internal var listData = ArrayList<MultiTypeData>()


    override fun onBGARefreshLayoutBeginLoadingMore(p0: BGARefreshLayout?): Boolean {
        return false
    }

    override fun onBGARefreshLayoutBeginRefreshing(p0: BGARefreshLayout?) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_gank, container, false)

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBGALayout()
        initRvGank()
        initBGAData()
    }

    open fun initBGAData() {

    }

    private fun initRvGank() {
        var lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_gank.layoutManager = lm
        rv_gank.adapter = adapter
    }

    private fun initBGALayout() {
        rl_gank.setDelegate(this)
        var refreshViewHolder = BGANormalRefreshViewHolder(context, true)
        refreshViewHolder.setLoadingMoreText("加载更多")
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.white)
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.white)
        rl_gank.setRefreshViewHolder(refreshViewHolder)
    }

    fun onNetworkError() {
        listData.clear()
        listData.add(EmptyData("网络错误~~~"))
        adapter.mData = listData
    }

    fun onDataEmpty() {
        listData.add(EmptyData("请求数据为空~~~"))
    }

    fun endLoading() {
        if(rl_gank!=null){
            if (rl_gank.isLoadingMore) {
                rl_gank.endLoadingMore()
            } else {
                rl_gank.endRefreshing()
            }
        }

    }
}