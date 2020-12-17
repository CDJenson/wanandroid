package com.jenson.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *  author: CDJenson
 *  date: 2020/8/12 11:37
 *	version: 1.0
 *  description: One-sentence description
 */
abstract class BaseMvvmFragment<VDB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes layoutId: Int) : IBaseMvvmView, BaseFragment(layoutId) {

    var mViewDataBinding: VDB? = null
    val mViewModel by lazy { getViewModel<VM>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding = DataBindingUtil.inflate<VDB>(inflater, layoutId, container, false).apply {
            lifecycleOwner = this@BaseMvvmFragment
            initDataBindingConfig()?.let {
                this.setVariable(it.variableId, mViewModel)
                it.bindingParams.forEach { key, value -> this.setVariable(key, value) }
            }

        }
        return mViewDataBinding?.root
    }

    override fun initData() {
        mLoadService?.showLoading()
        mViewModel.setArguments(arguments)
        mViewModel.initData()
    }


}

