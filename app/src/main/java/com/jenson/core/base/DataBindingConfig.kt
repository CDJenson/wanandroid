package com.jenson.core.base

import android.util.SparseArray

/**
 * author: CDJenson
 * date: 2020/5/20 11:13
 * version: 1.0
 * description: One-sentence description
 */
class DataBindingConfig(val variableId: Int) {

    val bindingParams = SparseArray<Any>()

    fun addExtra(variableId: Int, any: Any): DataBindingConfig {
        if (bindingParams[variableId] == null) {
            bindingParams.put(variableId, any)
        }
        return this
    }
}