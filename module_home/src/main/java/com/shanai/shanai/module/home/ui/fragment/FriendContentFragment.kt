package com.shanai.module.home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.LoadState
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.shanai.base.ktx.observeLiveData
import com.shanai.base.utils.DimenUtil
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl
import com.shanai.common.ui.fragment.BaseFragment
import com.shanai.module.home.databinding.HomeFragmentFriendcontentLayoutBinding
import com.shanai.shanai.common.adapter.GridSpacingItemDecoration
import com.shanai.shanai.common.view.EmptyView
import com.shanai.shanai.module.home.adapter.CustomLoadMoreAdapter
import com.shanai.shanai.module.home.adapter.FriendListAdapter
import com.shanai.shanai.module.home.adapter.ScrollViewModel
import com.shanai.shanai.module.home.entity.FriendInfoBean
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author
 *
 *
 */
@Route(name = RouteKey.FRIEND_FRAGMENT_CONTENT, path = RouteUrl.FRIEND_CONTENT_F)
@AndroidEntryPoint
class FriendContentFragment : BaseFragment<HomeFragmentFriendcontentLayoutBinding, FriendContentVM>() {

    @Autowired(name = RouteKey.Params.KEY_FRIEND_CONTENT_TYPE)
    lateinit var contentType:String;

    override val mViewModel by viewModels<FriendContentVM>()

    private lateinit var helper: QuickAdapterHelper
    @Inject
    lateinit var friendListAdapter: FriendListAdapter
    lateinit var loadMoreAdapter: CustomLoadMoreAdapter

    override fun createVB() = HomeFragmentFriendcontentLayoutBinding.inflate(layoutInflater)



    override fun HomeFragmentFriendcontentLayoutBinding.initView() {
        mBinding.lifecycleOwner = this@FriendContentFragment
        mBinding.friendlistinfo = mViewModel
        errorView.setTitleText("网络断开了?")
        errorView.setTitleHintText("请检查网络连接后重试?")
        errorView.setButtonText("重新尝试")
        errorView.setOnEmptyViewClickListener(object : EmptyView.OnEmptyViewClickListener {
            override fun onButtonClick() {
                refresh()
            }
        })
        emptyview.setOnEmptyViewClickListener(object : EmptyView.OnEmptyViewClickListener {
            override fun onButtonClick() {
                refresh()
            }
        })
        initRecyclerView()
    }

    override fun initObserve() {
        observeLiveData(mViewModel.dataList, ::processFriendData)
        observeLiveData(mViewModel.errorMessage, ::processErrorData)

    }



    private fun initRecyclerView() {
        mBinding.refreshLayout.apply {
            setOnRefreshListener { refresh() }
        }
        friendListAdapter = FriendListAdapter().apply {
            setItemAnimation(BaseQuickAdapter.AnimationType.AlphaIn)
            setOnItemClickListener { _, view, position ->

            }
        }
        loadMoreAdapter = CustomLoadMoreAdapter().apply {
            isAutoLoadMore = true
            preloadSize = 3
            setOnLoadMoreListener(object : TrailingLoadStateAdapter.OnTrailingListener {
                override fun onLoad() {
                    // 执行加载更多的操作，通常都是网络请求
                    loadMoreData()
                }

                override fun onFailRetry() {
                    // 加载失败后，点击重试的操作，通常都是网络请求
                    loadMoreData()
                }

                override fun isAllowLoading(): Boolean {
                    // 是否允许触发“加载更多”，通常情况下，下拉刷新的时候不允许进行加载更多
                    return !mBinding.refreshLayout.isRefreshing
                }
            })
        }
        // 第二部，使用 Builder 创建 QuickAdapterHelper 对象，这里需要传入你的 mAdapter
         helper = QuickAdapterHelper.Builder(friendListAdapter)
            // 使用默认样式的尾部"加载更多"
            .setTrailingLoadStateAdapter(loadMoreAdapter).build()

        mBinding.friendcontent.apply {
            adapter = helper.adapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(GridSpacingItemDecoration(1, DimenUtil.dpToPx(requireContext(), 8f), true))
        }
    }

    private fun processErrorData(s: String?) {
        helper.trailingLoadState = LoadState.Error(Throwable(s))
    }

    private fun processFriendData(dataList : List<FriendInfoBean>) {
        if (dataList.isNullOrEmpty()){
            processErrorData("empty");
            return
        }
        if (mViewModel.pageInfo.isFirstPage) {
            friendListAdapter.submitList(dataList)
            mBinding.refreshLayout.finishRefresh();
            // 首次加载
        } else {
            // 加载更多
            //不是第一页，则用add
            friendListAdapter.addAll(dataList)
            mBinding.refreshLayout.finishLoadMore();
        }
        helper.trailingLoadState = LoadState.NotLoading(false)
        mViewModel.pageInfo.nextPage()
    }

    override fun initRequestData() {
        mViewModel.getUserListData(contentType);
    }


    /**
     * 刷新
     */
    private fun refresh() {
        // 下拉刷新，需要重置页数
        mViewModel.pageInfo.reset()
        // 重置“加载更多”时状态
        helper.trailingLoadState = LoadState.None
        initRequestData()
    }

    /**
     * 加载更多
     */
    private fun loadMoreData() {
        // 重置“加载更多”时状态
        initRequestData()
    }

}