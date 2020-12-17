package com.jenson.wanandroid.ui

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jenson.core.base.BaseMvvmFragment
import com.jenson.core.base.DataBindingConfig
import com.jenson.wanandroid.BR
import com.jenson.wanandroid.R
import com.jenson.wanandroid.data.bean.ProjectType
import com.jenson.wanandroid.databinding.FragmentProjectBinding
import com.jenson.wanandroid.ext.observeResult
import com.jenson.wanandroid.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_project.*

/**
 *  author: CDJenson
 *  date: 2020/8/10 14:35
 *	version: 1.0
 *  description: One-sentence description
 */
class ProjectFragment : BaseMvvmFragment<FragmentProjectBinding, ProjectViewModel>(R.layout.fragment_project) {

    override fun initDataBindingConfig(): DataBindingConfig? = DataBindingConfig(BR.viewModel)

    override fun initLoadSirLayout(): View? = project_rootView

    override fun initView() {
        project_viewPager.run {
            offscreenPageLimit = 1
        }
    }

    override fun initUiEvent() {
        mViewModel.resultState.observe(this) { resultState ->
            mLoadService?.let {
                observeResult(it,resultState){
                    initTitle(resultState.data!!)
                }
            }
        }
    }

    private fun initTitle(data: ArrayList<ProjectType>) {
        project_viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = data.size

            override fun createFragment(position: Int): Fragment = ProjectChildFragment.create(data[position].id)
        }

        TabLayoutMediator(project_tabLayout, project_viewPager) { tab, position -> tab.text = data[position].name }.attach()
    }

}