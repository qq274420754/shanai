package com.shanai.shanai.module.home.ui.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.shanai.base.mvvm.vm.BaseViewModel
import com.shanai.base.utils.SpUtils
import com.shanai.base.utils.StateLayoutEnum
import com.shanai.common.constant.SpKey.KEY_PRIVACY_CACHE
import com.shanai.shanai.common.model.PrivacyAgreement
import com.shanai.shanai.common.model.SysParamBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val splashRepository: SplashRepo
) : BaseViewModel() {

    // LiveData 用于背景图片、标题和隐私协议内容
    val privacyAgreement = MutableLiveData<PrivacyAgreement>()
    val errorMessage = MutableLiveData<String>() // 用于展示错误消息
    val sysParamBean = MutableLiveData<SysParamBean>()

    // 加载隐私协议和背景数据
    fun loadPrivacyAlertData() {
        // 启动协程执行异步操作
        viewModelScope.launch(Dispatchers.IO) {
            val privacyjson = SpUtils.getString(KEY_PRIVACY_CACHE,"")
            if (privacyjson.isNullOrEmpty()){
                //空  请求接口获取系统配置信息
                splashRepository.fetchPrivacyAgreement()
                    .catch { e ->
                        changeStateViewWithMessage(StateLayoutEnum.ERROR,e.message)
                        errorMessage.postValue("sysconfigure")
                    }
                    .onStart {
                        changeStateView(StateLayoutEnum.LOADING)
                    }
                    .collect{
                        changeStateView(StateLayoutEnum.HIDE)
                        SpUtils.put(KEY_PRIVACY_CACHE,Gson().toJson(it))
                        privacyAgreement.postValue(it)
                    }
            }else{
                val privacyObj = Gson().fromJson(privacyjson, PrivacyAgreement::class.java)
                privacyAgreement.postValue(privacyObj)
                changeStateView(StateLayoutEnum.HIDE)
            }
        }
    }

    // 加载系统配置信息
    fun loadSysParam() {
        // 启动协程执行异步操作
        viewModelScope.launch(Dispatchers.IO) {
            splashRepository.fetchSysParam()
                .catch { e ->
                   errorMessage.postValue("sysparam")
                }
                .collect{
                    sysParamBean.postValue(it)
                }
        }
    }







}
