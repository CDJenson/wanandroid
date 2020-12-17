package com.jenson.wanandroid.ui

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ToastUtils
import com.jenson.core.base.BaseMvvmFragment
import com.jenson.core.base.DataBindingConfig
import com.jenson.wanandroid.BR
import com.jenson.wanandroid.R
import com.jenson.wanandroid.databinding.FragmentUserBinding
import com.jenson.wanandroid.viewmodel.SharedViewModel
import com.jenson.wanandroid.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user.*

/**
 *  author: CDJenson
 *  date: 2020/8/10 14:35
 *	version: 1.0
 *  description: One-sentence description
 */
class UserFragment : BaseMvvmFragment<FragmentUserBinding, UserViewModel>(R.layout.fragment_user) {

    private val mSharedViewModel by activityViewModels<SharedViewModel>()

    override fun initDataBindingConfig(): DataBindingConfig? = DataBindingConfig(BR.viewModel).addExtra(BR.clickProxy, ClickProxy())

    override fun initLoadSirLayout(): View? = null

    override fun initView() {
        user_item_article.id
    }

    override fun initUiEvent() {
        mSharedViewModel.user.observe(this,observer = Observer {
            it?.let {
                mViewModel.nickname.set(it.username)
                mViewModel.id.set(it.id.toString())
                mViewModel.coin.set(it.coinCount.toString())
            }
        })
    }

    inner class ClickProxy {

        fun login() {
//            if (!mSharedViewModel.isLogined()) {
                findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
//            }
        }

        val collectArticle = {
            ToastUtils.showShort("collectArticle")
        }

        val shareArticle = {
            ToastUtils.showShort("shareArticle")
        }

        val collectWebsite = {
            ToastUtils.showShort("collectWebsite")
        }

        val shareWebsite = {
            ToastUtils.showShort("shareWebsite")
        }

        val setting = {
            ToastUtils.showShort("设置")
        }

        val openSouce = {
            findNavController().navigate(R.id.action_mainFragment_to_broswerFragment, bundleOf(BroswerFragment.URL to SOURCE_URL))
        }

        fun checkLogin(block:()->Unit){
            if (!mSharedViewModel.isLogined()) {
                ToastUtils.showShort(R.string.toast_login)
                return
            }
            block()
        }
    }

    companion object {
        const val SOURCE_URL = "https://github.com/CDJenson/Jetpack-mvvm.git"
    }
}