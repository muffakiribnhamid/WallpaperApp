package com.example.wallpaperapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FragmentSettingsBinding

class FragmentSettings : Fragment(R.layout.fragment_settings) {

    var _binding : FragmentSettingsBinding? = null
    val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.git.setOnClickListener {
            val BrowserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/muffakiribnhamid/"))
            startActivity(BrowserIntent)
        }
        }


    }

