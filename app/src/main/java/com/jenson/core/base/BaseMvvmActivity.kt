package com.jenson.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *  author: CDJenson
 *  date: 2020/8/12 11:45
 *	version: 1.0
 *  description: One-sentence description
 */
abstract class BaseMvvmActivity<VDB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes private val layoutId:Int):IBaseMvvmView,BaseActivity(0) {

    var mViewDataBinding: VDB? = null
    val mViewModel by lazy { getViewModel<VM>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding = DataBindingUtil.setContentView<VDB>(this,layoutId).apply {
            lifecycleOwner = this@BaseMvvmActivity
            initDataBindingConfig()?.let {
                this.setVariable(it.variableId, mViewModel)
                it.bindingParams.forEach { key, value -> this.setVariable(key, value) }
            }
        }
    }

    override fun initData() {
        mViewModel.initData()
    }


}