package com.jenson.core.base

import android.view.View

/**
 *  author: CDJenson
 *  date: 2020/8/10 17:08
 *	version: 1.0
 *  description: One-sentence description
 */
interface IBaseView {

    fun initLoadSirLayout(): View?

    fun initView()

    fun initUiEvent()

    fun initData()

}