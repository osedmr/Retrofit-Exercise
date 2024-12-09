package com.example.retrofitexercise.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexercise.data.entity.Post
import com.example.retrofitexercise.data.repository.PostRepository
import com.example.retrofitexercise.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(val pRepo : PostRepository) : ViewModel() {


    val postLiveData : MutableLiveData<Resource<Post>> = MutableLiveData()
    val post2LiveData : MutableLiveData<Resource<Post>> = MutableLiveData()
    val commentsLiveData : MutableLiveData<Resource<List<Post>>> = MutableLiveData()


    fun post() {
        viewModelScope.launch(Dispatchers.IO) {

            postLiveData.postValue(Resource.Loading())
            val response = pRepo.post1() // Response<Post> döner
            postLiveData.postValue(handleResponse(response))


        }
    }
    fun post2(id:Int) {

        viewModelScope.launch(Dispatchers.Main) {

            post2LiveData.postValue(Resource.Loading())
            val response = pRepo.post2(id) // Response<Post> döner
            post2LiveData.postValue(handleResponse(response))
        }
    }
    fun getPostComments(postId:Int,sort:String,order:String){
       viewModelScope.launch(Dispatchers.Main) {
        commentsLiveData.postValue(Resource.Loading())
           val response = pRepo.getShortedPostComments(postId,sort,order)
           commentsLiveData.postValue(handleListResponse(response))
       }
    }

    private fun handleResponse(response:Response<Post>) : Resource<Post>  {
        if (response.isSuccessful){
            response.body()?.let {myResponse ->
                return Resource.Success(myResponse)
            }
        }
        return Resource.Error("Hata Oluştu ${response.errorBody()} - ${response.code()}")

    }

    private fun handleListResponse(response: Response<List<Post>>) : Resource<List<Post>> {
        if (response.isSuccessful){
            response.body()?.let {myResponse ->
                return Resource.Success(myResponse)
            }
        }
        return Resource.Error("Hata Oluştu ${response.errorBody()} - ${response.code()}")

    }


    // POST İŞLEMİ
    val pushLiveData : MutableLiveData<Resource<Post>> = MutableLiveData()

    fun pushPost(post: Post) {
        viewModelScope.launch(Dispatchers.Main) {
            pushLiveData.postValue(Resource.Loading())
            val response = pRepo.pushPost(post)
            pushLiveData.postValue(pushHandleResponse(response))
        }
    }

    private fun pushHandleResponse(response:Response<Post>) : Resource<Post>  {
      return  if (response.isSuccessful) {
          response.body()?.let { myResponse ->
              Log.d("Ose", "Response Code !!: ${myResponse}")
              Resource.Success(myResponse)
          } ?: Resource.Error("Hata Oluştu ${response.errorBody()} - ${response.code()}")
      }else{
          Log.d("retrofir", "Response Code !!: ${response.code()}")
          Resource.Error("Hata Oluştu ${response.errorBody()} - ${response.code()}")
      }

    }
}