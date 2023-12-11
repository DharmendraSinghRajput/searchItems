package com.example.localysearchrecyclerview.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.localysearchrecyclerview.adapter.SearchItemAdapter
import com.example.localysearchrecyclerview.databinding.ActivityMainBinding
import com.example.localysearchrecyclerview.models.PostApiResponseItem
import com.example.localysearchrecyclerview.utils.BaseActivity
import com.example.localysearchrecyclerview.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val searchViewModel by viewModels<SearchViewModel>()
    var arrListDataSearch= arrayListOf<PostApiResponseItem>()

    val searchItemAdapter by lazy {
        SearchItemAdapter{position ->

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.apply {
            searchViewModel.callGetAllPostAPI(this@MainActivity)
            searchViewModel._getPosAPIResponse.observe(this@MainActivity) {
                it.forEach {
                    arrListDataSearch.add(it)
                }
                searchItemAdapter.setData(it)

            }
            listRecyclerView.apply {

                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = searchItemAdapter

            }
            etSearchHobbies.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    val query = s.toString()
                    val searchItem = arrListDataSearch?.filter {
                        it.title.startsWith(query,true)
                    }
                    searchItemAdapter.setData(searchItem ?: arrayListOf())

                }

                override fun afterTextChanged(s: Editable?) {
                }

            })

        }
    }
}