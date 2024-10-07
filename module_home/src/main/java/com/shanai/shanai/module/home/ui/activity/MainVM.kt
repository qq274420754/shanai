package com.shanai.shanai.module.home.ui.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.shanai.base.mvvm.vm.BaseViewModel
import com.shanai.base.utils.SpUtils
import com.shanai.common.constant.SpKey.KEY_SYSPARAM_CACHE
import com.shanai.shanai.common.model.SysParamBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 首页的VM层
 *
 * @property mRepository HomeRepository 仓库层 通过Hilt注入
 *
 * @author LZM
 * @since 5/25/21 5:41 PM
 */
@HiltViewModel
class MainVM @Inject constructor(private val mRepository: MainRepo) : BaseViewModel() {

//    val messageAvatar = MutableLiveData<String>()
//    val newMessage = MutableLiveData<Boolean>()
//    val menuData = MutableLiveData<List<BottomMenuBean>>()

    val sysParamBean = MutableLiveData<SysParamBean>()

    fun getMenuData() {
        // 启动协程执行异步操作
        viewModelScope.launch(Dispatchers.IO){
            val sysjson = SpUtils.getString(KEY_SYSPARAM_CACHE,"")
            if (sysjson.isNullOrEmpty()){
                mRepository.fetchSysParam()
                    .catch { e ->
//                        errorMessage.postValue("sysparam")
                    }
                    .collect{
                        sysParamBean.postValue(it)
                    }
            }else{
                val sysObj = Gson().fromJson(sysjson, SysParamBean::class.java)
                sysParamBean.postValue(sysObj)
            }
        }
    }


}