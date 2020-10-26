package com.demosample.demophonexdownload

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var webDisplayer: WebView
    private lateinit var menuIcon: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webDisplayer = findViewById(R.id.webview);
        // webDisplayer.loadUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")
        webDisplayer.loadUrl("https://www.wikipedia.org")
        webDisplayer.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                view.loadUrl(request.url.toString())
                menuIcon!!.isVisible = request.url.toString().endsWith(".mp4")
                return true
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.e("HIIII", "onPageStarted: $url")
                menuIcon!!.isVisible = url.endsWith(".mp4")

            }
        }
        webDisplayer.settings.loadsImagesAutomatically = true
        webDisplayer.settings.javaScriptEnabled = true
        webDisplayer.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webDisplayer.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveBackToPreviousPage()
    }

    private fun moveBackToPreviousPage() {
        if (webDisplayer.canGoBack()) {
            webDisplayer.goBack()
        } else {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.my_menu, menu)

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuIcon = menu!!.findItem(R.id.actiondownload)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.actiondownload -> {
            startActivity(Intent(this, DownloadPage::class.java))
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }


}