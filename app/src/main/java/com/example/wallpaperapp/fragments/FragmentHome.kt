package com.example.wallpaperapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapp.AdapterMain
import com.example.wallpaperapp.R
import com.example.wallpaperapp.api.RetrofitInstance
import com.example.wallpaperapp.databinding.ActivityMainBinding
import com.example.wallpaperapp.databinding.FragmentHomeBinding
import com.example.wallpaperapp.model.PhotoResponse
import com.example.wallpaperapp.model.PhotoResponseItem
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates


class FragmentHome : Fragment(R.layout.fragment_home) {
    var _binding : FragmentHomeBinding? = null
    val binding get() = _binding
    private var photos: ArrayList<PhotoResponse> = arrayListOf()
    private lateinit var mRecyclerView: AdapterMain
    private lateinit var layout : GridLayoutManager
    var pageNum = 1
    private var pageSize = 30
    private var isLastPage by Delegates.notNull<Boolean>()
    var totalResult = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        getData()

        binding!!.rcmain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var visibleItem = layout.childCount
                var totalItem = layout.itemCount
                var firstVisibleItem: Int = layout.findFirstVisibleItemPosition()



                if (!isLastPage!!) {
                    if (visibleItem + firstVisibleItem >= totalItem
                        && firstVisibleItem >= 0 &&
                        totalItem >= pageSize
                    ) {
                        pageNum++
                        getData()

                    }
                }
            }
        })

    }

    private fun getData() {
        RetrofitInstance.apiInterface.getData(30, pageNum!!)
            .enqueue(object : Callback<PhotoResponse> {

                override fun onResponse(
                    call: Call<PhotoResponse>,
                    response: Response<PhotoResponse>
                ) {

                    if (response.isSuccessful) {
                        photos.addAll(response.body()!! as ArrayList<PhotoResponse>)
                        mRecyclerView = AdapterMain(context!!.applicationContext, photos as ArrayList<PhotoResponseItem>)
                        mRecyclerView.notifyDataSetChanged()
                        binding!!.rcmain.adapter = mRecyclerView
                        layout = GridLayoutManager(context!!.applicationContext, 3)
                        binding!!.rcmain.layoutManager = layout
                        mRecyclerView.notifyDataSetChanged()

                        binding!!.loading.visibility = View.GONE


                        if (photos.size > 0) {
                            isLastPage = photos.size < pageSize
                        } else isLastPage = true
                    }

                    else {
                        Toasty.error(context!!.applicationContext, "Error While Loading Data Please Check Your Connection.", Toast.LENGTH_SHORT, true).show();

                    }



                }

                override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                    Toasty.error(context!!.applicationContext, "Error While Fetching Data Please Check Your Connection.", Toast.LENGTH_SHORT, true).show();

                }
            })
    }


            }
