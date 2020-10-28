package com.demosample.demophonexdownload.activity

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demosample.demophonexdownload.R
import com.demosample.demophonexdownload.adapter.VideoAdapter
import com.demosample.demophonexdownload.utility.JsInterface
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MainActivity : AppCompatActivity() {

    private lateinit var webDisplayer: WebView
    private var sheetBehavior: BottomSheetBehavior<*>? = null
    private lateinit var layoutBottomSheet: ConstraintLayout
    private lateinit var downloadTitle: TextView
    private lateinit var videoRecycler: RecyclerView
    var videoLIst: ArrayList<String> = ArrayList()
    var videoAdapter = VideoAdapter(this, videoLIst)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutBottomSheet = findViewById(R.id.bcks)
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)
        downloadTitle = findViewById(R.id.textHeadings)
        videoRecycler = findViewById(R.id.recyclerList)
        videoRecycler.layoutManager = LinearLayoutManager(this)
        videoRecycler.adapter = videoAdapter



        sheetBehavior!!.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(view: View, i: Int) {
                if (BottomSheetBehavior.STATE_EXPANDED == i) {
                    downloadTitle.visibility = View.GONE
                    layoutBottomSheet.setBackgroundResource(R.drawable.boxy_shape)
                }
                if (BottomSheetBehavior.STATE_COLLAPSED == i) {

                    downloadTitle.visibility = View.VISIBLE
                    layoutBottomSheet.setBackgroundResource(R.drawable.rounder_shape)
                }
                if (BottomSheetBehavior.STATE_HIDDEN == i) {

                }
            }

            override fun onSlide(view: View, v: Float) {}
        })

        webDisplayer = findViewById(R.id.webview)
        webDisplayer.loadUrl("https://gaana.com")


        webDisplayer.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress === 100) {
                    val code = "var mediaElement;" +
                            "mediaCheck();" +
                            "document.onclick = function(){" +
                            "    mediaCheck();" +
                            "};" +
                            "function mediaCheck(){" +
                            "    for(var i = 0; i < document.getElementsByTagName('video').length; i++){" +
                            "        var media = document.getElementsByTagName('video')[i];" +
                            "        media.onplay = function(){" +
                            "            mediaElement = media;" +
                            "            JSOUT.mediaAction('true');" +
                            "        };" +
                            "        media.onpause = function(){" +
                            "            mediaElement = media;" +
                            "            JSOUT.mediaAction('false');" +
                            "        };" +
                            "    }" +
                            "    for(var i = 0; i < document.getElementsByTagName('audio').length; i++){" +
                            "        var media = document.getElementsByTagName('audio')[i];" +
                            "        media.onplay = function(){" +
                            "            mediaElement = media;" +
                            "            JSOUT.mediaAction('true');" +
                            "        };" +
                            "        media.onpause = function(){" +
                            "            mediaElement = media;" +
                            "            JSOUT.mediaAction('false');" +
                            "        };" +
                            "    }" +
                            "}"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        webDisplayer.evaluateJavascript(code, null)
                    } else {
                        webDisplayer.loadUrl("javascript:$code")
                    }
                }
            }


        }


        webDisplayer.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                view.loadUrl(request.url.toString())
                return true
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.e("HIIII", "onPageStarted: $url")

            }


        }
        webDisplayer.settings.loadsImagesAutomatically = true
        webDisplayer.settings.javaScriptEnabled = true
        webDisplayer.settings.domStorageEnabled = true
        webDisplayer.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webDisplayer.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webDisplayer.addJavascriptInterface(JsInterface(this), "JSOUT")
        wv = webDisplayer

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

    companion object {
        lateinit var wv: WebView
    }


}