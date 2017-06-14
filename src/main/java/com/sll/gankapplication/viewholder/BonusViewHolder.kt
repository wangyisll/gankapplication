package com.sll.gankapplication.viewholder

import android.graphics.Bitmap
import android.os.Environment
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.sll.gankapplication.R
import com.sll.gankapplication.util.RxPermissionUtils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import onlyloveyd.com.gankioclient.data.CategoryData
import onlyloveyd.com.gankioclient.utils.PublicTools
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.File

/**
 * Created by sunlinlin on 2017/6/9.
 */
class BonusViewHolder(view: View) : BaseViewHolder<CategoryData>(view) {
    val TAG = "BonusViewHolder"
    override fun bindViewData(data: CategoryData) {
        val ivPicture = getView(R.id.imgPicture) as ImageView
        val ibDownload = getView(R.id.ib_download) as ImageButton

        Glide.with(itemView.context).load(data.url).into(ivPicture)

        val rxPermission = RxPermissionUtils.instance
        ibDownload.onClick {
            rxPermission?.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ?.subscribe(object : Observer<Boolean> {
                        override fun onError(e: Throwable?) {

                        }

                        override fun onSubscribe(d: Disposable?) {
                        }

                        override fun onComplete() {
                        }

                        override fun onNext(value: Boolean) {
                            if (value) {//同意权限
                                Snackbar.make(itemView, "开始下载", Snackbar.LENGTH_SHORT).show()
                                Glide.with(itemView.context)
                                        .load(data.url)
                                        .asBitmap()
                                        .into(object : SimpleTarget<Bitmap>() {
                                            override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                                                Observable.create(ObservableOnSubscribe<Bitmap> {

                                                    try {
                                                        PublicTools.saveBitmap(resource!!, Environment.getExternalStorageDirectory().absolutePath
                                                                + File.separator
                                                                + data.desc)
                                                    } catch(e: Exception) {
                                                        e.printStackTrace()
                                                        it.onError(e)
                                                    }
                                                    it.onComplete()
                                                })
                                                        .subscribeOn(Schedulers.io())
                                                        .unsubscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(object : Observer<Bitmap> {
                                                            override fun onComplete() {
                                                                Log.i(TAG, "onComplete: ")
                                                                Log.i(TAG, "onNext: ")
                                                                Snackbar.make(itemView, "下载成功", Snackbar.LENGTH_SHORT).show()
                                                            }

                                                            override fun onSubscribe(d: Disposable?) {
                                                                Log.i(TAG, "onSubscribe: ")
                                                            }

                                                            override fun onError(e: Throwable) {
                                                                Log.i(TAG, "onError: ")
                                                                Snackbar.make(itemView, "下载失败", Snackbar.LENGTH_SHORT).show()
                                                                e.printStackTrace()
                                                            }

                                                            override fun onNext(value: Bitmap?) {

                                                            }
                                                        })
                                            }
                                        })
                            } else {
                                Snackbar.make(itemView, "请允许存储权限", Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    })
        }
    }
}