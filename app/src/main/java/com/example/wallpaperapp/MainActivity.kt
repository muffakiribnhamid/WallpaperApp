package com.example.wallpaperapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Range
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpaperapp.api.RetrofitInstance
import com.example.wallpaperapp.databinding.ActivityMainBinding
import com.example.wallpaperapp.fragments.FragmentHome
import com.example.wallpaperapp.fragments.FragmentSearch
import com.example.wallpaperapp.fragments.FragmentSettings
import com.example.wallpaperapp.model.PhotoResponse
import com.example.wallpaperapp.model.PhotoResponseItem
import com.example.wallpaperapp.model.Urls
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.Collections
import java.util.Random
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private var photos: ArrayList<PhotoResponse> = arrayListOf()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setBackgroundColor(Color.parseColor("#00000000"))

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN

        )

        fragmentTransaction(FragmentHome())

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_home -> fragmentTransaction(FragmentHome())
                R.id.menu_search -> fragmentTransaction(FragmentSearch())
                R.id.menu_About -> fragmentTransaction(FragmentSettings())
                else -> {
                }
            }
            true
        }





    }
    fun fragmentTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_home,fragment)
            .addToBackStack(null)
            .commit()
    }


}


