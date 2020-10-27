package com.demosample.demophonexdownload.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
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
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MainActivity : AppCompatActivity() {

    private lateinit var webDisplayer: WebView
    private var sheetBehavior: BottomSheetBehavior<*>? = null
    private lateinit var layoutBottomSheet: ConstraintLayout
    private lateinit var downloadTitle : TextView
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
        videoRecycler.layoutManager= LinearLayoutManager(this)
        videoRecycler.adapter = videoAdapter



        sheetBehavior!!.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(view: View, i: Int) {
                if (BottomSheetBehavior.STATE_EXPANDED == i) {
                    downloadTitle.visibility = View.GONE
                    layoutBottomSheet.setBackgroundResource(R.drawable.boxy_shape)
                    videoRecycler.visibility = View.VISIBLE
                }
                if (BottomSheetBehavior.STATE_COLLAPSED == i) {

                    downloadTitle.visibility = View.VISIBLE
                    videoRecycler.visibility = View.GONE
                    layoutBottomSheet.setBackgroundResource(R.drawable.rounder_shape)
                }
                if (BottomSheetBehavior.STATE_HIDDEN == i) {

                }
            }

            override fun onSlide(view: View, v: Float) {}
        })

        webDisplayer = findViewById(R.id.webview)
        webDisplayer.loadUrl("https://www.instagram.com")
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


            /**
             * Getting all the link of the resoursces
             */
            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                Log.e("HIIII", "onLoadResource: $url")
                if (url!!.contains(".mp4", true)) {
                    if (!videoLIst.contains(url))
                        videoLIst.add(url)
                    layoutBottomSheet.visibility = View.VISIBLE
                    videoAdapter.notifyItemInserted(videoLIst.size)

                    val p = view!!.layoutParams as MarginLayoutParams
                    p.setMargins(0, 0, 0, 120)
                    webDisplayer.requestLayout()
                }
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