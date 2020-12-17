package com.jenson.wanandroid.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.jenson.core.base.BaseViewModel
import com.jenson.core.data.CoroutineRequestHelper
import com.jenson.core.event.SingleLiveEvent
import com.jenson.wanandroid.data.bean.User
import com.jenson.wanandroid.data.repository.SpRepository
import com.jenson.wanandroid.data.repository.WanRepository
import com.jenson.wanandroid.data.response.WanResponse
import com.jenson.wanandroid.data.result.Result

/**
 *  author: CDJenson
 *  date: 2020/9/11 10:02
 *	version: 1.0
 *  description: One-sentence description
 */
class LoginViewModel : BaseViewModel() {

    val result = SingleLiveEvent<Result<User>>()
    val showProgressBar = SingleLiveEvent<Boolean>()
    val username by lazy {
        ObservableField<String>().apply {
            set(SpRepository.INSTANCE.loadUsername())
        }
    }
    val password by lazy {
        ObservableField<String>().apply {
            set(SpRepository.INSTANCE.loadPassword())
        }
    }

    override fun initData() {

    }

    fun login(username: String, password: String) {
        CoroutineRequestHelper.doRequest<WanResponse<User>>(viewModelScope) {
            load { WanRepository.INSTANCE.login(username, password) }
            onStart { showProgressBar.value = true }
            onSuccess {
                result.value = Result<User>().apply {
                    data = it.data
                    isSuccess = true
                }
            }
            onError { ToastUtils.showShort(it.message) }
            onComplete { showProgressBar.value = false }
        }
    }
}