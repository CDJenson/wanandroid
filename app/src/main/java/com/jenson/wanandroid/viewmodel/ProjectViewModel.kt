package com.jenson.wanandroid.viewmodel

import androidx.lifecycle.viewModelScope
import com.jenson.core.base.BaseViewModel
import com.jenson.core.data.CoroutineRequestHelper
import com.jenson.core.event.SingleLiveEvent
import com.jenson.wanandroid.data.result.Result
import com.jenson.wanandroid.data.bean.ProjectType
import com.jenson.wanandroid.data.repository.WanRepository
import com.jenson.wanandroid.data.response.WanResponse

/**
 *  author: CDJenson
 *  date: 2020/9/4 14:41
 *	version: 1.0
 *  description: One-sentence description
 */
class ProjectViewModel:BaseViewModel() {

    val resultState = SingleLiveEvent<Result<ArrayList<ProjectType>>>()

    override fun initData() {
        CoroutineRequestHelper.doRequest<WanResponse<ArrayList<ProjectType>>>(viewModelScope){
            load { WanRepository.INSTANCE.getProjectTypeList() }
            onSuccess {
                resultState.value = Result<ArrayList<ProjectType>>().apply {
                    isSuccess = true
                    data = it.data
                }
            }
            onError {
                resultState.value = Result<ArrayList<ProjectType>>().apply {
                    isSuccess = false
                }
            }
        }
    }
}