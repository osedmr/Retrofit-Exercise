package com.example.retrofitexercise.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexercise.data.entity.Post
import com.example.retrofitexercise.data.repository.PostRepository
import com.example.retrofitexercise.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class CommentsViewModel @Inject constructor(val pRepo : PostRepository): ViewModel()  {

    val commentsLiveData : MutableLiveData<Resource<List<Post>>> = MutableLiveData()
    val comments2LiveData : MutableLiveData<Resource<List<Post>>> = MutableLiveData()

    val postLiveData : MutableLiveData<Resource<Post>> = MutableLiveData()
    val post2LiveData : MutableLiveData<Resource<Post>> = MutableLiveData()

  /*  fun getPostComments() {
        viewModelScope.launch(Dispatchers.Main) {
            commentsLiveData.postValue(Resource.Loading())
            val response = pRepo.getPostComments()
            commentsLiveData.postValue(handleListResponse(response))

        }
    }

    fun getPostComments2(postId:Int) {
        viewModelScope.launch(Dispatchers.Main) {
            comments2LiveData.postValue(Resource.Loading())
            val response = pRepo.getPostComments2(postId)
            comments2LiveData.postValue(handleListResponse(response))
        }
    }

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

*/



    fun getAllData(){
        viewModelScope.launch(Dispatchers.Main) {

            val deferred1 = async {
                commentsLiveData.postValue(Resource.Loading())
                pRepo.getPostComments()
            }
            val deferred2 = async {
                comments2LiveData.postValue(Resource.Loading())
                pRepo.getPostComments2(2)
            }

            val deferred3 = async {
                postLiveData.postValue(Resource.Loading())
                pRepo.post1()
            }
            val deferred4 = async {
                post2LiveData.postValue(Resource.Loading())
                pRepo.post2(2)
            }
           val response1 =   deferred1.await()
           val response2 = deferred2.await()
           val response3 = deferred3.await()
           val response4 = deferred4.await()
            commentsLiveData.postValue(handleListResponse(response1))
            comments2LiveData.postValue(handleListResponse(response2))
            postLiveData.postValue(handleResponse(response3))
            post2LiveData.postValue(handleResponse(response4))
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

}