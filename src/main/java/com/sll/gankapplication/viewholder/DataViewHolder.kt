package com.sll.gankapplication.viewholder

import android.view.View
import android.widget.TextView
import com.sll.gankapplication.R
import com.sll.gankapplication.activity.WebActivity
import com.sll.gankapplication.constant.Constant
import onlyloveyd.com.gankioclient.data.CategoryData
import onlyloveyd.com.gankioclient.utils.PublicTools
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
/**
 * Created by sunlinlin on 2017/6/9.
 */
class DataViewHolder(view: View) : BaseViewHolder<CategoryData>(view) {
    override fun bindViewData(data: CategoryData) {
        val tvTitle = getView(R.id.tv_title) as TextView
        val tvAuthor = getView(R.id.tv_author) as TextView
        val tvDate = getView(R.id.tv_date) as TextView
        val tvType = getView(R.id.tv_type) as TextView
        tvTitle.text = data.desc.trim { it <= ' ' }

        var time = data.publishedAt
        time = time.substring(0, 19).replace("T", " ")
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = sdf.parse(time)
        tvDate.text = PublicTools.getTimestampString(date)
        tvAuthor.text = data.who
        tvType.text = data.type
        Constant.sTypeColor[data.type]?.let {
            tvType.setBackgroundResource(it)
        }
        itemView.onClick { itemView.context.startActivity<WebActivity>("URL" to data.url)}
    }
}