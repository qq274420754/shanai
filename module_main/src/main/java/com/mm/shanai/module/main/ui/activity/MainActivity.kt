package com.mm.shanai.module.main.ui.activity

import android.text.TextUtils
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mm.shanai.common.adapter.CommonViewPagerAdapter
import com.mm.shanai.common.constant.MainTabKey.MainTab_FRIEND
import com.mm.shanai.common.constant.MainTabKey.MainTab_ME
import com.mm.shanai.common.constant.MainTabKey.MainTab_MESSAGE
import com.mm.shanai.common.constant.MainTabKey.MainTab_TRENDS
import com.mm.shanai.common.constant.MainTabKey.MainTab_VIDEOCHAT
import com.shanai.base.ktx.observeLiveData
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl
import com.shanai.common.ui.activity.BaseActivity
import com.shanai.module.main.R
import com.shanai.module.main.databinding.MainActivityBinding
import com.mm.shanai.common.model.BottomMenuBean
import com.mm.shanai.common.model.SysParamBean
import com.mm.shanai.common.view.tablayout.TabEntity
import com.mm.shanai.common.view.tablayout.listener.OnTabSelectListener

import dagger.hilt.android.AndroidEntryPoint


/**
 * 首页
 *
 * @author LZM
 * @since 5/22/21 2:26 PM
 */
@Route(name = RouteKey.MAIN_PAGE, path = RouteUrl.MAIN)
@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityBinding, MainVM>() {


    @Autowired(name = RouteKey.Params.DEFAULT_TAB)
    lateinit var defaultTab:String;

    private var TabMap: Map<String, String> = mapOf(
        MainTab_FRIEND to RouteUrl.FRIEND_F,
        MainTab_TRENDS to RouteUrl.TREND_F,
        MainTab_VIDEOCHAT to RouteUrl.VIDEOCHAT_F,
        MainTab_MESSAGE to RouteUrl.MESSAGE_F,
        MainTab_ME to RouteUrl.ME_F
    )
    private var iconMap: Map<String, Int> = mapOf(
        MainTab_FRIEND to R.drawable.bottomtab_friend_selected,
        MainTab_TRENDS to R.drawable.bottomtab_trend_selected,
        MainTab_VIDEOCHAT to R.drawable.bottomtab_video_selected,
        MainTab_MESSAGE to R.drawable.bottomtab_message_selected,
        MainTab_ME to R.drawable.bottomtab_me_selected
    )
    private var iconGaryMap: Map<String, Int> = mapOf(
        MainTab_FRIEND to R.drawable.bottomtab_friend_normal,
        MainTab_TRENDS to R.drawable.bottomtab_trend_normal,
        MainTab_VIDEOCHAT to R.drawable.bottomtab_video_normal,
        MainTab_MESSAGE to R.drawable.bottomtab_message_normal,
        MainTab_ME to R.drawable.bottomtab_me_normal
    )

    // 动态管理Fragment的集合
    private val tabMap = mutableMapOf<String, Int>()
    private val fragmentList = mutableListOf<Fragment>()
    private val mTabEntities = mutableListOf<com.mm.shanai.common.view.tablayout.TabEntity>()
    private lateinit var mainMenuList: List<BottomMenuBean>

    override val mViewModel by viewModels<MainVM>()

    override fun createVB() = MainActivityBinding.inflate(layoutInflater)

    override fun MainActivityBinding.initView() {
        initListener()
    }

    private fun initListener() {
        mBinding.llBindphonehint.setOnClickListener {
            ARouter.getInstance().build(RouteUrl.PHONELOGIN).navigation()
        }
    }

    override fun initObserve() {
        observeLiveData(mViewModel.sysParamBean, ::setupBottomMenu)
    }

    override fun initRequestData() {
        // 模拟获取数据
        mViewModel.getMenuData()
    }

    private fun setupBottomMenu(sysParamBean: SysParamBean) {
        mainMenuList = sysParamBean.bottomMenuList
        tabMap.clear()
        mTabEntities.clear()
        fragmentList.clear()
        mainMenuList.forEachIndexed { index,menuItem ->
            tabMap.set(menuItem.key,index)
            fragmentList.add(ARouter.getInstance().build(TabMap.get(menuItem.key)).navigation() as Fragment)
            mTabEntities.add(
                com.mm.shanai.common.view.tablayout.TabEntity(
                    menuItem.key,
                    menuItem.name,
                    menuItem.ico,
                    menuItem.ico_gray,
                    iconMap.get(menuItem.key),
                    iconGaryMap.get(menuItem.key)
                )
            )
        }

        mBinding.viewPager.apply {
            adapter =  CommonViewPagerAdapter(this@MainActivity, fragmentList)
            offscreenPageLimit = fragmentList.size
            //静止滑动
            isUserInputEnabled = false
        }

        mBinding.bottomNavigation.apply {
            setTabData(mTabEntities as ArrayList<com.mm.shanai.common.view.tablayout.TabEntity>?)
            setOnTabSelectListener(object :
                com.mm.shanai.common.view.tablayout.listener.OnTabSelectListener {
                override fun onTabSelect(position: Int) {
                    mBinding.viewPager.setCurrentItem(position, false)
                    mBinding.bottomNavigation.setCurrentTab(position)
                }
                override fun onTabReselect(position: Int) {}
            })
            setCurrentTab(getCurrLocal(defaultTab))
        }
    }


    private fun getCurrLocal(key: String): Int {
        return getCurrLocal(key, 0)
    }

    private fun getCurrLocal(key: String, defaultValue: Int): Int {
        if (TextUtils.isEmpty(key)) {
            return defaultValue
        }
        for ((tab, value) in tabMap.entries) {
            if (tab == key) {
                return value
            }
        }
        return defaultValue
    }



}