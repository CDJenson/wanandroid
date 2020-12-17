package com.jenson.wanandroid.ext

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.ResourceUtils
import com.chad.library.adapter.base.BaseBinderAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import com.jenson.core.base.showEmpty
import com.jenson.core.base.showError
import com.jenson.wanandroid.R
import com.jenson.wanandroid.data.result.ListResult
import com.jenson.wanandroid.data.result.Result
import com.kingja.loadsir.core.LoadService
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 *  author: CDJenson
 *  date: 2020/8/24 18:01
 *	version: 1.0
 *  description: One-sentence description
 */

fun observeListResult(loadService: LoadService<Any>, refreshLayout: SmartRefreshLayout, adapter: BaseBinderAdapter, listResult: ListResult<*>) {
    when {
        listResult.isRefresh -> {
            if (listResult.isNoMore) refreshLayout.finishRefreshWithNoMoreData() else refreshLayout.finishRefresh(listResult.isSuccess)
            if (listResult.isSuccess) {
                if (listResult.data.isEmpty()) {
                    loadService.showEmpty()
                } else {
                    loadService.showSuccess()
                    adapter.setList(listResult.data)
                }
            } else {
                loadService.showError()
            }
        }
        listResult.isLoadMore -> {
            if (listResult.isNoMore) refreshLayout.finishLoadMoreWithNoMoreData() else refreshLayout.finishLoadMore(listResult.isSuccess)
            if (listResult.isSuccess) adapter.addData(listResult.data)
        }
    }
}

fun observeResult(loadService: LoadService<Any>, result: Result<*>, block: () -> Unit) {
    if (result.isSuccess) {
        if (ObjectUtils.isEmpty(result.data)) {
            loadService.showEmpty()
        } else {
            loadService.showSuccess()
            block()
        }
    } else {
        loadService.showError()
    }
}

fun Context.createTagViewNormal(text: String): TextView {
    return TextView(this).apply {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.text = text
        id = View.generateViewId()
        background = ResourceUtils.getDrawable(R.drawable.bg_tag_normal)
        textSize = 10F
        setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
    }
}

fun Context.createTagViewTopOrNew(text: String): TextView {
    return TextView(this).apply {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.text = text
        id = View.generateViewId()
        background = ResourceUtils.getDrawable(R.drawable.bg_tag_hot)
        textSize = 10F
        setTextColor(ContextCompat.getColor(context, R.color.red))
    }
}

fun FloatingActionButton.init(recyclerView: RecyclerView) {
    imageTintList = ColorStateList.valueOf(Color.WHITE)

    setOnClickListener {
        hide(object : FloatingActionButton.OnVisibilityChangedListener() {
            override fun onHidden(fab: FloatingActionButton?) {
                super.onHidden(fab)
                fab?.visibility = View.INVISIBLE
            }
        })
        if ((recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() > 40) {
            recyclerView.scrollToPosition(0)
        } else {
            recyclerView.smoothScrollToPosition(0)
        }
    }
}

fun Context.showProgressDialog(message:String, cancelable:Boolean = false, onDismissListener: DialogInterface.OnDismissListener? = null): AlertDialog {
    val view = LayoutInflater.from(this).inflate(R.layout.layout_progress_dialog, null)
    val textView = view.findViewById<MaterialTextView>(R.id.progress_dialog_message)
    textView.text = message
    return AlertDialog
        .Builder(this)
        .apply {
            setView(view)
            setCancelable(cancelable)
            setOnDismissListener(onDismissListener)
        }.show()
}