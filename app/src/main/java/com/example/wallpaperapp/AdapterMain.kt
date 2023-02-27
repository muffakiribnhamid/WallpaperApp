package com.example.wallpaperapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.model.PhotoResponseItem
import com.example.wallpaperapp.model.Urls

class AdapterMain(
    val context: Context, val photoList: ArrayList<PhotoResponseItem>
) : RecyclerView.Adapter<AdapterMain.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photoMain: ImageView? = null

        init {
            photoMain = itemView.findViewById(R.id.imageMain)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_photo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = photoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photoModel = photoList[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(context,FullActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            intent.putExtra("url",photoModel.urls.full)
            context.startActivity(intent)
        }

        Glide.with(context).load(photoModel.urls.regular).into(holder.photoMain!!)
    }
}