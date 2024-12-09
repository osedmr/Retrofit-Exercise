package com.example.retrofitexercise.retrofit

import com.example.retrofitexercise.data.entity.Post

class ApiUtils {
    companion object{
        val BASE_URL = "https://jsonplaceholder.typicode.com/"
        fun getPost(): PostDao {
            return RetrofitClient.getClient(BASE_URL).create(PostDao::class.java)
        }

    }
}