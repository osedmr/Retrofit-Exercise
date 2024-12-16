package com.example.retrofitexercise.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexercise.R
import com.example.retrofitexercise.databinding.ActivityCoilBinding
import com.example.retrofitexercise.ui.adapters.CoilAdapter
import com.example.retrofitexercise.ui.viewmodels.CoilViewModel
import com.example.retrofitexercise.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoilBinding
    private val viewModel: CoilViewModel by viewModels()
    private lateinit var coilAdapter: CoilAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCoilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Swipe Refresh

        binding.swipeRefreshLayout.setOnRefreshListener {
            coilAdapter.differ.submitList(emptyList())
            viewModel.refreshPosts()
        }

        binding.recyclerView.apply {
            coilAdapter = CoilAdapter()
            adapter =coilAdapter
            layoutManager = LinearLayoutManager(this@CoilActivity,LinearLayoutManager.VERTICAL,false)


            //Pagimation
            addOnScrollListener(object  : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1) && !viewModel.isLastPage && !viewModel.isRefreshing){
                        viewModel.loadMoreImages()
                    }
                }
            })
        }

        viewModel.imageCoil.observe(this, Observer { getImage ->
            when (getImage) {
                is Resource.Success -> {
                    binding.swipeRefreshLayout.isRefreshing =false
                    hideProgressBar()
                    hidePaginationProgressBar()
                    getImage.data?.let {imageList ->
                        coilAdapter.differ.submitList(coilAdapter.differ.currentList + imageList )
                    }
                }
                is Resource.Error -> {
                    binding.swipeRefreshLayout.isRefreshing =false
                    hideProgressBar()
                    hidePaginationProgressBar()
                    getImage.message?.let {
                        binding.textView.visibility = View.VISIBLE
                        binding.textView.text = it
                    }
                }
                is Resource.Loading -> {
                    if (!viewModel.hasFirstImageSeen && !viewModel.isPagination){
                        showProgressBar()
                    }else if(!viewModel.isRefreshing){
                        showPaginationProgressBar()
                    }
                }
            }
        })
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
    }
    private fun showPaginationProgressBar(){
        binding.pbPagination.visibility = View.VISIBLE
    }
    private fun hidePaginationProgressBar(){
        binding.pbPagination.visibility = View.GONE

    }
}