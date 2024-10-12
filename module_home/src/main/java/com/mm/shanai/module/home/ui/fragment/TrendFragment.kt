package com.shanai.module.home.ui.fragment

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl
import com.shanai.common.ui.fragment.BaseFragment
import com.shanai.module.home.databinding.HomeFragmentTrendLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author DBoy 2021/7/6 <p>
 * - 文件描述 : 测试fragment
 */
@Route(name = RouteKey.TREND_FRAGMENT, path = RouteUrl.TREND_F)
@AndroidEntryPoint
class TrendFragment : BaseFragment<HomeFragmentTrendLayoutBinding, TrendVM>() {

    override val mViewModel by viewModels<TrendVM>()

    override fun createVB() = HomeFragmentTrendLayoutBinding.inflate(layoutInflater)

    override fun HomeFragmentTrendLayoutBinding.initView() {}


    override fun initObserve() {

    }

    override fun initRequestData() {
        mViewModel.getData()
    }



}