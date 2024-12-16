package com.example.retrofitexercise.data.datasource

import com.example.retrofitexercise.retrofit.CoilDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CoilDataSource @Inject constructor(private val coilDao: CoilDao) {

    suspend fun getImageCoil() = withContext(Dispatchers.IO) {
        coilDao.getCoilImages()
    }
}