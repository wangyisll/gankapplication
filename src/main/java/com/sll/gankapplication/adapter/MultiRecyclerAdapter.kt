package com.sll.gankapplication.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sll.gankapplication.R
import com.sll.gankapplication.`interface`.MultiTypeData
import com.sll.gankapplication.viewholder.*
import onlyloveyd.com.gankioclient.data.CategoryData

/**
 * Created by sunlinlin on 2017/6/9.
 */
class MultiRecyclerAdapter : RecyclerView.Adapter<BaseViewHolder<Any>>() {

    var mData = ArrayList<MultiTypeData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(vg: ViewGroup, type: Int): BaseViewHolder<Any> {
        val view = LayoutInflater.from(vg.context).inflate(type, vg, false)
        when (type) {
            R.layout.rv_item_image -> return BonusViewHolder(view) as BaseViewHolder<Any>
            R.layout.rv_item_text -> return DataViewHolder(view) as BaseViewHolder<Any>
            R.layout.rv_item_daily -> return DailyViewHolder(view) as BaseViewHolder<Any>
            R.layout.rv_item_empty -> return EmptyViewHolder(view) as BaseViewHolder<Any>
            R.layout.rv_item_mind -> return MindViewHolder(view) as BaseViewHolder<Any>
            else -> return null!!
        }
    }

    override fun onBindViewHolder(vh: BaseViewHolder<Any>?, position: Int) {
        mData[position].let { vh?.bindViewData(it) }
    }

    override fun getItemViewType(position: Int): Int {
        when (mData[position].getType()) {
            2 -> {
                if ((mData[position] as CategoryData).type == "福利") {

                    return R.layout.rv_item_image
                } else {
                    return R.layout.rv_item_text
                }
            }
            0 -> return R.layout.rv_item_empty
            1 -> return R.layout.rv_item_daily
            3 -> return R.layout.rv_item_mind
        }
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int = mData.size

}