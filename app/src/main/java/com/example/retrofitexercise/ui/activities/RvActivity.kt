package com.example.retrofitexercise.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitexercise.databinding.ActivityRvBinding
import com.example.retrofitexercise.ui.adapters.RvAdapter
import com.example.retrofitexercise.ui.viewmodels.RvViewModel
import com.example.retrofitexercise.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RvActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRvBinding
    val rvViewModel : RvViewModel by viewModels()
    private lateinit var postRecyclerViewAdapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRvBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.recyclerView.apply {
            postRecyclerViewAdapter = RvAdapter()
            adapter = postRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@RvActivity,LinearLayoutManager.VERTICAL,false)
        }

        rvViewModel.getPosts()

        rvViewModel.postList.observe(this, Observer {repo ->
            when(repo){
                is Resource.Success -> {
                    Log.d("Ose", "Başarılı: ${repo.data}")
                    hideProgressBar()
                    repo.data?.let {postList ->
                       postRecyclerViewAdapter.differ.submitList(postList)
                    }

                }

                is Resource.Error -> {
                    hideProgressBar()
                    repo.message?.let {error ->
                        Log.e("MainActivity", "Error: $error")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
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


}