package com.jenson.wanandroid.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils
import com.jenson.core.widget.recyclerView.SpaceItemDecoration
import com.jenson.wanandroid.R
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 *  author: CDJenson
 *  date: 2020/8/25 16:19
 *	version: 1.0
 *  description: One-sentence description
 */
@BindingAdapter("onRefresh")
fun SmartRefreshLayout.onRefresh(block: () -> Unit) {
    setOnRefreshListener { block() }
}

@BindingAdapter("onLoadMore")
fun SmartRefreshLayout.onLoadMore(block: () -> Unit) {
    setOnLoadMoreListener {
        block()
        findViewWithTag<RecyclerView>(context.getString(R.string.tag_recyclerview)).stopScroll()
    }
}

@BindingAdapter("itemDecorationOrientation", "itemDecorationSize", "itemDecorationColor", "itemDecorationFirstLineVisible", requireAll = false)
fun RecyclerView.itemDocration(orientation: Int, size: Int, color: Int , firstLineVisible: Boolean) {
    val itemDecoration = with(SpaceItemDecoration.Builer()){
        this.orientation = orientation
        this.size = SizeUtils.dp2px(size.toFloat())
        this.color = color
        this.firstLineVisible = firstLineVisible
        create()
    }
    addItemDecoration(itemDecoration)
}