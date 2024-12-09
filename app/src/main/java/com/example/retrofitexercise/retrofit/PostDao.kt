package com.example.retrofitexercise.retrofit

import com.example.retrofitexercise.data.entity.Post
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostDao {

    @GET ("/posts/1")
    suspend fun post1(): Response<Post>

    @GET ("/posts/{id}")
    suspend fun post2(
        @Path("id") id :Int
    ) :Response<Post>

    @GET ("/posts/1/comments")
    suspend fun getPostComments() : Response<List<Post>>

    @GET ("/posts/{postId}/comments")
    suspend fun getPostComments2(
        @Path("postId") postId :Int
    ) : Response<List<Post>>

    @GET ("/comments")
    suspend fun getShortedPostComments(
        @Query("postId") postId :Int,
        @Query("_sort") sort :String,
        @Query("_order") order :String
    ) : Response<List<Post>>

    @POST("/posts")
    suspend fun pushPost(
        @Body post: Post
    ) : Response<Post>

    //Recyclerview  bölümü

    @GET("/posts")
    suspend fun getPosts() : Response<List<Post>>

    @GET("/posts")
    suspend fun get10Posts(
        @Query("userId") userId :Int
    ) : Response<List<Post>>

}