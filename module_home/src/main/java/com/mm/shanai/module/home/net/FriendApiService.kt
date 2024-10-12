package com.shanai.module.home.net

import com.mm.shanai.module.home.entity.FriendInfoListBean
import com.quyunshuo.wanandroid.common.bean.BaseResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Home模块的接口
 *
 * @author LZM
 * @since 6/4/21 5:51 PM
 */
interface FriendApiService {

    // 定义基础路径前缀
    companion object {
        const val BASE_PATH = "/app-api/setting"
    }

    /**
     * 获取用户列表信息
     */
    @GET("$BASE_PATH/get_user_list/{itemtpe}/{currpager}")
    suspend fun getUserList(@Path("itemtpe")itemtpe:String,@Path("currpager")currpager:Int,): BaseResponse<FriendInfoListBean>


}