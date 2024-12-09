package com.example.retrofitexercise.data.repository

import com.example.retrofitexercise.data.datasource.RvDataSource
import javax.inject.Inject

class RvRepository @Inject constructor(val rvds: RvDataSource) {

    suspend fun getPosts() =rvds.getPosts()

    suspend fun get10Posts(userId:Int) = rvds.get10Posts(userId)
}