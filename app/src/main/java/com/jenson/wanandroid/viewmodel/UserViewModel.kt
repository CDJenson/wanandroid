package com.jenson.wanandroid.viewmodel

import androidx.databinding.ObservableField
import com.jenson.core.base.BaseViewModel

/**
 *  author: CDJenson
 *  date: 2020/9/9 17:42
 *	version: 1.0
 *  description: One-sentence description
 */
class UserViewModel:BaseViewModel() {

    val nickname = ObservableField<String>("点击登录")
    val coin = ObservableField<String>("--")
    val id = ObservableField<String>("--")

    override fun initData() {

    }
}