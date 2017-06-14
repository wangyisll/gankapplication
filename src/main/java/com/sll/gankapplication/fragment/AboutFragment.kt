package com.sll.gankapplication.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sll.gankapplication.R

import com.vansuita.materialabout.builder.AboutBuilder
import org.jetbrains.anko.share

/**
 * Created by sunlinlin on 2017/6/8.
 */
class AboutFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = AboutBuilder.with(context)
                .setPhoto(R.mipmap.ic_launcher_round)
                .setName("名字")
                .setSubTitle("子名字")
                .setBrief("hahahahaha")
                .setAppName("appname")
                .addEmailLink("wangyisll@126.com")
                .setVersionNameAsAppSubTitle()
                .addAction(com.vansuita.materialabout.R.mipmap.share,
                        "分享",
                        {context.share("ziji")})
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(false)
                .setBackgroundColor(R.color.colorPrimary)
                .build()

        return view
    }
    companion object{
        fun getInstance() = AboutFragment()
    }
}
