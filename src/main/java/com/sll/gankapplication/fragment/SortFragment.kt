package com.sll.gankapplication.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sll.gankapplication.R
import com.sll.gankapplication.constant.Constant
import kotlinx.android.synthetic.main.fragment_sort.*

/**
 * Created by sunlinlin on 2017/6/13.
 */
class SortFragment : Fragment() {

    private var sortAdapter: MyAdapter? = null
    private var mCurrentTag = "all"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sort, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sortAdapter = MyAdapter(childFragmentManager)
        vp_sort.adapter = sortAdapter
        tab_sort.setViewPager(vp_sort)
    }

    companion object {
        fun getInstance() = SortFragment()
    }

    class MyAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return SortChildFragment.getInstance(Constant.sCategoryList[position])
        }

        override fun getCount(): Int = Constant.sCategoryList.size

        override fun getPageTitle(position: Int): CharSequence {
            return Constant.sCategoryList[position]
        }
    }

    override fun onResume() {
        super.onResume()
        if (Constant.sCategryListChanged) {
            sortAdapter = null
            sortAdapter = MyAdapter(childFragmentManager)
            vp_sort.adapter = sortAdapter
            tab_sort.setViewPager(vp_sort)
            Constant.sCategryListChanged = false
            for (i in Constant.sCategoryList.indices){
                if (Constant.sCategoryList[i]==mCurrentTag){
                    vp_sort.setCurrentItem(i,true)
                    break
                }
            }
        }
    }

    override fun onPause() {
        mCurrentTag = Constant.sCategoryList[vp_sort.currentItem]
        super.onPause()
    }
}