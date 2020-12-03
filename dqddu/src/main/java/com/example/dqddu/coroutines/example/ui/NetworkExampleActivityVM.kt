package com.example.dqddu.coroutines.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dqddu.base.BaseApp
import com.example.dqddu.coroutines.example.bean.CommonlyUsedWebSite
import com.example.dqddu.coroutines.example.retrofit.RetrofitExt.mApiService
import kotlinx.coroutines.launch

/**
 * 常用网站的列表页的VM
 *
 * @author DQDana For Olivia
 * @since 2020/12/3 5:34 PM
 */
class NetworkExampleActivityVM(app: Application = BaseApp.app) : AndroidViewModel(app) {

    val mResultContentObs = MutableLiveData<List<CommonlyUsedWebSite>>()
    val mResultStatusObs = MutableLiveData<String>()

    /**
     * 请求 api
     * 默认在主线程
     * @param mockResult 想要模拟的结果 success" or failure
     */
    fun getCommonlyUsedWebSiteListRepoAsync(mockResult: Boolean) = viewModelScope.launch {
        try {
            mResultStatusObs.postValue("Loading...")
            if (mockResult) {
                val repo = mApiService.getCommonlyUsedWebSiteListRepoAsync()
                mResultContentObs.postValue(repo.data)
                mResultStatusObs.postValue("成功啦 O(∩_∩)O哈哈~")
            } else {
                mResultStatusObs.postValue("失败了 ┭┮﹏┭┮ ")
            }
        } catch (ex: Exception) {
            mResultContentObs.postValue(emptyList())
            mResultStatusObs.postValue("Server encountered error.")
        }
    }
}