package com.jenson.wanandroid.viewmodel

import androidx.lifecycle.viewModelScope
import com.jenson.core.data.CoroutineRequestHelper
import com.jenson.wanandroid.data.bean.Article
import com.jenson.wanandroid.data.repository.WanRepository
import com.jenson.wanandroid.data.response.WanPageResponse
import com.jenson.wanandroid.data.response.WanResponse
import com.jenson.wanandroid.ui.ProjectChildFragment

/**
 *  author: CDJenson
 *  date: 2020/9/4 15:34
 *	version: 1.0
 *  description: One-sentence description
 */
class ProjectChildViewModel:WanListViewModel<Article>() {

    override fun initData() {
        super.initData()
        fetchListData()
    }

    override fun fetchListData() {
        val cid = mArguments?.getInt(ProjectChildFragment.CID) ?:0
        CoroutineRequestHelper.doRequest<WanResponse<WanPageResponse<ArrayList<Article>>>>(viewModelScope){
            load { WanRepository.INSTANCE.getProjectList(mCurPage,cid) }
            onSuccess(fetchSuccess)
            onError(fetchError)
        }
    }
}