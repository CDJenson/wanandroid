package com.jenson.core.widget.loadsir

import com.jenson.wanandroid.R
import com.kingja.loadsir.callback.Callback

/**
 *  author: CDJenson
 *  date: 2020/8/29 10:59
 *	version: 1.0
 *  description: One-sentence description
 */
class EmptyCallback: Callback() {
    override fun onCreateView(): Int {
        return R.layout.state_empty
    }
}