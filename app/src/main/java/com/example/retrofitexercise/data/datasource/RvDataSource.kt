package com.example.retrofitexercise.data.datasource

import com.example.retrofitexercise.retrofit.PostDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RvDataSource @Inject constructor(val rwDao : PostDao) {

    suspend fun getPosts() = withContext(Dispatchers.IO){
        delay(2000L)
        rwDao.getPosts()
    }

    suspend fun get10Posts(userId:Int) = withContext(Dispatchers.IO){

        rwDao.get10Posts(userId)
    }

}