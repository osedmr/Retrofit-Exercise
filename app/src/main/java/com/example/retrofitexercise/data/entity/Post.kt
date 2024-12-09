package com.example.retrofitexercise.data.entity

import com.example.retrofitexercise.retrofit.PostDao
import java.io.Serializable


data class Post(
    val body: String?,
    val id: Int?,
    val title: String?,
    val userId: Int?,

    //Comments cagırısı için sonradan eklediğimiz
    val postId: Int?,
    val name: String?,
    val email: String?
) : Serializable