package com.example.wallpaperapp

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.downloader.*
import com.example.wallpaperapp.databinding.ActivityFullBinding
import es.dmoral.toasty.Toasty
import java.io.IOException
import java.net.URL
import kotlin.Error
import kotlin.Int
import kotlin.arrayOf


class FullActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullBinding
    private lateinit var PRDownloader : PRDownloader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullBinding.inflate(layoutInflater)
        setContentView(binding.root)




        getImage()

        binding.wallpaper.setOnClickListener {
            setWallpaper()
        }
        binding.share.setOnClickListener {
            shareImage()
        }
        binding.download.setOnClickListener {
            saveImage()
        }
    }

    private fun setWallpaper() {
        val url = intent.getStringExtra("url")

        //Download Image
        object : AsyncTask<Void,Void,Bitmap>() {
            override fun doInBackground(vararg p0: Void?): Bitmap {
                val url = URL(url)
                return BitmapFactory.decodeStream(url.openConnection().getInputStream())
            }

            //Then set wallpaper

            override fun onPostExecute(result: Bitmap?) {
                val wallpaperManager = WallpaperManager.getInstance(this@FullActivity)
                try {
                    wallpaperManager.setBitmap(result!!)
                    Toast.makeText(this@FullActivity, "Wallpaper Set Succesfully", Toast.LENGTH_SHORT).show()
                }

                catch (e : IOException) {
                    Toast.makeText(this@FullActivity, "Error Settting Wallpaper", Toast.LENGTH_SHORT).show()
                }
            }
        }.execute()




    }
    private fun getImage() {
        val url = intent.getStringExtra("url")
        Glide.with(this).load(url).into(binding.imageView)


    }
    private fun shareImage() {
        val url = intent.getStringExtra("url")
        val intent = Intent(Intent.ACTION_SEND)
        val message = "Hey I got this Wallpaper from Muffakir's App you can Download It here ${url} \nThanks! "
        intent.type = "text/plain"
        intent.putExtra("message",message)

        val chooser = Intent.createChooser(intent,"Share Wallpaper Using!!")
        startActivity(chooser)

    }
    private fun saveImage() {
        if (ContextCompat.checkSelfPermission(this@FullActivity,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),0)
        }
        else {
            val url = intent.getStringExtra("url")

            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle("Downloading Your Wallpaper")
                .setDescription("Please Wait!!")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)


            val dm =  getSystemService(DOWNLOAD_SERVICE) as DownloadManager

            dm.enqueue(request)
            Toasty.success(this,"Downloaded Succesfully",Toasty.LENGTH_SHORT).show()
        }


        }
    }
