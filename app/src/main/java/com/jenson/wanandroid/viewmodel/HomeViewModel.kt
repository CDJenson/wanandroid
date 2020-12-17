package com.jenson.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jenson.core.data.CoroutineRequestHelper
import com.jenson.wanandroid.data.bean.Article
import com.jenson.wanandroid.data.bean.BannerBean
import com.jenson.wanandroid.data.repository.WanRepository
import com.jenson.wanandroid.data.response.WanPageResponse
import com.jenson.wanandroid.data.response.WanResponse

/**
 *  author: CDJenson
 *  date: 2020/8/12 13:44
 *	version: 1.0
 *  description: One-sentence description
 */
class HomeViewModel : WanListViewModel<Article>() {

    var bannerData = MutableLiveData<ArrayList<BannerBean>>()

    override fun initData() {
        super.initData()
        fetchBanner()
        fetchListData()
    }

    override fun fetchListData() {
        CoroutineRequestHelper.doRequest<WanResponse<WanPageResponse<ArrayList<Article>>>>(viewModelScope) {
            load { WanRepository.INSTANCE.getHomeArticleList(mCurPage) }
            onSuccess(fetchSuccess)
            onError(fetchError)
        }
    }

    fun fetchBanner() {
        CoroutineRequestHelper.doRequest<WanResponse<ArrayList<BannerBean>>>(viewModelScope) {
            load { WanRepository.INSTANCE.getBanner() }
            onSuccess {
                bannerData.value = it.data
            }
        }
    }

}