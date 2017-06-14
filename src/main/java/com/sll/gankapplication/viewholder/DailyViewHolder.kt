package com.sll.gankapplication.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sll.gankapplication.R
import com.sll.gankapplication.activity.WebActivity
import com.sll.gankapplication.constant.Constant
import onlyloveyd.com.gankioclient.data.DetailsData
import onlyloveyd.com.gankioclient.utils.PublicTools
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * Created by sunlinlin on 2017/6/13.
 */
class DailyViewHolder(view: View) : BaseViewHolder<DetailsData>(view) {
    override fun bindViewData(data: DetailsData) {
        val ivDailay = getView(R.id.iv_daily) as ImageView
        val tvTitle = getView(R.id.tv_title_daily) as TextView
        val tvType = getView(R.id.tv_type_daily) as TextView
        val tvDate = getView(R.id.tv_date_daily) as TextView

        tvTitle.text = data.desc.trim { it <= ' ' }
        tvDate.text = PublicTools.date2String(data.publishedAt.time, "yyyy.MM.dd")

        if (data.images!=null&&data.images.size>0) {
            Glide.with(itemView.context)
                    .load(data.images[0])
                    .placeholder(R.mipmap.img_default_gray)
                    .into(ivDailay)
        } else {
            if (data.type == "福利") {
                Glide.with(itemView.context)
                        .load(data.url)
                        .placeholder(R.mipmap.img_default_gray)
                        .into(ivDailay)
            } else {
                ivDailay.visibility = View.GONE
            }
        }

        tvType.text = data.type
        Constant.sTypeColor[data.type]?.let { tvType.backgroundResource = it }
        itemView.onClick { itemView.context.startActivity<WebActivity>("URL" to data.url) }
    }
}
