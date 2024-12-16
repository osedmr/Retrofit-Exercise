package com.example.retrofitexercise.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitexercise.R
import com.example.retrofitexercise.data.entity.Post
import com.example.retrofitexercise.databinding.ActivityMainBinding
import com.example.retrofitexercise.ui.viewmodels.PostViewModel
import com.example.retrofitexercise.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel : PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.post()
        viewModel.getPostComments(2,"id","desc")

        viewModel.postLiveData.observe(this, Observer {repo ->
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

        viewModel.post2LiveData.observe(this, Observer {repo ->
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

        viewModel.commentsLiveData.observe(this, Observer { commentsData ->
            when(commentsData){
                is Resource.Success -> {
                    hideProgressBar()
                    commentsData.data?.let { postCommentsList ->
                        postCommentsList.forEach {
                            val postCommentText = postCommentsList.joinToString("\n"){it.name.toString()}
                            binding.tvComments.text = postCommentText
                            Log.d("Ose", "Başarılı: ${postCommentText}")
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    commentsData.message?.let { error ->
                        Log.e("MainActivity", "Error: $error")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })

        viewModel.pushPost(Post("beyza",1,"Eşim",5,null,null,null))

        viewModel.pushLiveData.observe(this, Observer {repo ->
            when(repo){
                is Resource.Success ->{
                    hideProgressBar()
                    repo.data?.let {
                        Log.d("Ose", "Başarılı: ${repo.data}")
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    repo.message?.let { error ->
                        Log.e("MainActivity", "Error: $error")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })

        binding.button.setOnClickListener {
            val id = binding.enterId.text.toString().toInt()
            viewModel.post2(id)
        }
        binding.button2.setOnClickListener {
            startActivity(Intent(this, CommentsActivity::class.java))
        }
        binding.button3.setOnClickListener {
            startActivity(Intent(this, RvActivity::class.java))
        }
        binding.button4.setOnClickListener {
            startActivity(Intent(this, Rv10Activity::class.java))
        }
        binding.button5.setOnClickListener {
            startActivity(Intent(this, CoilActivity::class.java))
        }


    }


    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
    }


}