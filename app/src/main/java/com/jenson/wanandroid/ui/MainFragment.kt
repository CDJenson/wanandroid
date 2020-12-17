package com.jenson.wanandroid.ui

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jenson.core.base.BaseMvvmFragment
import com.jenson.core.base.DataBindingConfig
import com.jenson.wanandroid.R
import com.jenson.wanandroid.databinding.FragmentMainBinding
import com.jenson.wanandroid.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseMvvmFragment<FragmentMainBinding, MainViewModel>(R.layout.fragment_main) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mHomeFragment by lazy { HomeFragment() }
    private val mProjectFragment by lazy { ProjectFragment() }
    private val mUserFragment by lazy { UserFragment() }

    init {
        mFragmentList.run {
            add(mHomeFragment)
            add(mProjectFragment)
            add(mUserFragment)
        }
    }

    override fun initDataBindingConfig(): DataBindingConfig? = null

    override fun initLoadSirLayout(): View? = null

    override fun initView() {
        main_viewPager.run {
            isUserInputEnabled = false
            offscreenPageLimit = 1
            adapter = object : FragmentStateAdapter(this@MainFragment) {
                override fun getItemCount() = mFragmentList.size
                override fun createFragment(position: Int) = mFragmentList[position]
            }
        }
    }

    override fun initUiEvent() {
        main_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> main_viewPager.setCurrentItem(0, false)
                R.id.navigation_project -> main_viewPager.setCurrentItem(1, false)
                R.id.navigation_user -> main_viewPager.setCurrentItem(2, false)
            }
            true
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mViewModel.isInFront.value = !hidden
    }

}