package com.sll.gankapplication.activity

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sll.gankapplication.R
import kotlinx.android.synthetic.main.activity_web.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.share

class WebActivity : AppCompatActivity() {
    var url:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        url=intent.extras.getString("URL")

        setSupportActionBar(tb_web)
        tb_web.setNavigationIcon(R.drawable.arrow_back_black_24dp)
        tb_web.setNavigationOnClickListener { onBackPressed() }

        initWebView()

        wv.loadUrl(url)
    }

    private fun initWebView() {
        var settings = wv.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = false
        settings.setAppCacheEnabled(true)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.setSupportZoom(true)
        wv.setWebChromeClient(object: WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                pb_web.progress = newProgress

                pb_web.visibility = if (newProgress==100) View.GONE else View.VISIBLE
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                setTitle(title)
            }
        })

        wv.setWebViewClient(object:WebViewClient(){})
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.refresh -> wv.reload()
            R.id.share -> url?.let { share(it) }
            R.id.openinbrowse -> url?.let { browse(it) }
            R.id.copyurl -> {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.text = url
                Snackbar.make(wv,"已复制到剪切板",Snackbar.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
