package com.shanai.module.home.ui.fragment

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl
import com.shanai.common.ui.fragment.BaseFragment
import com.shanai.module.home.databinding.HomeFragmentVideochatLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author DBoy 2021/7/6 <p>
 * - 文件描述 : 测试fragment
 */
@Route(name = RouteKey.VIDEOCHAT_FRAGMENT, path = RouteUrl.VIDEOCHAT_F)
@AndroidEntryPoint
class VideoChatFragment : BaseFragment<HomeFragmentVideochatLayoutBinding, VideoChatVM>() {

    override val mViewModel by viewModels<VideoChatVM>()

    override fun createVB() = HomeFragmentVideochatLayoutBinding.inflate(layoutInflater)

    override fun HomeFragmentVideochatLayoutBinding.initView() {}


    override fun initObserve() {

    }

    override fun initRequestData() {
        mViewModel.getData()
    }



}