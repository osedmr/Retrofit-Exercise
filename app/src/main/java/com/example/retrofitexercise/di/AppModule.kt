package com.example.retrofitexercise.di


import com.example.retrofitexercise.data.datasource.PostDataSource
import com.example.retrofitexercise.data.repository.PostRepository
import com.example.retrofitexercise.retrofit.ApiUtils
import com.example.retrofitexercise.retrofit.PostDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providePostDao(): PostDao {
        return ApiUtils.getPost()
    }

    @Provides
    @Singleton
    fun provideDataSource():PostDataSource{
        return PostDataSource(providePostDao())
    }

    @Provides
    @Singleton
    fun provideRepository(): PostRepository {
        return PostRepository(provideDataSource())
    }

}