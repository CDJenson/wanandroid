package com.jenson.wanandroid.viewmodel

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.jenson.core.base.BaseViewModel

/**
 *  author: CDJenson
 *  date: 2020/9/2 9:25
 *	version: 1.0
 *  description: One-sentence description
 */
class BroswerViewModel:BaseViewModel() {

    var progress = ObservableInt(0)
    var visibility = ObservableInt(View.VISIBLE)

    override fun initData() {

    }
}