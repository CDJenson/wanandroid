package com.jenson.wanandroid.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseBinderAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder
import com.jenson.core.base.BaseMvvmFragment
import com.jenson.core.base.DataBindingConfig
import com.jenson.wanandroid.BR
import com.jenson.wanandroid.R
import com.jenson.wanandroid.data.bean.Article
import com.jenson.wanandroid.databinding.FragmentProjectChildBinding
import com.jenson.wanandroid.databinding.ItemProjectBinding
import com.jenson.wanandroid.ext.init
import com.jenson.wanandroid.ext.observeListResult
import com.jenson.wanandroid.viewmodel.ProjectChildViewModel
import kotlinx.android.synthetic.main.fragment_project_child.*
import kotlinx.android.synthetic.main.include_floating_action_button.*

/**
 *  author: CDJenson
 *  date: 2020/9/4 15:32
 *	version: 1.0
 *  description: One-sentence description
 */
class ProjectChildFragment : BaseMvvmFragment<FragmentProjectChildBinding, ProjectChildViewModel>(R.layout.fragment_project_child) {

    private val mAdapter by lazy { BaseBinderAdapter() }

    override fun initDataBindingConfig(): DataBindingConfig? = DataBindingConfig(BR.viewModel)

    override fun initLoadSirLayout(): View? = project_refreshLayout

    override fun initView() {
        project_recyclerView.adapter = mAdapter.apply {
            setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)

            addItemBinder(itemBinder)

            setOnItemClickListener { _, _, position ->
                findNavController().navigate(R.id.action_mainFragment_to_broswerFragment, bundleOf(BroswerFragment.URL to (data[position] as Article).link))
            }

            addChildClickViewIds(R.id.item_article_collect)
            setOnItemChildClickListener { _, view, position ->
                when (view.id) {
                    R.id.item_article_collect -> Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show()
                }
            }
        }
        floatingActionButton.init(project_recyclerView)
    }

    override fun initUiEvent() {
        mViewModel.listResult.observe(this){
            mLoadService?.let { loadService -> observeListResult(loadService, project_refreshLayout, mAdapter, it) }
        }
    }

    private val itemBinder = object : QuickDataBindingItemBinder<Article, ItemProjectBinding>() {
        override fun convert(holder: BinderDataBindingHolder<ItemProjectBinding>, data: Article) {
            holder.dataBinding.run {
                item = data
                executePendingBindings()
            }
        }

        override fun onCreateDataBinding(layoutInflater: LayoutInflater, parent: ViewGroup, viewType: Int): ItemProjectBinding {
            return ItemProjectBinding.inflate(layoutInflater, parent, false)
        }
    }

    companion object {
        const val CID = "cid"

        fun create(cid: Int): ProjectChildFragment {
            return ProjectChildFragment().apply {
                arguments = bundleOf(CID to cid)
            }
        }
    }
}