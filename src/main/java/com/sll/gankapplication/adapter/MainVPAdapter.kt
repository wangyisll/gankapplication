package com.sll.gankapplication.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.sll.gankapplication.constant.Constant
import com.sll.gankapplication.fragment.AboutFragment
import com.sll.gankapplication.fragment.DailyFragment
import com.sll.gankapplication.fragment.MindFragment
import com.sll.gankapplication.fragment.SortFragment

/**
 * Created by sunlinlin on 2017/6/8.
 */
class MainVPAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> DailyFragment.getInstance()
            1 -> SortFragment.getInstance()
            2 -> MindFragment.getInstance()
            else -> AboutFragment.getInstance()
        }

    }

    override fun getCount(): Int = Constant.sTabTitles.size

}