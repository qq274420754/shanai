package com.shanai.module.home.ui.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter4.BaseQuickAdapter
import com.elvishew.xlog.XLog
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.shanai.base.ktx.observeLiveData
import com.shanai.base.utils.DimenUtil
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl
import com.shanai.common.ui.fragment.BaseFragment
import com.shanai.module.home.R
import com.shanai.module.home.databinding.HomeFragmentFriendLayoutBinding
import com.shanai.shanai.common.adapter.CustomSpacingItemDecoration
import com.shanai.shanai.common.view.magicindicator.ScaleTransitionPagerBoldTitleView
import com.shanai.shanai.common.view.magicindicator.ViewPagerHelper
import com.shanai.shanai.common.view.magicindicator.buildins.UIUtil
import com.shanai.shanai.common.view.magicindicator.buildins.commonnavigator.CommonNavigator
import com.shanai.shanai.common.view.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import com.shanai.shanai.common.view.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import com.shanai.shanai.common.view.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import com.shanai.shanai.common.view.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import com.shanai.shanai.module.home.adapter.FriendTopBannerAdapter
import com.shanai.shanai.module.home.adapter.FriendTopMenuAdapter
import com.shanai.shanai.module.home.adapter.MainViewPagerAdapter
import com.shanai.shanai.module.home.adapter.ScrollViewModel
import com.shanai.shanai.module.home.entity.FriendTopBannerBean
import com.shanai.shanai.module.home.entity.FriendTopMenuItem
import com.zhpan.bannerview.BannerViewPager
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs


/**
 * @author
 *
 */
@Route(name = RouteKey.FRIEND_FRAGMENT, path = RouteUrl.FRIEND_F)
@AndroidEntryPoint
class FriendFragment : BaseFragment<HomeFragmentFriendLayoutBinding, FriendVM>() {

    override val mViewModel by viewModels<FriendVM>()
    private lateinit var topMenuAdapter: FriendTopMenuAdapter
    private lateinit var fragmentList: List<Fragment>
    private var bannerPhotoList = mutableListOf<FriendTopBannerBean>()

    override fun createVB() = HomeFragmentFriendLayoutBinding.inflate(layoutInflater)


    override fun HomeFragmentFriendLayoutBinding.initView() {
        // 获取 ViewModel
        initRecyclerView()
        initBannerViewPager()
        initViewPagerAndTabs()
        initListeners()
    }

    private fun initRecyclerView() {
        topMenuAdapter = FriendTopMenuAdapter().apply {
            setItemAnimation(BaseQuickAdapter.AnimationType.AlphaIn)
            setOnItemClickListener { _, view, position ->
                getItem(position)?.let { Snackbar.make(view, it.key, Snackbar.LENGTH_SHORT).show() }
            }
        }
        mBinding.friendsecordmenuview.apply {
            adapter = topMenuAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(CustomSpacingItemDecoration(4, DimenUtil.dpToPx(requireContext(), 6f), false))
        }
    }

    private fun initBannerViewPager() {
        val bannerViewPager = mBinding.bannerGroupphoto as BannerViewPager<FriendTopBannerBean>
        bannerViewPager.apply {
            adapter = FriendTopBannerAdapter()
            setUserInputEnabled(false)
            registerLifecycleObserver(lifecycle)
        }.create()
    }

    private fun initViewPagerAndTabs() {
        fragmentList = mViewModel.getFriendFragments()
        mBinding.friendviewPager.apply {
            adapter = MainViewPagerAdapter(this@FriendFragment.requireActivity(), fragmentList)
            offscreenPageLimit = fragmentList.size
        }
        initMagicIndicator(fragmentList.size)
    }

    private fun initMagicIndicator(count: Int) {
        val commonNavigator = CommonNavigator(context).apply {
            scrollPivotX = 0.8f
            adapter = object : CommonNavigatorAdapter() {
                override fun getCount() = count

                override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                    return ScaleTransitionPagerBoldTitleView(context).apply {
                        text = mViewModel.getFriendName(index)
                        textSize = 24F
                        normalColor = resources.getColor(R.color.common_textprimary)
                        selectedColor = resources.getColor(R.color.common_black)
                        setOnClickListener { mBinding.friendviewPager.currentItem = index }
                    }
                }

                override fun getIndicator(context: Context?): IPagerIndicator {
                    return LinePagerIndicator(context).apply {
                        mode = LinePagerIndicator.MODE_EXACTLY
                        lineHeight = UIUtil.dip2px(context, 4.0).toFloat()
                        lineWidth = UIUtil.dip2px(context, 12.0).toFloat()
                        roundRadius = UIUtil.dip2px(context, 2.0).toFloat()
                        startInterpolator = AccelerateInterpolator()
                        endInterpolator = DecelerateInterpolator(2.0f)
                        setColors(Color.parseColor("#000000"))
                    }
                }
            }
        }
        mBinding.friendMagicIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(mBinding.friendMagicIndicator, mBinding.friendviewPager)
    }

    private fun initListeners() {
        mBinding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            val percentage = abs(verticalOffset).toFloat() / totalScrollRange
            // 根据滑动比例设置 Toolbar 透明度
            val alpha = percentage.coerceIn(0f, 1f) // 确保在 0 到 1 之间
            mBinding.toolbar.background = ColorDrawable(getColorWithAlpha(alpha, R.color.common_backgroudprimary))
        })

        mBinding.ivSearch.setOnClickListener {
            // 搜索按钮点击逻辑
        }
        mBinding.bannerGroupphoto.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                XLog.d("position= "+position)
                if (!bannerPhotoList.isNullOrEmpty()){
                    try {
                        val friendBannerBean = bannerPhotoList.get(position)

                        Glide.with(requireContext())
                            .load(friendBannerBean?.ladyhead)
                            .circleCrop() // 圆形裁剪
                            .error(R.color.common_gentleman) // 使用资源颜色作为错误占位图
                            .into(mBinding.ivHeadman)

                        Glide.with(requireContext())
                            .load(friendBannerBean?.gentlemanhead)
                            .circleCrop() // 圆形裁剪
                            .error(R.color.common_lady) // 使用资源颜色作为错误占位图
                            .into(mBinding.ivHeadlady)

                    }catch (e:IndexOutOfBoundsException){

                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

            }
        })
    }

    override fun initObserve() {
        observeLiveData(mViewModel.topMenulist, ::setupTopMenu)
        observeLiveData(mViewModel.topBannerList, ::setupTopBanner)
    }

    override fun initRequestData() {
        mViewModel.getData()
    }

    private fun setupTopMenu(menuList: List<FriendTopMenuItem>) {
        topMenuAdapter.submitList(menuList)
    }

    private fun setupTopBanner(friendTopBannerBeans: List<FriendTopBannerBean>?) {
        if (!friendTopBannerBeans.isNullOrEmpty()) {
            bannerPhotoList.clear()
            bannerPhotoList.addAll(friendTopBannerBeans)
            mBinding.bannerGroupphoto.refreshData(friendTopBannerBeans)
        }
    }

    override fun onPause() {
        mBinding.bannerGroupphoto.stopLoop()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mBinding.bannerGroupphoto.startLoop()
    }

    override fun onDestroy() {
        mBinding.bannerGroupphoto.stopLoop()
        super.onDestroy()
    }

    // Helper function to adjust color transparency
    private fun getColorWithAlpha(alpha: Float, colorRes: Int): Int {
        val baseColor = getColor(requireContext(),colorRes)
        val a = (255 * alpha).toInt()
        val red = baseColor shr 16 and 0xFF
        val green = baseColor shr 8 and 0xFF
        val blue = baseColor and 0xFF
        return (a and 0xFF shl 24) or (red shl 16) or (green shl 8) or blue
    }
}
