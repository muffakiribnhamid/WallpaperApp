package com.example.wallpaperapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapp.*
import com.example.wallpaperapp.api.RetrofitInstance
import com.example.wallpaperapp.api.RetrofitInstance2
import com.example.wallpaperapp.databinding.FragmentHomeBinding
import com.example.wallpaperapp.databinding.FragmentSearchBinding
import com.example.wallpaperapp.model.PhotoResponse
import com.example.wallpaperapp.model.PhotoResponseItem
import com.example.wallpaperapp.model.SearchResponse
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class FragmentSearch : Fragment(R.layout.fragment_search) {
    var _binding : FragmentSearchBinding? = null
    val binding get() = _binding
    private var photos: ArrayList<ResultsItem> = arrayListOf()
    private lateinit var mRecyclerView: AdapterSearch
    private lateinit var layout : GridLayoutManager
    var pageNum = 1
    private var query = "Coding"
    private var pageSize = 30
    private var isLastPage by Delegates.notNull<Boolean>()
    var totalResult = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getData("Kashmir")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData("Kashmir")

        binding!!.search.setOnClickListener {
        val search = binding!!.etQuery.text.toString()
            photos.clear()
            getData(search)

        }

        binding!!.rcSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                        var qu = binding!!.etQuery.text.toString()
                        getData(qu)

                    }
                }
            }
        })

    }

    private fun getData(query : String) {
        RetrofitInstance2.apiInterface2.searchData(30, pageNum!!,query)
            .enqueue(object : Callback<SearchResponseModel> {

                override fun onResponse(
                    call: Call<SearchResponseModel>,
                    response: Response<SearchResponseModel>
                ) {


                    if (response.isSuccessful) {
                        photos.addAll(response.body()!!.results as ArrayList<ResultsItem>)
                        mRecyclerView = AdapterSearch(
                            context!!.applicationContext,
                            photos
                        )
                        mRecyclerView.notifyDataSetChanged()
                        binding!!.rcSearch.adapter = mRecyclerView
                        layout = GridLayoutManager(context!!.applicationContext, 3)
                        binding!!.rcSearch.layoutManager = layout
                        mRecyclerView.notifyDataSetChanged()




                        if (photos.size > 0) {
                            isLastPage = photos.size < pageSize
                        } else isLastPage = true
                    } else {
                        Toasty.error(
                            context!!.applicationContext,
                            "Error While Loading Data Please Check Your Connection.",
                            Toast.LENGTH_SHORT,
                            true
                        ).show();

                    }

                }
            
                

                override fun onFailure(call: Call<SearchResponseModel>, t: Throwable) {
                    Toasty.error(
                        context!!.applicationContext,
                        "Error While Fetching Data Please Check Your Connection.",
                        Toast.LENGTH_SHORT,
                        true
                    ).show();

                }
            })
    }
    }


