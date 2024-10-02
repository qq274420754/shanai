package com.shanai.module.home.ui.activity

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter4.BaseQuickAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.Behavior.DragCallback
import com.gyf.immersionbar.ktx.immersionBar
import com.shanai.base.ktx.observeLiveData
import com.shanai.base.utils.DimenUtil
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl
import com.shanai.common.ui.activity.BaseActivity
import com.shanai.module.home.R
import com.shanai.module.home.databinding.HomeActivityTestBinding
import com.shanai.module.home.databinding.HomeActivityWebBinding
import com.shanai.shanai.common.adapter.GridSpacingItemDecoration
import com.shanai.shanai.common.view.magicindicator.ScaleTransitionPagerBoldTitleView
import com.shanai.shanai.common.view.magicindicator.ViewPagerHelper
import com.shanai.shanai.common.view.magicindicator.buildins.UIUtil
import com.shanai.shanai.common.view.magicindicator.buildins.commonnavigator.CommonNavigator
import com.shanai.shanai.common.view.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import com.shanai.shanai.common.view.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import com.shanai.shanai.common.view.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import com.shanai.shanai.common.view.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import com.shanai.shanai.module.home.adapter.FriendListAdapter
import com.shanai.shanai.module.home.adapter.MainViewPagerAdapter
import com.shanai.shanai.module.home.entity.FriendInfoBean
import com.shanai.shanai.module.home.entity.FriendTopBannerBean
import com.shanai.shanai.module.home.entity.FriendTopMenuItem
import com.shanai.shanai.module.home.ui.activity.TestVM
import com.shanai.shanai.module.home.ui.activity.WebVM
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(name = RouteKey.WEB_PAGE, path = RouteUrl.WEB)
@AndroidEntryPoint
class TestActivity : BaseActivity<HomeActivityTestBinding, TestVM>(){

    @Autowired(name = RouteKey.Params.WEB_URL)
    lateinit var webUrl:String;

    @Inject
    lateinit var friendListAdapter: FriendListAdapter


    private lateinit var fragmentList: List<Fragment>
    // 通过 Hilt 注入 ViewModel
    override val mViewModel: TestVM by viewModels()

    override fun createVB() = HomeActivityTestBinding.inflate(layoutInflater)

    // 初始化视图
    override fun HomeActivityTestBinding.initView() {
//        initRecyclerView()
        initViewPagerAndTabs()
    }

    override fun initObserve() {

    }

    override fun initRequestData() {
//        mViewModel.getUserListData()
    }

//    private fun initRecyclerView() {
//        friendListAdapter = FriendListAdapter().apply {
//            setItemAnimation(BaseQuickAdapter.AnimationType.AlphaIn)
//            setOnItemClickListener { _, view, position ->
//
//            }
//        }
//        mBinding.recycleview.apply {
//            adapter = friendListAdapter
////            isNestedScrollingEnabled = false
//            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
//            addItemDecoration(GridSpacingItemDecoration(1, DimenUtil.dpToPx(this.context, 8f), false))
//        }
//    }

    private fun initViewPagerAndTabs() {
        fragmentList = mViewModel.getFriendFragments()
        mBinding.friendviewPager.apply {
//            adapter = FriendPagerAdapter(childFragmentManager,this@FriendFragment.requireActivity(), fragmentList)
            adapter = MainViewPagerAdapter(this@TestActivity, fragmentList)
            offscreenPageLimit = fragmentList.size
        }
        initMagicIndicator(fragmentList.size)
    }

    private fun initMagicIndicator(count: Int) {
        val commonNavigator = CommonNavigator(this).apply {
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


    private fun processFriendData(dataList : List<FriendInfoBean>) {
        if (mViewModel.pageInfo.isFirstPage) {
            friendListAdapter.submitList(dataList)
            // 首次加载
        } else {
            // 加载更多
            //不是第一页，则用add
            friendListAdapter.addAll(dataList)
        }
        mViewModel.pageInfo.nextPage()
    }
}