package com.example.retrofitexercise.retrofit

import com.example.retrofitexercise.data.entity.CoilImages
import retrofit2.Response
import retrofit2.http.GET

interface CoilDao {

    @GET("v2/list")
    suspend fun getCoilImages(): Response<List<CoilImages>>
}