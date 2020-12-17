package com.jenson.wanandroid.viewmodel

import com.jenson.core.base.BaseViewModel
import com.jenson.core.event.SingleLiveEvent
import com.jenson.wanandroid.data.bean.User
import com.jenson.wanandroid.data.repository.SpRepository

/**
 *  author: CDJenson
 *  date: 2020/9/10 14:59
 *	version: 1.0
 *  description: One-sentence description
 */
class SharedViewModel : BaseViewModel() {

    val user = SingleLiveEvent<User>().apply { value = SpRepository.INSTANCE.loadUser() }

    override fun initData() {

    }

    fun isLogined(): Boolean {
        return user.value != null
    }
}