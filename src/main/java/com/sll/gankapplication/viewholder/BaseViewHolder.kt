package com.sll.gankapplication.viewholder

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View

/**
 * Created by sunlinlin on 2017/6/9.
 */
abstract class BaseViewHolder<T>( itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var views = SparseArray<View>()

    fun getView(id:Int):View{
        var view = views.get(id)
        if(view==null){
            view = itemView.findViewById(id)
            views.put(id,view)
        }
        return view
    }

    abstract fun bindViewData(data:T)

}