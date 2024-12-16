package com.example.retrofitexercise.retrofit

import com.example.retrofitexercise.data.entity.Post

class ApiUtils {
    companion object{
        val BASE_URL = "https://jsonplaceholder.typicode.com/"
        fun getPost(): PostDao {
            return RetrofitClient.getClient(BASE_URL).create(PostDao::class.java)
        }

        val BASE_URL2 = "https://picsum.photos/"
        fun getCoil(): CoilDao {
            return RetrofitClient.getCoilClient(BASE_URL2).create(CoilDao::class.java)
        }


    }
}