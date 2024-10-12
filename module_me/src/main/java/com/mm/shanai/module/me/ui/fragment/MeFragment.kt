package com.mm.shanai.module.me.ui.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter4.BaseQuickAdapter
import com.shanai.base.ktx.observeLiveData
import com.shanai.base.utils.DimenUtil
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl
import com.shanai.common.ui.fragment.BaseFragment
import com.shanai.module.me.databinding.HomeFragmentMeLayoutBinding
import com.mm.shanai.common.adapter.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author DBoy 2021/7/6 <p>
 * - 文件描述 : 测试fragment
 */
@Route(name = RouteKey.ME_FRAGMENT, path = RouteUrl.ME_F)
@AndroidEntryPoint
class MeFragment : BaseFragment<HomeFragmentMeLayoutBinding, MeVM>() {



    override val mViewModel by viewModels<MeVM>()


    override fun createVB() = HomeFragmentMeLayoutBinding.inflate(layoutInflater)

    override fun HomeFragmentMeLayoutBinding.initView() {

    }


    override fun initObserve() {
    }

    override fun initRequestData() {
    }




}