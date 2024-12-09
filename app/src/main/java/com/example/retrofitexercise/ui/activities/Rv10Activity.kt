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
import com.example.retrofitexercise.databinding.ActivityRv10Binding
import com.example.retrofitexercise.ui.adapters.Rv10Adapter
import com.example.retrofitexercise.ui.viewmodels.RvViewModel
import com.example.retrofitexercise.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Rv10Activity : AppCompatActivity() {

    private lateinit var binding: ActivityRv10Binding
    val rvViewModel : RvViewModel by viewModels()
    private lateinit var postRecyclerViewAdapter: Rv10Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRv10Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerView.apply {
            postRecyclerViewAdapter = Rv10Adapter()
            adapter = postRecyclerViewAdapter
            layoutManager =LinearLayoutManager(this@Rv10Activity, LinearLayoutManager.VERTICAL,false)


            //PAGINATION İşlemi
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (!recyclerView.canScrollVertically(1) && !rvViewModel.isLastPage){
                        rvViewModel.loadMorePosts()
                    }
                }
            })
        }


        rvViewModel.response10Post.observe(this, Observer { repo ->
            when(repo){
                is Resource.Success -> {
                    hideProgressBar()
                    hidePaginationProgressBar()
                    repo.data?.let {postList ->
                        postRecyclerViewAdapter.differ.submitList(postRecyclerViewAdapter.differ.currentList + postList)
                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    hidePaginationProgressBar()
                    repo.message?.let {error ->
                        binding.textView.text = error
                    }
                }
                is Resource.Loading -> {
                    if (!rvViewModel.hasFirstPostSeen && !rvViewModel.isPagination){
                         showProgressBar()
                    }else{
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