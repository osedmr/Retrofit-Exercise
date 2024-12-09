package com.example.retrofitexercise.data.datasource

import com.example.retrofitexercise.data.entity.Post
import com.example.retrofitexercise.retrofit.PostDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostDataSource@Inject constructor (var postDao : PostDao) {


    suspend fun post1() = withContext(Dispatchers.IO){
        postDao.post1()

    }

    suspend fun post2(id:Int) = withContext(Dispatchers.IO){
        postDao.post2(id)
    }

    suspend fun getPostComments() = withContext(Dispatchers.IO){
        postDao.getPostComments()

    }
    suspend fun getPostComments2(postId:Int) = withContext(Dispatchers.IO) {
        postDao.getPostComments2(postId)
    }
    suspend fun getShortedPostComments(postId:Int,sort:String,order:String) = withContext(Dispatchers.IO) {
        postDao.getShortedPostComments(postId,sort,order)

    }
    suspend fun pushPost(post: Post) = withContext(Dispatchers.IO) {
        postDao.pushPost(post)

    }


}