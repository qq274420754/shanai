package com.shanai.shanai.module.home.ui.activity

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.shanai.base.mvvm.vm.BaseViewModel
import com.shanai.base.utils.SpUtils
import com.shanai.base.utils.StateLayoutEnum
import com.shanai.common.constant.RouteKey.Params.KEY_FRIEND_CONTENT_TYPE
import com.shanai.common.constant.RouteUrl
import com.shanai.shanai.common.model.FriendListBean
import com.shanai.shanai.common.model.SysParamBean
import com.shanai.shanai.module.home.constant.MMKVKeys.KEY_SYSPARAM_CACHE
import com.shanai.shanai.module.home.entity.FriendInfoBean
import com.shanai.shanai.module.home.entity.FriendTopBannerBean
import com.shanai.shanai.module.home.entity.FriendTopMenuItem
import com.shanai.shanai.module.home.utils.PageInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestVM @Inject constructor(private val mRepository: TestRepo) : BaseViewModel() {


    /**
     * 当前页码
     */
    val pageInfo = PageInfo()
    /**
     * 列表数据
     */
    val dataList = MutableLiveData<List<FriendInfoBean>>()

    val allDataList = mutableListOf<FriendInfoBean>()
    var homeFriendList = mutableListOf<FriendListBean>()

    val topMenulist = MutableLiveData<List<FriendTopMenuItem>>()

    var topBannerList = MutableLiveData<List<FriendTopBannerBean>> ()


    /**
     *加载列表数据
     */
    fun getUserListData(contentType:String = "recommon") {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.getUserList(contentType,pageInfo.page)
                .catch {
                    //数据库 展示错误信息和页面
                    if (allDataList.isEmpty()){
                        changeStateView(StateLayoutEnum.ERROR)
                    }
                }
                .onStart {
                    //数据空显示loading
                    if (allDataList.isEmpty()){
                        changeStateView(StateLayoutEnum.LOADING)
                    }
                }
                .collect {
                    if (it.friendList.isEmpty() && allDataList.isEmpty()){
                        changeStateView(StateLayoutEnum.ERROR)
                    }else{
                        changeStateView(StateLayoutEnum.HIDE)
                    }
                    dataList.postValue(it.friendList)
                    if (pageInfo.isFirstPage) {
                        allDataList.clear()
                    }
                    allDataList.addAll(it.friendList)
                }
        }
    }


    fun getFriendFragments():List<Fragment>{
        val fragmentList = mutableListOf<Fragment>()
        getFriendList().forEachIndexed { index,menuItem ->
            fragmentList.add(
                ARouter.getInstance().build(RouteUrl.FRIEND_CONTENT_F).withString(
                    KEY_FRIEND_CONTENT_TYPE,menuItem.key).navigation() as Fragment
            )
        }
        return fragmentList
    }

    fun getFriendName(index: Int): String {
        val friendList = getFriendList()
        return friendList[index].name
    }

    private fun getFriendList(): List<FriendListBean> {
        if (homeFriendList.isEmpty()){
            val sysjson = SpUtils.getString(KEY_SYSPARAM_CACHE, "")
            if (!sysjson.isNullOrEmpty()) {
                val sysObj = Gson().fromJson(sysjson, SysParamBean::class.java)
                if (sysObj!= null && sysObj.friendList!= null) {
                    homeFriendList.addAll(sysObj.friendList)
                }
            }
        }
        return homeFriendList
    }

}
