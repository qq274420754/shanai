package com.shanai.shanai.module.home.ui.activity

import com.quyunshuo.wanandroid.common.helper.responseCodeExceptionHandler
import com.shanai.base.mvvm.m.BaseRepository
import com.shanai.module.home.net.FriendApiService
import com.shanai.shanai.module.home.entity.FriendInfoListBean
import javax.inject.Inject

/**
 *
 *
 * @author Qu Yunshuo
 * @since 5/25/21 5:42 PM
 */
class TestRepo @Inject constructor() : BaseRepository() {


    @Inject
    lateinit var friendApiService: FriendApiService

    /**
     * 获取列表数据
     */
    suspend fun getUserList(itemtpe:String,currpager:Int) = request<FriendInfoListBean> {
        friendApiService.getUserList(itemtpe,currpager).run {
            responseCodeExceptionHandler(code,msg)
            emit(data)
        }
    }


}