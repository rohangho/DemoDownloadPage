package com.demosample.demophonexdownload

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var webDisplayer: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webDisplayer = findViewById(R.id.webview);
        // webDisplayer.loadUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")
        webDisplayer.loadUrl("https://www.instagram.com")
        webDisplayer.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                view.loadUrl(request.url.toString())
                Log.e("HIIII", "shouldOverrideUrlLoading: ${request.url}" )
                return true
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.e("HIIII", "onPageStarted: $url")


            }



            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                Log.e("HIIII", "onLoadResource: $url" )
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



}