package com.jenson.wanandroid.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseBinderAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder
import com.jenson.core.base.BaseMvvmFragment
import com.jenson.core.base.DataBindingConfig
import com.jenson.wanandroid.BR
import com.jenson.wanandroid.R
import com.jenson.wanandroid.data.bean.Article
import com.jenson.wanandroid.data.bean.BannerBean
import com.jenson.wanandroid.databinding.FragmentHomeBinding
import com.jenson.wanandroid.databinding.ItemArticleBinding
import com.jenson.wanandroid.ext.*
import com.jenson.wanandroid.viewmodel.HomeViewModel
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_floating_action_button.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 *  author: CDJenson
 *  date: 2020/8/10 16:51
 *	version: 1.0
 *  description: One-sentence description
 */
class HomeFragment : BaseMvvmFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private val mBanner by lazy { Banner<BannerBean, BannerImageAdapter<BannerBean>>(context) }
    private val mAdapter by lazy { BaseBinderAdapter() }

    override fun initDataBindingConfig(): DataBindingConfig? = DataBindingConfig(BR.viewModel)

    override fun initLoadSirLayout(): View? = home_refreshLayout

    override fun initView() {
        initToolbar()
        initBanner()
        initList()
    }

    override fun initUiEvent() {
        mViewModel.run {
            listResult.observe(viewLifecycleOwner, Observer {
                mLoadService?.let { loadService -> observeListResult(loadService, home_refreshLayout, mAdapter, it) }
            })

            bannerData.observe(viewLifecycleOwner, Observer {
                mBanner.run {
                    adapter = object : BannerImageAdapter<BannerBean>(it) {
                        override fun onBindView(holder: BannerImageHolder, data: BannerBean, position: Int, size: Int) {
                            Glide.with(this@HomeFragment).load(data.imagePath).into(holder.imageView)
                        }
                    }
                    setOnBannerListener { _, position ->
                        LogUtils.d("banner-->${position}")
                    }
                }
            })
        }

        (parentFragment as MainFragment).mViewModel.run {
            isInFront.observe(viewLifecycleOwner, Observer {
                if (it) {
                    mBanner.start()
                } else {
                    mBanner.stop()
                }
            })
        }
    }

    private fun initToolbar() {
        toolbar.run {
            title = getString(R.string.title_home)
            inflateMenu(R.menu.home_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_close -> findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
                }
                true
            }
        }
    }

    private fun initBanner() {
        mBanner.run {
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(180f))
            indicator = CircleIndicator(context)
            addBannerLifecycleObserver(viewLifecycleOwner)
        }
    }

    private fun initList() {
        home_recyclerView.adapter = mAdapter.apply {
            setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)

            addHeaderView(mBanner)

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
        floatingActionButton.init(home_recyclerView)
    }

    private val itemBinder = object : QuickDataBindingItemBinder<Article, ItemArticleBinding>() {
        override fun convert(holder: BinderDataBindingHolder<ItemArticleBinding>, data: Article) {
            holder.dataBinding.run {
                val hasTags = data.fresh || data.type == 1 || data.tags.isNotEmpty()
                if (hasTags) {
                    //解决复用
                    if (itemArticleTags.referencedIds.isNotEmpty()) {
                        itemArticleTags.referencedIds.forEach { removeTagView(itemContainer.findViewById<TextView>(it)) }
                    }

                    if (data.fresh) {
                        addTagView(context.createTagViewTopOrNew(getString(R.string.item_tag_new)))
                    }
                    if (data.type == 1) {
                        addTagView(context.createTagViewTopOrNew(getString(R.string.item_tag_top)))
                    }
                    if (data.tags.isNotEmpty()) {
                        data.tags.forEach { addTagView(context.createTagViewNormal(it.name)) }
                    }
                }
                holder.setGone(R.id.item_article_tags, !hasTags)
                item = data
                executePendingBindings()
            }
        }

        override fun onCreateDataBinding(layoutInflater: LayoutInflater, parent: ViewGroup, viewType: Int): ItemArticleBinding {
            return ItemArticleBinding.inflate(layoutInflater, parent, false)
        }

        private fun ItemArticleBinding.addTagView(tagView: View) {
            itemContainer.addView(tagView)
            itemArticleTags.addView(tagView)
        }

        private fun ItemArticleBinding.removeTagView(tagView: View) {
            itemContainer.removeView(tagView)
            itemArticleTags.removeView(tagView)
        }
    }
}