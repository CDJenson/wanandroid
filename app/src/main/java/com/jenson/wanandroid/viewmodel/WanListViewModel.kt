package com.jenson.wanandroid.viewmodel

import com.jenson.core.base.BaseViewModel
import com.jenson.core.data.AppException
import com.jenson.wanandroid.data.result.ListResult
import com.jenson.core.event.SingleLiveEvent
import com.jenson.wanandroid.data.response.WanPageResponse
import com.jenson.wanandroid.data.response.WanResponse

/**
 *  author: CDJenson
 *  date: 2020/8/26 16:09
 *	version: 1.0
 *  description: One-sentence description
 */
abstract class WanListViewModel<T> : BaseViewModel() {

    var listResult = SingleLiveEvent<ListResult<T>>()
    private var mRefreshState = RefreshState.IDLE
    protected var mCurPage = 0

    protected val fetchSuccess: (WanResponse<WanPageResponse<ArrayList<T>>>) -> Unit = {
        listResult.value = ListResult<T>().apply {
            this.isLoadMore = mRefreshState == RefreshState.LOADMORE
            this.isRefresh = mRefreshState == RefreshState.REFRESH
            this.isSuccess = true
            this.isNoMore = it.data.isNoMore()
            this.data = it.data.datas
        }
        mRefreshState = RefreshState.IDLE
    }

    protected val fetchError: (AppException) -> Unit = {
        listResult.value = ListResult<T>().apply {
            this.isLoadMore = mRefreshState == RefreshState.LOADMORE
            this.isRefresh = mRefreshState == RefreshState.REFRESH
            this.isSuccess = false
        }
        mRefreshState = RefreshState.IDLE
    }

    val onRefresh: () -> Unit = {
        mRefreshState = RefreshState.REFRESH
        mCurPage = 0
        fetchListData()
    }

    val onLoadMore: () -> Unit = {
        mRefreshState = RefreshState.LOADMORE
        mCurPage ++
        fetchListData()
    }

    override fun initData() {
        mCurPage = 0
        mRefreshState = RefreshState.REFRESH
    }

    protected abstract fun fetchListData()

    enum class RefreshState(val value: Int) {
        IDLE(1), REFRESH(2), LOADMORE(3)
    }
}