package com.example.retrofitexercise.data.repository

import com.example.retrofitexercise.data.datasource.PostDataSource
import com.example.retrofitexercise.data.entity.Post
import javax.inject.Inject

class PostRepository @Inject constructor(var pds:PostDataSource) {

    suspend fun post1() =pds.post1()

    suspend fun post2(id:Int) = pds.post2(id)

    suspend fun getPostComments() = pds.getPostComments()
    suspend fun getPostComments2(postId:Int) = pds.getPostComments2(postId)

    suspend fun getShortedPostComments(postId:Int,sort:String,order:String) = pds.getShortedPostComments(postId,sort,order)

    suspend fun pushPost(post: Post) = pds.pushPost(post)
}