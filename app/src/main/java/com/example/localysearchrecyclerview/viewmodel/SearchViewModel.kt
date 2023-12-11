package com.example.localysearchrecyclerview.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localysearchrecyclerview.models.PostApiResponseItem
import com.example.roomdatabasearray.remote.RemoteService
import com.example.roomdatabasearray.remote.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.reflect.Constructor
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val workService: RemoteService):ViewModel() {
  //  private var _getPosAPIResponse = MutableLiveData<Resource<Any>>()
   // var getPosAPIResponse: LiveData<Resource<Any>> = _getPosAPIResponse
  var _getPosAPIResponse = MutableLiveData<List<PostApiResponseItem>>()

    fun callGetAllPostAPI(context: Context) = viewModelScope.launch {
         //_getPosAPIResponse.value = Resource.Loading()
        try {
            val response = workService.getAllPostData()
            if (response.isSuccessful) {
                _getPosAPIResponse.postValue(response.body()!!)
                    //_getPosAPIResponse.value=Resource.Success(response.body()!!)
            } else {
               // _getPosAPIResponse.value=Resource.Error(response.message())

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}