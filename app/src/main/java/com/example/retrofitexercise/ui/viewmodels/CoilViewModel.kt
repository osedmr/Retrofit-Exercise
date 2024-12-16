package com.example.retrofitexercise.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexercise.data.entity.CoilImages
import com.example.retrofitexercise.data.repository.CoilRepository
import com.example.retrofitexercise.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CoilViewModel @Inject constructor(private val coilRepository: CoilRepository) : ViewModel() {

    val imageCoil : MutableLiveData<Resource<List<CoilImages>>> = MutableLiveData()

    private var currentUserId = 1
    var hasFirstImageSeen :Boolean = false
    var isPagination :Boolean = false
        private set
    var isRefreshing :Boolean =false
        private set

    var isLastPage :Boolean =false
    private val totalResults = 100 // totalResults normalde veri tababından alınır kaç veri olduğunu söyler buna göre işlem yaparız.
    private val totalPage = totalResults / 30 //10 deme sebebi verileri 10 10 çekiyoruz

    init {
        getImageCoil(currentUserId)
    }

    private fun getImageCoil(userId:Int){
        viewModelScope.launch(Dispatchers.Main) {
            imageCoil.postValue(Resource.Loading())
            val response = coilRepository.getImageCoil()
            imageCoil.postValue(handleListResponse(response))
            isPagination = false
            isRefreshing = false
        }
    }

    fun loadMoreImages(){
        if (!isLastPage){
            isPagination = true
            currentUserId++
            getImageCoil(currentUserId)
        }
    }

    fun refreshPosts(){
        isRefreshing = true
        currentUserId =1
        hasFirstImageSeen =false
        isPagination = false
        isLastPage = false
        getImageCoil(currentUserId)
    }


    private fun handleListResponse(response :Response<List<CoilImages>>) : Resource<List<CoilImages>> {
        if (response.isSuccessful){
            response.body()?.let {myResponse ->
                if (!hasFirstImageSeen){
                    hasFirstImageSeen = true
                }
                isLastPage = currentUserId == totalPage
                return Resource.Success(myResponse)
            }
        }
        return Resource.Error("Hata Oluştu ${response.errorBody()} - ${response.code()}")

    }
}