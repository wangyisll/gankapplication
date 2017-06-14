package com.sll.gankapplication.viewholder

import android.view.View
import android.widget.TextView
import com.sll.gankapplication.R
import com.sll.gankapplication.activity.WebActivity
import onlyloveyd.com.gankioclient.data.MindData
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * Created by sunlinlin on 2017/6/13.
 */
class MindViewHolder(view: View) : BaseViewHolder<MindData>(view) {
    override fun bindViewData(data: MindData) {
        val tvTitle = getView(R.id.tv_title) as TextView
        val tvAuthor = getView(R.id.tv_author) as TextView
        val tvDate = getView(R.id.tv_date) as TextView

        tvTitle.text = data.title?.trim { it <= ' ' }
        tvDate.text = data.time?:""

        tvAuthor.text = data.author?:""

        itemView.onClick { itemView.context.startActivity<WebActivity>("URL" to data.url) }
    }
}