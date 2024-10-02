package com.shanai.module.home.ui.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter4.BaseQuickAdapter
import com.shanai.base.ktx.observeLiveData
import com.shanai.base.utils.DimenUtil
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl
import com.shanai.common.ui.fragment.BaseFragment
import com.shanai.module.home.databinding.HomeFragmentMeLayoutBinding
import com.shanai.shanai.common.adapter.GridSpacingItemDecoration
import com.shanai.shanai.module.home.adapter.FriendListAdapter
import com.shanai.shanai.module.home.entity.FriendInfoBean
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

    @Inject
    lateinit var friendListAdapter: FriendListAdapter

    override fun createVB() = HomeFragmentMeLayoutBinding.inflate(layoutInflater)

    override fun HomeFragmentMeLayoutBinding.initView() {
        initRecyclerView()

    }


    override fun initObserve() {
        observeLiveData(mViewModel.dataList, ::processFriendData)
    }

    override fun initRequestData() {
        mViewModel.getUserListData()
    }
    private fun initRecyclerView() {
        friendListAdapter = FriendListAdapter().apply {
            setItemAnimation(BaseQuickAdapter.AnimationType.AlphaIn)
            setOnItemClickListener { _, view, position ->

            }
        }
        mBinding.recycleview.apply {
            adapter = friendListAdapter
//            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(GridSpacingItemDecoration(1, DimenUtil.dpToPx(requireContext(), 8f), false))
        }

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