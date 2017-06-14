package com.sll.gankapplication.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowManager
import android.widget.ArrayAdapter
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.sll.gankapplication.R
import com.sll.gankapplication.`interface`.MultiTypeData
import com.sll.gankapplication.adapter.MultiRecyclerAdapter
import com.sll.gankapplication.http.RetrofitClient
import com.sll.gankapplication.util.RxPermissionUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_search.*
import onlyloveyd.com.gankioclient.data.SearchData
import onlyloveyd.com.gankioclient.utils.PublicTools
import org.jetbrains.anko.sdk25.coroutines.onClick


class SearchActivity : AppCompatActivity(), BGARefreshLayout.BGARefreshLayoutDelegate {


    private val TAG = "SearchActivity"
    private var adapter: MultiRecyclerAdapter? = null
    private var list = ArrayList<MultiTypeData>()
    private var pageIndex = 1
    private var keyWord = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        setSupportActionBar(toolBar_search)
        toolBar_search.setNavigationIcon(R.drawable.arrow_back_black_24dp)
        toolBar_search.setNavigationOnClickListener({ onBackPressed() })

        RxPermissionUtils.createInstance(this)
        initSpinner()

        initBGALayout()
        initContent()

        tv_search.isClickable = false
        tv_search.onClick {
            if (keyWord.isEmpty()) {
                Snackbar.make(rv_search, "搜索字不能为空", Snackbar.LENGTH_SHORT).show()
            } else {

                PublicTools.hide_keyboard_from(this@SearchActivity, edt_search)
                refresh()
            }
        }

        edt_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i(TAG, "onTextChanged: $s")
                keyWord = s.toString()
                tv_search.isClickable = !keyWord.isEmpty()
            }

        })


    }

    private fun refresh() {
        pageIndex = 1
        rl_search.beginRefreshing()
        queryGanks(keyWord, spinner.selectedItem.toString(), pageIndex)
    }

    private fun queryGanks(keyWord: String, selectedItem: String, pageIndex: Int) {
        val subscriber = object : Observer<SearchData> {
            override fun onSubscribe(d: Disposable?) {
            }

            override fun onError(e: Throwable?) {
                Snackbar.make(rv_search, "请求错误", Snackbar.LENGTH_SHORT).show()
                rl_search.endRefreshing()
                e?.printStackTrace()
            }

            override fun onComplete() {
                if (rl_search.isLoadingMore) {
                    rl_search.endLoadingMore()
                } else {
                    rl_search.endRefreshing()
                }
            }

            override fun onNext(value: SearchData) {
                if (rl_search.isLoadingMore) {
                } else {
                    list.clear()
                }
                list.addAll(value.results)
                adapter?.mData = list
            }

        }
        Log.i(TAG, " keyword:$keyWord selecteiten:$selectedItem  pageinde:$pageIndex ")
        RetrofitClient.searchData(subscriber, keyWord, selectedItem, pageIndex)
    }

    private fun initBGALayout() {
        rl_search.setDelegate(this)
        val refreshViewHolder = BGANormalRefreshViewHolder(this, true)
        refreshViewHolder.setLoadingMoreText("加载更多")
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.white)
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.white)
        rl_search.setRefreshViewHolder(refreshViewHolder)
    }

    private fun initContent() {
        val llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MultiRecyclerAdapter()
        rv_search.layoutManager = llm
        rv_search.adapter = adapter
    }

    private fun initSpinner() {
        spinner.adapter = ArrayAdapter.createFromResource(this, R.array.dummy_items, R.layout.spinner_item_text)
    }

    override fun onBGARefreshLayoutBeginLoadingMore(p0: BGARefreshLayout?): Boolean {
        queryGanks(keyWord, spinner.selectedItem.toString(), pageIndex)
        return true
    }

    override fun onBGARefreshLayoutBeginRefreshing(p0: BGARefreshLayout?) {
        refresh()
    }
}
