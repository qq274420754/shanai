package com.mm.shanai.module.me.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shanai.base.mvvm.vm.BaseViewModel
import com.shanai.base.utils.StateLayoutEnum
import com.shanai.base.utils.status.ViewStatusHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author DBoy 2021/7/6 <p>
 * - 文件描述 : ViewModel再ViewPager2的Fragment中会随着Fragment执行[Fragment.onDestory]一同销毁。
 * 所以一些需要长期保存的变量数据，不适合保存再ViewModel，考虑使用[ViewStatusHelper]保存页面上部分数据，
 * 页面恢复的时候再交给ViewModel处理,例如[recreatedCont]
 */
@HiltViewModel
class MeVM @Inject constructor( private var mRepository: MeRepo) : BaseViewModel() {



}
