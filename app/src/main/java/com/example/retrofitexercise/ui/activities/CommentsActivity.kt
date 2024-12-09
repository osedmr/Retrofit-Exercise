package com.example.retrofitexercise.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.retrofitexercise.databinding.ActivityCommentsBinding
import com.example.retrofitexercise.ui.viewmodels.CommentsViewModel
import com.example.retrofitexercise.ui.viewmodels.PostViewModel
import com.example.retrofitexercise.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentsBinding
    val commentsViewModel : CommentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)




        commentsViewModel.getAllData()
        commentsViewModel.commentsLiveData.observe(this, Observer { repo ->
            when (repo) {
                is Resource.Success -> {
                    hideProgressBar()
                    repo.data?.let { postCommentList ->
                        val postCommentText = postCommentList.joinToString("\n") { it.name.toString() }
                        binding.comments1.text = postCommentText
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    repo.message?.let { error ->
                        binding.comments1.text = error
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }



            }

        })
        commentsViewModel.comments2LiveData.observe(this, Observer { repo ->
            when (repo) {
                is Resource.Success -> {
                    hideProgressBar()
                    repo.data?.let { postCommentList ->
                        val postCommentText = postCommentList.joinToString("\n") { it.name.toString() }
                        binding.comments2.text = postCommentText
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    repo.message?.let { error ->
                        binding.comments2.text = error
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }



            }

        })
        commentsViewModel.postLiveData.observe(this, Observer {repo ->
            when(repo){
                is Resource.Success -> {
                    Log.d("Ose", "Başarılı: ${repo.data}")
                    hideProgressBar()
                    binding.progressBar.visibility = View.GONE
                    binding.textView.text = repo.data.toString()

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
        commentsViewModel.post2LiveData.observe(this, Observer {repo ->
            when(repo){
                is Resource.Success -> {
                    Log.d("Ose", "Başarılı: ${repo.data}")
                    hideProgressBar()

                    binding.textView2.text = repo.data.toString()

                }
                is Resource.Error -> {
                    hideProgressBar()
                    repo.message?.let { error ->
                        Log.e("MainActivity", "Error: $error")
                    }
                }
                is Resource.Loading -> { showProgressBar()}
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