package com.example.localysearchrecyclerview.utils

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.localysearchrecyclerview.R
import com.example.roomdatabasearray.remote.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
open class BaseActivity() : AppCompatActivity() {
    private lateinit var loader: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loader = Dialog(this).apply {
            setContentView(R.layout.item_loader)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setCancelable(false)
        }
    }
    fun handleLoader(resource: Resource<Any>, showLoader: Boolean = true, swipeRefreshLayout: SwipeRefreshLayout? = null, successResponse: (Resource<Any>) -> Unit) {
        swipeRefreshLayout?.isRefreshing = false
        when (resource) {
            is Resource.Error -> {
                Timber.v("okhttp: State Error")
                hideLoader()
            }

            is Resource.Loading -> {
                Timber.v("okhttp: State Loading")
                if (showLoader)
                    showLoader()
            }

            is Resource.NoInternet -> {
                Timber.v("okhttp: State NoInternet")
                hideLoader()
                NetworkUtil.showNetworkDialog(this) { }
            }

            is Resource.Success -> {
//                FileReadWriteUtil(this).writeFileOnInternalStorage("API_Response.txt", GeneralFunctions.prettifyJson(Gson().toJson(resource.data))!!)
                Timber.v("okhttp: State Success")
                hideLoader()
                successResponse(resource)
            }
        }
    }


    fun showLoader() {
        if (!loader.isShowing)
            loader.show()
    }

    fun hideLoader() {
        if (loader.isShowing)
            loader.dismiss()
    }
}
