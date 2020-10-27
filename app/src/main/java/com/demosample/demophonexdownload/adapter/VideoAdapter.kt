package com.demosample.demophonexdownload.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demosample.demophonexdownload.R
import com.demosample.demophonexdownload.adapter.VideoAdapter.MyViewHolder
import com.demosample.demophonexdownload.utility.Utility

class VideoAdapter(val context: Context,val list: ArrayList<String>) : RecyclerView.Adapter<MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.video_container, parent, false)
        return MyViewHolder(v, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.thumbnail.setImageBitmap(Utility.retriveVideoFrameFromVideo(list[position]))
        holder.title.text = URLUtil.guessFileName(list[position], null, null)
    }
    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){
       val thumbnail = itemView.findViewById<ImageView>(R.id.videoTumbnail)
        val title = itemView.findViewById<TextView>(R.id.title)
        val downLoadButton = itemView.findViewById<ImageView>(R.id.downloadButton)
    }
}