package com.sll.gankapplication.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.sll.gankapplication.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class WelcomeActivity : AppCompatActivity() {
    var TAG = "WelcomeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_welcome)
        ptv.init("SunLinLin")
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {
                    override fun onComplete() {
                        Log.i(TAG, "onComplete: ")
                    }

                    override fun onError(e: Throwable?) {
                        Log.i(TAG, "onError: ")
                    }

                    override fun onSubscribe(d: Disposable?) {
                        Log.i(TAG, "onSubscribe: ")
                    }

                    override fun onNext(value: Long?) {
                        Log.i(TAG, "onNext: ")
                        startActivity<MainActivity>()
                        finish()
                    }

                })
    }
}
