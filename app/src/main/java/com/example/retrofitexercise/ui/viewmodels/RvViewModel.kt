package com.example.retrofitexercise.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexercise.data.entity.Post
import com.example.retrofitexercise.data.repository.RvRepository
import com.example.retrofitexercise.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RvViewModel @Inject constructor(val rvRepo : RvRepository) : ViewModel() {

    val postList :MutableLiveData<Resource<List<Post>>> = MutableLiveData()

    fun getPosts(){
        viewModelScope.launch(Dispatchers.Main) {
            postList.postValue(Resource.Loading())
            val response = rvRepo.getPosts()
            postList.postValue(handleListResponse(response))
        }
    }

    private fun handleListResponse(response: Response<List<Post>>) : Resource<List<Post>> {
        if (response.isSuccessful){
            response.body()?.let {myResponse ->
                return Resource.Success(myResponse)
            }
        }
        return Resource.Error("Hata Oluştu ${response.errorBody()} - ${response.code()}")

    }


//---------------------------------------------------------------------------------------------------
    // Post 10 10 Çekme yöntemi ile yapma

    val response10Post :MutableLiveData<Resource<List<Post>>> = MutableLiveData()
    private  var currentUserId = 1
    var hasFirstPostSeen : Boolean =false
    var isPagination :Boolean = false
        private set
    var isRefreshing :Boolean =false
        private set

    var isLastPage :Boolean =false
    private val totalResults = 100 // totalResults normalde veri tababından alınır kaç veri olduğunu söyler buna göre işlem yaparız.
    private val totalPage = totalResults / 10 //10 deme sebebi verileri 10 10 çekiyoruz




    init {
        get10Post(currentUserId)
    }

    fun get10Post(userId:Int){
        viewModelScope.launch(Dispatchers.Main) {
            response10Post.postValue(Resource.Loading())
            val response = rvRepo.get10Posts(userId)
            response10Post.postValue(handle10ListResponse(response))
            isPagination = false
            isRefreshing = false
        }
    }

    fun loadMorePosts(){
        if (!isLastPage){
            isPagination = true
            currentUserId++
            get10Post(currentUserId)
        }

    }
    fun refreshPosts(){
        isRefreshing = true
        currentUserId=1
        hasFirstPostSeen=false
        isPagination=false
        isLastPage=false
        get10Post(currentUserId)
    }

    private fun handle10ListResponse(response: Response<List<Post>>) : Resource<List<Post>> {
        if (response.isSuccessful){
            response.body()?.let {myResponse ->
                if (!hasFirstPostSeen){
                    hasFirstPostSeen = true
                }
                isLastPage = currentUserId == totalPage
                return Resource.Success(myResponse)
            }
        }
        return Resource.Error("Hata Oluştu ${response.errorBody()} - ${response.code()}")

    }


}