package com.jenson.wanandroid.viewmodel

import com.jenson.core.base.BaseViewModel
import com.jenson.core.event.SingleLiveEvent

/**
 *  author: CDJenson
 *  date: 2020/8/28 15:17
 *	version: 1.0
 *  description: One-sentence description
 */
class MainViewModel:BaseViewModel() {

    var isInFront  = SingleLiveEvent<Boolean>()

    override fun initData() {

    }
}