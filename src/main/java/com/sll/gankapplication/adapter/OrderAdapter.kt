package com.sll.gankapplication.adapter

import android.support.v4.view.MotionEventCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.sll.gankapplication.R
import com.sll.gankapplication.constant.Constant
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.sdk25.coroutines.onTouch

/**
 * Created by sunlinlin on 2017/6/13.
 */
class OrderAdapter(val touchHelper: ItemTouchHelper) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onBindViewHolder(vh: OrderViewHolder, position: Int) {
        vh.tvTitle?.text = Constant.sCategoryList[position]
        vh.iv_sort?.onTouch { v, event ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                touchHelper.startDrag(vh)
            }
        }

        Constant.sTypeColor[Constant.sCategoryList[position]]?.let { vh.item?.backgroundResource=it }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OrderViewHolder {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.rv_item_sort, p0, false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Constant.sCategoryList.size
    }

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView? = null
        var iv_sort: ImageView? = null
        var item: FrameLayout? = null

        init {
            tvTitle = view.findViewById(R.id.tv_title) as TextView
            iv_sort = view.findViewById(R.id.iv_sort) as ImageView
            item = view.findViewById(R.id.item) as FrameLayout
        }

    }
}