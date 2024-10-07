package com.shanai.module.home.ui.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.shanai.base.mvvm.vm.BaseViewModel
import com.shanai.base.utils.SpUtils
import com.shanai.base.utils.status.ViewStatusHelper
import com.shanai.common.constant.RouteKey.Params.KEY_FRIEND_CONTENT_TYPE
import com.shanai.common.constant.RouteUrl
import com.shanai.common.constant.SpKey.KEY_SYSPARAM_CACHE
import com.shanai.shanai.common.model.FriendListBean
import com.shanai.shanai.common.model.SysParamBean
import com.shanai.shanai.module.home.constant.MainTabKey.FRIEND_CONTENT_KEY
import com.shanai.shanai.module.home.entity.FriendTopBannerBean
import com.shanai.shanai.module.home.entity.FriendTopMenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author DBoy 2021/7/6 <p>
 * - 文件描述 : ViewModel再ViewPager2的Fragment中会随着Fragment执行[Fragment.onDestory]一同销毁。
 * 所以一些需要长期保存的变量数据，不适合保存再ViewModel，考虑使用[ViewStatusHelper]保存页面上部分数据，
 * 页面恢复的时候再交给ViewModel处理,例如[recreatedCont]
 */
@HiltViewModel
class FriendVM @Inject constructor(private var mRepository: FriendRepo) : BaseViewModel() {


    val topMenulist = MutableLiveData<List<FriendTopMenuItem>>()

    var topBannerList = MutableLiveData<List<FriendTopBannerBean>> ()

    var homeFriendList = mutableListOf<FriendListBean>()
    /**
     * 获取数据
     */
    fun getData() {

        val topMenu = listOf<FriendTopMenuItem>(
            FriendTopMenuItem( "rank","排行榜",""),
            FriendTopMenuItem("task","任务中心",""),
            FriendTopMenuItem("invite","邀请好友",""),
            FriendTopMenuItem("videochat","缘分速配","")
        )
        topMenulist.postValue(topMenu)


        val topbanner = listOf<FriendTopBannerBean>(
            FriendTopBannerBean( "http://img.mclzm.cn/shanai/test/169634148329540.jpg","http://img.mclzm.cn/shanai/test/169916553673217.jpg","http://img.mclzm.cn/shanai/test/group01.jpg"),
            FriendTopBannerBean("http://img.mclzm.cn/shanai/test/170027831922895.jpg","http://img.mclzm.cn/shanai/test/170625980016539.jpg","http://img.mclzm.cn/shanai/test/group02.jpg"),
            FriendTopBannerBean("http://img.mclzm.cn/shanai/test/17221687468990.jpg","http://img.mclzm.cn/shanai/test/17225708446769.jpg","http://img.mclzm.cn/shanai/test/group03.jpg"),
            FriendTopBannerBean("http://img.mclzm.cn/shanai/test/17237284680589.jpg","http://img.mclzm.cn/shanai/test/17241086187182.jpg","http://img.mclzm.cn/shanai/test/group04.jpg")
        )
        topBannerList.postValue(topbanner)
    }

    fun getFriendFragments():List<Fragment>{
        val fragmentList = mutableListOf<Fragment>()
        getFriendList().forEachIndexed { index,menuItem ->
            fragmentList.add(ARouter.getInstance().build(RouteUrl.FRIEND_CONTENT_F).withString(KEY_FRIEND_CONTENT_TYPE,menuItem.key).navigation() as Fragment)
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
