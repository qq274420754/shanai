package com.shanai.shanai.module.home.ui.activity

import androidx.lifecycle.MutableLiveData
import com.shanai.base.mvvm.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebVM @Inject constructor(private val webRepository: WebRepo) : BaseViewModel() {


    val title: MutableLiveData<String> = MutableLiveData()
    val righttitle: MutableLiveData<String> = MutableLiveData()


}
