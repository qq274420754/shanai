package com.shanai.module.home.ui.fragment

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl
import com.shanai.common.ui.fragment.BaseFragment
import com.shanai.module.home.databinding.HomeFragmentMessageLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author DBoy 2021/7/6 <p>
 * - 文件描述 : 测试fragment
 */
@Route(name = RouteKey.MESSAGE_FRAGMENT, path = RouteUrl.MESSAGE_F)
@AndroidEntryPoint
class MessageFragment : BaseFragment<HomeFragmentMessageLayoutBinding, MessageVM>() {

    override val mViewModel by viewModels<MessageVM>()

    override fun createVB() = HomeFragmentMessageLayoutBinding.inflate(layoutInflater)

    override fun HomeFragmentMessageLayoutBinding.initView() {}


    override fun initObserve() {

    }

    override fun initRequestData() {
        mViewModel.getData()
    }



}