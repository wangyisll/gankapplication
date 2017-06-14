package com.sll.gankapplication.viewholder

import android.view.View
import android.widget.TextView
import com.sll.gankapplication.R
import onlyloveyd.com.gankioclient.data.EmptyData

/**
 * Created by sunlinlin on 2017/6/13.
 */
class EmptyViewHolder(view:View) :BaseViewHolder<EmptyData>(view) {
    override fun bindViewData(data: EmptyData) {
        val tv = getView(R.id.tv_empty) as TextView
        tv.text = data.message
    }
}