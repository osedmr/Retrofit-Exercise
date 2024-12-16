package com.example.retrofitexercise.data.repository

import com.example.retrofitexercise.data.datasource.CoilDataSource
import javax.inject.Inject

class CoilRepository @Inject constructor(private val coilDataSource: CoilDataSource) {
    suspend fun getImageCoil() =coilDataSource.getImageCoil()
}